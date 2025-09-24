package com.example.demo.dto;

public class PlayerDto {

    private String playerName;
    private  String teamName;
    private String playerType;
    private String format;
    private  long runs;
    private long wickets;
    private int iccRanking;

    public PlayerDto(String playerName,String playerType, String teamName, String format, long runs, long wickets, int iccRanking){
        this.playerName=playerName;
        this.teamName=teamName;
        this.playerType=playerType;
        this.format=format;
        this.runs=runs;
        this.wickets=wickets;
        this.iccRanking=iccRanking;
    }

    public String getPlayerName(){
        return playerName;
    }
    public String getTeamName(){
        return teamName;
    }
    public String getFormat(){
        return format;
    }
    public long getRuns(){
        return runs;
    }

    public long getWickets(){
        return wickets;
    }
    public int getIccRanking(){
        return iccRanking;
    }
    public String getPlayerType(){
        return playerType;
    }
}

