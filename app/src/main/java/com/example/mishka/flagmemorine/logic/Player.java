package com.example.mishka.flagmemorine.logic;

import android.content.pm.InstrumentationInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mishka on 15/03/18.
 * Класс для хранения данных игрока.
 */

public class Player {

    public Player(String name, String country, Integer xsScore,
                  Integer sScore, Integer mScore, Integer lScore, Integer xlScore, Integer xxlScore){
        this.name = name;
        this.country = country;
        this.xsScore = xsScore;
        this.sScore = sScore;
        this.mScore = mScore;
        this.lScore = lScore;
        this.xlScore = xlScore;
        this.xxlScore = xxlScore;
        this.totalScore = xsScore + sScore + mScore + lScore + xlScore + xxlScore;
    }

    public static void initPlayers(){
        players = new ArrayList<>();
    }

    public static void clearPlayers(){ players.clear(); }

    public static void addPlayer(Player player){
        players.add(player);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void sortPlayers(){
        Player player;
        for (int i = 0; i < players.size() - 1; i++) {
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(i).getTotalScore() < players.get(j).getTotalScore()){
                    player = players.get(i);
                    players.set(i,players.get(j));
                    players.set(j,player);
                }
            }
        }
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(List<Player> players) {
        Player.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getXsScore() {
        return xsScore;
    }

    public void setXsScore(Integer xsScore) {
        this.xsScore = xsScore;
    }

    public Integer getsScore() {
        return sScore;
    }

    public void setsScore(Integer sScore) {
        this.sScore = sScore;
    }

    public Integer getmScore() {
        return mScore;
    }

    public void setmScore(Integer mScore) {
        this.mScore = mScore;
    }

    public Integer getlScore() {
        return lScore;
    }

    public void setlScore(Integer lScore) {
        this.lScore = lScore;
    }

    public Integer getXlScore() {
        return xlScore;
    }

    public void setXlScore(Integer xlScore) {
        this.xlScore = xlScore;
    }

    public Integer getXxlScore() {
        return xxlScore;
    }

    public void setXxlScore(Integer xxlScore) {
        this.xxlScore = xxlScore;
    }

    private static List<Player> players;

    private String name;
    private String country;
    private Integer totalScore;
    private Integer xsScore;
    private Integer sScore;
    private Integer mScore;
    private Integer lScore;
    private Integer xlScore;
    private Integer xxlScore;

}
