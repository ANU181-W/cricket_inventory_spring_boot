package com.example.demo.service;

import com.example.demo.dto.AuctionResponse;
import com.example.demo.dto.BidMessage;

public interface AuctionService {
    void startAuction(int playerId);
    AuctionResponse processBid(BidMessage bidMessage);
}
