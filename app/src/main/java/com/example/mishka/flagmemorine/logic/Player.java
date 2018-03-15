package com.example.mishka.flagmemorine.logic;

import android.content.pm.InstrumentationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mishka on 15/03/18.
 */

public class Player {

    public Player(String name, String country, Integer totalScore, Integer xsScore,
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
