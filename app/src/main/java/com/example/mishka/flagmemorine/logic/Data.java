package com.example.mishka.flagmemorine.logic;

import com.example.mishka.flagmemorine.R;

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

    public static String getTestAnotherPlayerChoiceServlet() {
        return testAnotherPlayerChoiceServlet;
    }

    public static void setTestAnotherPlayerChoiceServlet(String testAnotherPlayerChoiceServlet) {
        Data.testAnotherPlayerChoiceServlet = testAnotherPlayerChoiceServlet;
    }

    public static String getWaitSecondPlayerServlet() {
        return waitSecondPlayerServlet;
    }

    public static void setWaitSecondPlayerServlet(String waitSecondPlayerServlet) {
        Data.waitSecondPlayerServlet = waitSecondPlayerServlet;
    }

    public static String getConnectToRoomServlet() {
        return connectToRoomServlet;
    }

    public static void setConnectToRoomServlet(String connectToRoomServlet) {
        Data.connectToRoomServlet = connectToRoomServlet;
    }

    public static String getGetElementRoomServlet() {
        return getElementRoomServlet;
    }

    public static void setGetElementRoomServlet(String getElementRoomServlet) {
        Data.getElementRoomServlet = getElementRoomServlet;
    }

    public static int getXsmallSize() {
        return xsmallSize;
    }

    public static int getSmallSize() {
        return smallSize;
    }

    public static int getMediumSize() {
        return mediumSize;
    }

    public static int getLargeSize() {
        return largeSize;
    }

    public static int getXlargeSize() {
        return xlargeSize;
    }

    public static int getXxlargeSize() {
        return xxlargeSize;
    }

    public static int getIdxsmall(int index) {
        return idxsmall[index];
    }

    public static int getIdxxlarge(int index) {
        return idxxlarge[index];
    }

    private static String customURL = "82.202.246.170";
    private static String LOG_TAG = "flagmemorine";
    private static String serverAppName = "TestGet";
    private static String mainServlet = "hello";
    private static String createRoomServlet = "createRoom";
    private static String roomListRequestServlet = "getRoomList";
    private static String testAnotherPlayerChoiceServlet = "testAnotherPlayerChoice";
    private static String waitSecondPlayerServlet = "waitSecondPlayer";
    private static String connectToRoomServlet = "connectToRoom";
    private static String getElementRoomServlet = "getElementRoom";
    private static String battleFieldServlet = "getElement";
    private static String battleFieldServletParameter = "size";
    private static int xsmallSize = 8;
    private static int smallSize = 12;
    private static int mediumSize = 16;
    private static int largeSize = 24;
    private static int xlargeSize = 30;
    private static int xxlargeSize = 36;
    private static int[] idxxlarge = {R.id.xxlarge1,R.id.xxlarge2,R.id.xxlarge3,R.id.xxlarge4,
        R.id.xxlarge5,R.id.xxlarge6,R.id.xxlarge7,R.id.xxlarge8,R.id.xxlarge9,R.id.xxlarge10,R.id.xxlarge11,
        R.id.xxlarge12,R.id.xxlarge13,R.id.xxlarge14,R.id.xxlarge15,R.id.xxlarge16,R.id.xxlarge17,R.id.xxlarge18,
        R.id.xxlarge19,R.id.xxlarge20,R.id.xxlarge21,R.id.xxlarge22,R.id.xxlarge23,R.id.xxlarge24,R.id.xxlarge25,
        R.id.xxlarge26,R.id.xxlarge27,R.id.xxlarge28,R.id.xxlarge29,R.id.xxlarge30,R.id.xxlarge31,R.id.xxlarge32,
        R.id.xxlarge33,R.id.xxlarge34,R.id.xxlarge35,R.id.xxlarge36};
    private static int[] idxsmall = {R.id.xsmall1,R.id.xsmall2,R.id.xsmall3,R.id.xsmall4,
            R.id.xsmall5,R.id.xsmall6,R.id.xsmall7,R.id.xsmall8};


}
