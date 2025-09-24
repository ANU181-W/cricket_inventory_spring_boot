package com.example.demo.dao;

import com.example.demo.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface auction extends JpaRepository<Auction, Long> {
    Optional<Auction> findByPlayer_PlayerId(int playerId);
}
