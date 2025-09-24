package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="playerStats")
public class PlayerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int Id;


    @Column(name = "format")
    private  String format;

    @Column(name = "runs")
    private long runs;

    @Column(name="wickets")
    private long wickets;

    @Column(name="icc_ranking")
    private int iccRanking;
    @ManyToOne()
    @JoinColumn(name = "player_id")

    private Player thePlayer;
    public PlayerStats(){};

    public PlayerStats(String format, long runs, long wickets, int icc_ranking,Player thePlayer){
        this.format=format;
        this.runs=runs;
        this.wickets=wickets;
        this.iccRanking=icc_ranking;
        this.thePlayer=thePlayer;
    };

    public  String getFormat(){
        return format;
    }

    public void setFormat(String format){
        this.format=format;
    }

    public  long getRuns(){
        return runs;
    }

    public void setRuns(long runs){
        this.runs=runs;
    }

    public  long getWickets(){
        return wickets;
    }

    public void setWickets(long wickets){
        this.wickets=wickets;
    }

    public  int getIccRanking(){
        return iccRanking;
    }

    public void setIccRanking(int icc_ranking){
        this.iccRanking=icc_ranking;
    }
    @JsonBackReference
    public Player getPlayer() {
        return thePlayer;
    }

    public void setPlayer(Player thePlayer) {
        this.thePlayer = thePlayer;
    }
}
