package com.rodionov.mishka.flagmemorine.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lab on 19.07.2018.
 */

public class PersonalStat {

    public PersonalStat(){};

    public PersonalStat(String lclNm, String lclOrgn, String enmUsrnm, String enmNm, String enmOrgn){
        this.localName = lclNm;
        this.localOrigin = lclOrgn;
        this.enemyUsername = enmUsrnm;
        this.enemyOrigin = enmOrgn;
        this.enemyName = enmNm;
        this.score = 0;
        this.enemyScore = 0;
        this.gameCount = 0;
    }

    public void addScore(){ score++; }

    public void addEnemyScore(){ enemyScore++; }

    public void addGameCount(){ gameCount++; }

    public static void addPersonalStat(PersonalStat personalStat){personalStatList.add(personalStat);}

    public static void clearPersonalStatList(){ personalStatList.clear(); }

    public static void initPersonalStatList(){
        personalStatList = new ArrayList<PersonalStat>();
    }

    public static List<PersonalStat> getPersonalStatList(){ return personalStatList; }

    public String getEnemyOrigin() {
        return enemyOrigin;
    }

    public void setEnemyOrigin(String enemyOrigin) {
        this.enemyOrigin = enemyOrigin;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getEnemyScore() {
        return enemyScore;
    }

    public void setEnemyScore(int enemyScore) {
        this.enemyScore = enemyScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLocalOrigin() {
        return localOrigin;
    }

    public void setLocalOrigin(String localOrigin) {
        this.localOrigin = localOrigin;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public String getEnemyUsername() {
        return enemyUsername;
    }

    public void setEnemyUsername(String enemyUsername) {
        this.enemyUsername = enemyUsername;
    }

    private String enemyOrigin;
    private String enemyName;
    private String localOrigin;
    private String localName;
    private String enemyUsername;
    private int enemyScore;
    private int score;
    private int gameCount;
    private static List<PersonalStat> personalStatList;
}
