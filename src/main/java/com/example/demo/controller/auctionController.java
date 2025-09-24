package com.example.demo.controller;

import com.example.demo.dto.AuctionResponse;
import com.example.demo.dto.BidMessage;
import com.example.demo.entity.Player;
import com.example.demo.service.AuctionServiceImpl;
import com.example.demo.service.playerServiceImpl;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/auction")
public class auctionController {
    private AuctionServiceImpl auctionManager;
    private playerServiceImpl playerService;

    public auctionController(AuctionServiceImpl theAuctionManager, playerServiceImpl playerService) {
        this.auctionManager = theAuctionManager;
        this.playerService = playerService;
    }

    @GetMapping("/getAuction")
    public String getAuctionPage(Model model) {
        List<Player> result = playerService.getAllPlayers();
        model.addAttribute("players", result);
        return "auction-page";
    }

    @MessageMapping("/startAuction")
    public void startAuction(@Payload Map<String, Integer> message) {
        Integer playerId = message.get("playerId");
        if (playerId != null) {
            auctionManager.startAuction(playerId);
        }
    }

    @MessageMapping("/bid")
    @SendTo("/topic/auction")
    public AuctionResponse placeBid(@Payload BidMessage bidMessage) {
        return auctionManager.processBid(bidMessage);
    }
}
