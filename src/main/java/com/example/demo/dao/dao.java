package com.example.demo.dao;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;

import java.util.List;

public interface dao {
    public void savePlayer(List<Player> thePlayer);

    public List<Player> getAllPlayers();
    public Player getPlayerById(int Id);
    public List<Player> getPlayersLimited();
    public List<PlayerDto> getPlayerStats();
    public List<PlayerDto> getPlayerStatsByFormat(String format);
}
