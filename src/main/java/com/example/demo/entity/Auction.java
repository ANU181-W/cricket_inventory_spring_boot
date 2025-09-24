package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="auctionId")
    private int id;

    @Column(name="auctionAmount")
    private long auctionAmount;

    @Column(name="biddingAmount")
    private long biddingAmount;

    @Column(name="highest_bidder")
    private String highestBidder;

    @OneToOne
    @JoinColumn(name="player_id")
    private Player player;

    public Auction() {}

    public Auction(long auctionAmount, long biddingAmount, Player player) {
        this.auctionAmount = auctionAmount;
        this.biddingAmount = biddingAmount;
        this.player = player;
    }

    public long getAuctionAmount() {
        return auctionAmount;
    }

    public void setAuctionAmount(long auctionAmount) {
        this.auctionAmount = auctionAmount;
    }

    public long getBiddingAmount() {
        return biddingAmount;
    }

    public void setBiddingAmount(long biddingAmount) {
        this.biddingAmount = biddingAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }
}
