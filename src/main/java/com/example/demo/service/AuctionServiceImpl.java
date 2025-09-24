package com.example.demo.service;

import com.example.demo.dao.auction;
import com.example.demo.dto.AuctionResponse;
import com.example.demo.dto.BidMessage;
import com.example.demo.entity.Auction;
import com.example.demo.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class AuctionServiceImpl implements AuctionService {

    private auction auctionRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private final Map<Integer, ScheduledFuture<?>> timers = new ConcurrentHashMap<>();
    private final Map<Integer, Boolean> activeAuctions = new ConcurrentHashMap<>();

    private static final long BASE_PRICE = 5000000;   // ₹50 lakh base price
    private static final long MIN_INCREMENT = 1000000; // ₹20 lakh increment

    @Autowired
    public AuctionServiceImpl(auction auctionRepository, SimpMessagingTemplate messagingTemplate) {
        this.auctionRepository = auctionRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void startAuction(int playerId) {
        if (activeAuctions.getOrDefault(playerId, false)) {
            messagingTemplate.convertAndSend("/topic/auction",
                    new AuctionResponse(playerId, 0, "", "Auction is already active"));
            return;
        }

        activeAuctions.put(playerId, true);

        // Initialize auction with base price
        Auction auction = auctionRepository.findByPlayer_PlayerId(playerId)
                .orElseGet(() -> {
                    Auction newAuction = new Auction();
                    Player player = new Player();
                    player.setPlayerId(playerId);
                    newAuction.setPlayer(player);
                    return newAuction;
                });

        auction.setBiddingAmount(BASE_PRICE);
        auction.setHighestBidder(null);
        auctionRepository.save(auction);

        messagingTemplate.convertAndSend("/topic/auction",
                new AuctionResponse(playerId, auction.getBiddingAmount(), "", "Auction started with base price ₹" + BASE_PRICE));

        resetAuctionTimer(playerId);
    }

    @Override
    public AuctionResponse processBid(BidMessage bidMessage) {
        int playerId = bidMessage.getPlayerId();
        long amount = bidMessage.getBidAmount();
        String bidder = bidMessage.getBidderName();

        if (!activeAuctions.getOrDefault(playerId, false)) {
            return new AuctionResponse(playerId, 0, "", "Auction is not active");
        }

        Auction auction = auctionRepository.findByPlayer_PlayerId(playerId)
                .orElseGet(() -> {
                    Auction newAuction = new Auction();
                    Player player = new Player();
                    player.setPlayerId(playerId);
                    newAuction.setPlayer(player);
                    return newAuction;
                });

        if (amount >= auction.getBiddingAmount() + MIN_INCREMENT) {
            auction.setBiddingAmount(amount);
            auction.setHighestBidder(bidder);
            auctionRepository.save(auction);

            resetAuctionTimer(playerId);

            return new AuctionResponse(playerId, auction.getBiddingAmount(),
                    auction.getHighestBidder(), "New bid placed");
        } else {
            return new AuctionResponse(playerId, auction.getBiddingAmount(),
                    auction.getHighestBidder(), "Bid too low. Minimum increment is ₹" + MIN_INCREMENT);
        }
    }

    private void resetAuctionTimer(int playerId) {
        ScheduledFuture<?> prevTimer = timers.get(playerId);
        if (prevTimer != null) {
            prevTimer.cancel(false);
        }

        ScheduledFuture<?> newTimer = scheduler.schedule(() -> endAuction(playerId), 30, TimeUnit.SECONDS);
        timers.put(playerId, newTimer);
    }

    public void endAuction(int playerId) {
        Auction auction = auctionRepository.findByPlayer_PlayerId(playerId).orElse(null);

        if (auction != null) {
            auction.setAuctionAmount(auction.getBiddingAmount());
            auctionRepository.save(auction);

            String winner = (auction.getHighestBidder() != null) ? auction.getHighestBidder() : "Unsold";

            messagingTemplate.convertAndSend("/topic/auction",
                    new AuctionResponse(
                            playerId,
                            auction.getBiddingAmount(),
                            winner,
                            "Auction ended"
                    ));
        } else {
            messagingTemplate.convertAndSend("/topic/auction",
                    new AuctionResponse(playerId, 0, "Unsold", "Auction ended"));
        }

        timers.remove(playerId);
        activeAuctions.remove(playerId);
    }
}
