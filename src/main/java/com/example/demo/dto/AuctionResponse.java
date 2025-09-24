package com.example.demo.dto;

public class AuctionResponse {

    private int playerId;
    private long currentBid;
    private String highestBidder;
    private String message;

    public AuctionResponse() {}

    public AuctionResponse(int playerId, long currentBid, String highestBidder, String message) {
        this.playerId = playerId;
        this.currentBid = currentBid;
        this.highestBidder = highestBidder;
        this.message = message;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public long getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(long currentBid) {
        this.currentBid = currentBid;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
