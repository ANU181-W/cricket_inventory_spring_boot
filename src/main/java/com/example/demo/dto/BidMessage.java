package com.example.demo.dto;

public class BidMessage {

    private int playerId;
    private long bidAmount;
    private String bidderName;

    public BidMessage() {}

    public BidMessage(int playerId, long bidAmount, String bidderName) {
        this.playerId = playerId;
        this.bidAmount = bidAmount;
        this.bidderName = bidderName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public long getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(long bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }
}
