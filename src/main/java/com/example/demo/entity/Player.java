package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ipl_players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="player_id")
    private int playerId;

    @Column(name="player_name")
    private String playerName;

    @Column(name="jersey_number")
    private int jerseyNumber;

    @Column(name="ranking")
    private int ranking;

    @Column(name="team_name")
    private String teamName;

    @Column(name="country")
    private String country;

@Column(name="player_type")
private String playerType;
    @OneToMany(mappedBy="thePlayer",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<PlayerStats> playerStats=new ArrayList<>();
@ManyToOne()
@JoinColumn(name="teamId")
@JsonBackReference
private Team theTeam;
    public Player(String player_name, int jersey_number, int ranking, String team_name,String playerType,String country){
        this.playerName=player_name;
        this.jerseyNumber=jersey_number;
        this.ranking=ranking;
        this.teamName=team_name;
        this.playerType=playerType;
        this.country=country;
    }
    public Player(){}
    public String getPlayerName(){
        return  playerName;
    }
    public void setPlayerName(String player_name){
        this.playerName=player_name;
    }

    public String getTeamName(){
        return teamName;
    }
    public void setTeamName(String team_name){
        this.teamName=team_name;
    }

    public int getRanking(){
        return  ranking;
    }
    public void setRanking(int ranking){
        this.ranking=ranking;
    }

    public int getJerseyNumber(){
        return jerseyNumber;
    }
    public  void setJerseyNumber(int jersey_number){
        this.jerseyNumber=jersey_number;
    }

    public List<PlayerStats> getPlayerStats(){
        return playerStats;
    }

    public void setPlayerStats(List<PlayerStats> playerStats){
        this.playerStats=playerStats;
    }

    public int getPlayerId(){
        return playerId;
    }
    public void setPlayerId(int playerId){
        this.playerId=playerId;
    }

    public String getPlayerType(){
        return playerType;
    }
    public void setPlayerType(String playerType){
        this.playerType=playerType;
    }
public String getCountry(){
        return country;
}

public void setCountry(String country){
        this.country=country;
}
}
