package com.example.demo.dao;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class doaImpl implements dao{

    private EntityManager entityManager;

    public doaImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }
@Transactional
public void savePlayer(List<Player> thePlayers) {
    for (Player player : thePlayers) {
        entityManager.persist(player);
    }
}

    public List<Player> getAllPlayers(){
      String jpql="Select DISTINCT p from Player p JOIN FETCH p.playerStats";
        TypedQuery<Player> result=entityManager.createQuery(jpql, Player.class);
        return result.getResultList();
    }

    public Player getPlayerById(int id){

        String jpql="Select p from Player p JOIN FETCH p.playerStats where p.playerId=:id";
        TypedQuery<Player> result=entityManager.createQuery(jpql,Player.class);
        result.setParameter("id", id);
        return  result.getSingleResult();
    }


    public List<Player> getPlayersLimited(){
         String jpql="Select p.playerName,p.teamName from Player p JOIN  p.playerStats";
         TypedQuery<Player> result=entityManager.createQuery(jpql,Player.class);
         return result.getResultList();
    }

    public List<PlayerDto> getPlayerStats(){
        String jpql="Select new PlayerDto(p.playerName,p.teamName,p.playerType,ps.format,ps.runs,ps.wickets,ps.iccRanking)"+
                "from PlayerStats ps JOIN ps.thePlayer p";
        TypedQuery<PlayerDto> result=entityManager.createQuery(jpql, PlayerDto.class);
        return result.getResultList();

    }
    public List<PlayerDto> getPlayerStatsByFormat(String format){
        String jpql="Select new PlayerDto(p.playerName, p.teamName, p.playerType, ps.format, ps.runs, ps.wickets, ps.iccRanking) "
                +"from PlayerStats ps JOIN ps.thePlayer p "
                +"where LOWER(ps.format)=LOWER(:format) "
                +"order by iccRanking ASC";

        TypedQuery<PlayerDto> result=entityManager.createQuery(jpql, PlayerDto.class);
        result.setParameter("format",format);
        return result.getResultList();
    }
}
