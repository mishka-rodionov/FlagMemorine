package com.example.mishka.flagmemorine.logic;

import com.example.mishka.flagmemorine.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Lab1 on 22.01.2018.
 */

public class Data {

    //region Setters
    public static void setLOG_TAG(String LOG) {
        LOG_TAG = LOG;
    }

    public static void setServerAppName(String serverAppName) {
        Data.serverAppName = serverAppName;
    }

    public static void setMainServlet(String mainServlet) {
        Data.mainServlet = mainServlet;
    }

    public static void setCreateRoomServlet(String createRoomServlet) {
        Data.createRoomServlet = createRoomServlet;
    }

    public static void setBattleFieldServlet(String battleFieldServlet) {
        Data.battleFieldServlet = battleFieldServlet;
    }

    public static void setBattleFieldServletParameter(String battleFieldServletParameter) {
        Data.battleFieldServletParameter = battleFieldServletParameter;
    }

    public static void setRoomListRequestServlet(String roomListRequestServlet) {
        Data.roomListRequestServlet = roomListRequestServlet;
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

    public static void setConnectToRoomServlet(String connectToRoomServlet) {
        Data.connectToRoomServlet = connectToRoomServlet;
    }

    public static void setGetElementRoomServlet(String getElementRoomServlet) {
        Data.getElementRoomServlet = getElementRoomServlet;
    }
    //endregion



    //region Getters
    public static String getCustomURL(){
        return customURL;
    }

    public static String getLOG_TAG() {
        return LOG_TAG;
    }

    public static String getServerAppName() {
        return serverAppName;
    }

    public static String getMainServlet() {
        return mainServlet;
    }

    public static String getCreateRoomServlet() {
        return createRoomServlet;
    }

    public static String getBattleFieldServlet() {
        return battleFieldServlet;
    }

    public static String getBattleFieldServletParameter() {
        return battleFieldServletParameter;
    }

    public static String getRoomListRequestServlet() {
        return roomListRequestServlet;
    }

    public static String getTestAnotherPlayerChoiceServlet() {
        return testAnotherPlayerChoiceServlet;
    }

    public static String getConnectToRoomServlet() {
        return connectToRoomServlet;
    }

    public static String getGetElementRoomServlet() {
        return getElementRoomServlet;
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

    public static int getIdsmall(int index) {
        return idsmall[index];
    }

    public static int getIdmedium(int index) {
        return idmedium[index];
    }

    public static int getIdlarge(int index) {
        return idlarge[index];
    }

    public static int getIdxlarge(int index) {
        return idxlarge[index];
    }

    public static String getDbName() {
        return dbName;
    }

    public static int getDbVersion() {
        return dbVersion;
    }

    public static String getDbUserNameColumn() {
        return dbUserNameColumn;
    }

    public static String getDbLoginColumn() {
        return dbLoginColumn;
    }

    public static String getDbCountryColumn() {
        return dbCountryColumn;
    }

    public static String getDbBFColumn() {
        return dbBFColumn;
    }

    public static String getDbDateColumn() {
        return dbDateColumn;
    }

    public static String getDbStepColumn() {
        return dbStepColumn;
    }

    public static String getDbScoreColumn() {
        return dbScoreColumn;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getLogin() {
        return login;
    }

    public static String getUserCountry() {
        return userCountry;
    }

    public static String getDbStatisticTable() {
        return dbStatisticTable;
    }

    public static String getDbRecordTable() {
        return dbRecordTable;
    }

    public static String getDbGameTimeColumn() {
        return dbGameTimeColumn;
    }

    public static String getDbUserInfoTable() { return dbUserInfoTable; }

    public static Long getMillisInHour() {
        return millisInHour;
    }

    public static Long getMillisInDay() {
        return millisInDay;
    }

    //endregion

    public static String getCurrentDate(){
        return new Date().toString();
    }

    //region Private static fields
    private static String customURL = "192.168.1.33";
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
    private static String dbName  = "FlagMem";
    private static String dbStatisticTable  = "Statistic";
    private static String dbRecordTable  = "Record";
    private static String dbUserInfoTable  = "UserInfo";
    private static String dbUserNameColumn = "UserName";
    private static String dbLoginColumn = "Login";
    private static String dbCountryColumn = "Country";
    private static String dbBFColumn = "BF";
    private static String dbDateColumn = "Date";
    private static String dbStepColumn = "Step";
    private static String dbScoreColumn = "Score";
    private static String dbGameTimeColumn = "GameTime";
    private static String userName = "User001";
    private static String login = "Login001";
    private static String userCountry = "Russia";

    private static Long millisInHour = 3600000l;
    private static Long millisInDay = 84000000l;
    private static int dbVersion = 1;
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
    private static int[] idsmall = {R.id.small1,R.id.small2,R.id.small3,R.id.small4,
            R.id.small5,R.id.small6,R.id.small7,R.id.small8,R.id.small9,R.id.small10,R.id.small11,R.id.small12};
    private static int[] idmedium = {R.id.medium1,
            R.id.medium2,R.id.medium3,R.id.medium4,R.id.medium5,R.id.medium6,R.id.medium7,R.id.medium8,
            R.id.medium9,R.id.medium10,R.id.medium11,R.id.medium12,R.id.medium13,R.id.medium14,R.id.medium15,
            R.id.medium16};
    private static int[] idlarge = {R.id.large1,R.id.large2,R.id.large3,R.id.large4,
            R.id.large5,R.id.large6,R.id.large7,R.id.large8,R.id.large9,R.id.large10,R.id.large11,
            R.id.large12,R.id.large13,R.id.large14,R.id.large15,R.id.large16,R.id.large17,R.id.large18,
            R.id.large19,R.id.large20,R.id.large21,R.id.large22,R.id.large23,R.id.large24};
    private static int[] idxlarge = {R.id.xlarge1,R.id.xlarge2,R.id.xlarge3,R.id.xlarge4,
            R.id.xlarge5,R.id.xlarge6,R.id.xlarge7,R.id.xlarge8,R.id.xlarge9,R.id.xlarge10,R.id.xlarge11,
            R.id.xlarge12,R.id.xlarge13,R.id.xlarge14,R.id.xlarge15,R.id.xlarge16,R.id.xlarge17,R.id.xlarge18,
            R.id.xlarge19,R.id.xlarge20,R.id.xlarge21,R.id.xlarge22,R.id.xlarge23,R.id.xlarge24,R.id.xlarge25,
            R.id.xlarge26,R.id.xlarge27,R.id.xlarge28,R.id.xlarge29,R.id.xlarge30};
    //endregion


}
