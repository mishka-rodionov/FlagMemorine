package com.example.mishka.flagmemorine.logic;

/**
 * Created by Lab1 on 22.01.2018.
 */

public class Data {
    public static String getCustomURL(){
        return customURL;
    }

    public static String getLOG_TAG() {
        return LOG_TAG;
    }

    public static void setLOG_TAG(String LOG) {
        LOG_TAG = LOG;
    }



    public static String getServerAppName() {
        return serverAppName;
    }

    public static void setServerAppName(String serverAppName) {
        Data.serverAppName = serverAppName;
    }

    public static String getMainServlet() {
        return mainServlet;
    }

    public static void setMainServlet(String mainServlet) {
        Data.mainServlet = mainServlet;
    }

    public static String getRoomServlet() {
        return roomServlet;
    }

    public static void setRoomServlet(String roomServlet) {
        Data.roomServlet = roomServlet;
    }

    public static String getBattleFieldServlet() {
        return battleFieldServlet;
    }

    public static void setBattleFieldServlet(String battleFieldServlet) {
        Data.battleFieldServlet = battleFieldServlet;
    }

    public static String getBattleFieldServletParameter() {
        return battleFieldServletParameter;
    }

    public static void setBattleFieldServletParameter(String battleFieldServletParameter) {
        Data.battleFieldServletParameter = battleFieldServletParameter;
    }

    private static String customURL = "82.202.246.170";
    private static String LOG_TAG = "flagmemorine";
    private static String serverAppName = "TestGet";
    private static String mainServlet = "hello";
    private static String roomServlet = "rooms";
    private static String battleFieldServlet = "getElement";
    private static String battleFieldServletParameter = "size";

}
