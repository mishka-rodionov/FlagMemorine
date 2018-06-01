package com.example.mishka.flagmemorine.logic;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Lab1 on 22.01.2018.
 */

public class HttpClient/* extends AsyncTask<String, Void, String>*/{
  /*  @Override
    protected String doInBackground(String... strings) {
        String temp = "";
        try {
            switch(strings[0]){
                case "roomListRequest":
//                    temp = roomListRequest();
                    break;
                case "createBattleField":
//                    temp = createBattleField(strings[1]);
                    break;
                case "stepWait":
                    temp = stepWait(strings[1], strings[2]);
                    break;
                case "sendValue":
                    temp = sendValue(strings[1], strings[2], strings[3], strings[4]);
                    break;
                case "connectToRoom":
                    temp = connectToRoom(strings[1], strings[2], strings[3], strings[4]);
                    break;
                case "getElementRoom":
//                    temp = getElementRoom(strings[1], strings[2], strings[3], strings[4]);
                case "testAnotherPlayerChoice":
//                    temp = testAnotherPlayerChoice(strings[1], strings[2]);
                    break;
            }

        }catch (Exception e){
            e.printStackTrace(System.out);
            Log.d(LOG_TAG, "exception");
        }
        return temp;
    }
*/
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public String createBattleField(String size)
//            throws Exception {
//
//        String index;
//
//        HttpUrl httpUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host(Data.getCustomURL())
//                .port(8080)
//                .addPathSegment(Data.getServerAppName())
//                .addPathSegment(Data.getMainServlet())
//                .addQueryParameter(Data.getBattleFieldServletParameter(),size)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(httpUrl)
//                .header("User-Agent", "OkHttp Headers.java")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(LOG_TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            index = response.body().string();
//            Log.d(LOG_TAG, "" + index);
//            return index;
//        }
//
////        return Integer.parseInt(index);
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public String getElement(String rowIndex, String columnIndex, String battleFieldIndex)
//            throws Exception {
//
//        String resp = "";
//
//        HttpUrl httpUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host(Data.getCustomURL())
//                .port(8080)
//                .addPathSegment(Data.getServerAppName())
//                .addPathSegment(Data.getBattleFieldServlet())
//                .addQueryParameter("rowIndex",rowIndex)
//                .addQueryParameter("columnIndex",columnIndex)
//                .addQueryParameter("battleFieldIndex",battleFieldIndex)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(httpUrl)
//                .header("User-Agent", "OkHttp Headers.java")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(LOG_TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            resp = response.body().string();
//            Log.d(LOG_TAG, resp);
//        }
//        return resp;
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public String createRoom(String name, String battleFieldSize)
//            throws Exception {
//
//        String index = "";
//
//        HttpUrl httpUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host(Data.getCustomURL())
//                .port(8080)
//                .addPathSegment(Data.getServerAppName())
//                .addPathSegment(Data.getCreateRoomServlet())
//                .addQueryParameter("roomName", name)
//                .addQueryParameter("battleFieldSize", battleFieldSize)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(httpUrl)
//                .header("User-Agent", "OkHttp Headers.java")
//                .build();
//
//        Log.d(LOG_TAG, "room name URL = " + httpUrl.toString());
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            index = response.body().string();
//            Log.d(Data.getLOG_TAG(), "" + index);
//            return index;
//        }
//
////        return Integer.parseInt(index);
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public String roomListRequest()
//            throws Exception {
//
//        String roomList = "";
//
//        HttpUrl httpUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host(Data.getCustomURL())
//                .port(8080)
//                .addPathSegment(Data.getServerAppName())
//                .addPathSegment(Data.getRoomListRequestServlet())
//                .addQueryParameter("roomList", "all")
//                .build();
//
//        Request request = new Request.Builder()
//                .url(httpUrl)
//                .header("User-Agent", "OkHttp Headers.java")
//                .build();
//
//        Log.d(LOG_TAG, "room name URL = " + httpUrl.toString());
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            roomList = response.body().string();
//            Log.d(Data.getLOG_TAG(), "" + roomList);
//            return roomList;
//        }
//
////        return Integer.parseInt(index);
//    }

//    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Request connectToRoom(String playerName, String user, String origin, String size)
            {

//        final String battlefield = "";

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getMainServlet())
                .addQueryParameter(Data.getPlayerName(), playerName)
                .addQueryParameter(Data.getUser(), user)
                .addQueryParameter(Data.getOrigin(), origin)
                .addQueryParameter(Data.getSize(), size)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

                Log.d(LOG_TAG, "connect to room URL = " + httpUrl.toString());

        return request;
    }


    public Request send(String text)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment("testServlet")
                .addQueryParameter("type", "send")
                .addQueryParameter("name", "second")
                .addQueryParameter("body", text)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request availableUsers()
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getAvailableUsersServlet())
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request sendValue(String name, String step, String roomIndex)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getWaitServlet())
                .addQueryParameter(Data.getType(), Data.getSend())
                .addQueryParameter(Data.getPlayerName(), name)
                .addQueryParameter(Data.getStep(), step)
                .addQueryParameter(Data.getRoomIndexLabel(), roomIndex)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request waitUser(String roomIndex, String playerNumber)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getUsernameServlet())
                .addQueryParameter(Data.getRoomIndexLabel(), roomIndex)
                .addQueryParameter(Data.getPlayerNumber(), playerNumber)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request receiveValue(String name, String roomIndex)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getWaitServlet())
                .addQueryParameter(Data.getType(), Data.getReceive())
                .addQueryParameter(Data.getPlayerName(), name)
                .addQueryParameter(Data.getRoomIndexLabel(), roomIndex)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request pushResultToDB(String enemyUsername, String enemyPlayername, String enemyOrigin,
                                  String score, String enemyScore, String result, String date, String username)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getPushResultToDB())
                .addQueryParameter(Data.getUsername(), username)
                .addQueryParameter(Data.getEnemyPlayername(), enemyPlayername)
                .addQueryParameter(Data.getEnemyUsername(), enemyUsername)
                .addQueryParameter(Data.getEnemyOrigin(), enemyOrigin)
                .addQueryParameter(Data.getEnemyScore(), enemyScore)
                .addQueryParameter(Data.getScore(), score)
                .addQueryParameter(Data.getResult(), result)
                .addQueryParameter(Data.getDate(), date)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request receive()
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment("testServlet")
                .addQueryParameter("name", "second")
                .addQueryParameter("type", "receive")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request removeRoom(String roomIndex)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getRemoveRoomServlet())
                .addQueryParameter(Data.getRoomIndexLabel(), roomIndex)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request getUsername(String playername, String origin, String username)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getStartServlet())
                .addQueryParameter(Data.getPlayerName(), playername)
                .addQueryParameter(Data.getOrigin(), origin)
                .addQueryParameter(Data.getUsername(), username)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    private String LOG_TAG = "flagmemorine";
    private final OkHttpClient client = new OkHttpClient();
    private String battlefield;
}