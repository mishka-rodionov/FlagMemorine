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

    public static String getCreateRoomServlet() {
        return createRoomServlet;
    }

    public static void setCreateRoomServlet(String createRoomServlet) {
        Data.createRoomServlet = createRoomServlet;
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

    public static String getRoomListRequestServlet() {
        return roomListRequestServlet;
    }

    public static void setRoomListRequestServlet(String roomListRequestServlet) {
        Data.roomListRequestServlet = roomListRequestServlet;
    }

    private static String customURL = "82.202.246.170";
    private static String LOG_TAG = "flagmemorine";
    private static String serverAppName = "TestGet";
    private static String mainServlet = "hello";
    private static String createRoomServlet = "createRoom";
    private static String roomListRequestServlet = "rooms";
    private static String battleFieldServlet = "getElement";
    private static String battleFieldServletParameter = "size";

}
