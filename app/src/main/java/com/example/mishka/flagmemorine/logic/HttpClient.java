package com.example.mishka.flagmemorine.logic;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lab1 on 22.01.2018.
 */

public class HttpClient extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        String temp = "";
        try {
            if (strings.length == 0)
                temp = roomListRequest();
            else if (strings.length == 1)
                temp = createBattleField(strings[0]);
            else if (strings.length == 2)
                temp = createRoom(strings[0], strings[1]);
            else if (strings.length == 3)
                temp = getElement(strings[0], strings[1], strings[2]);
        }catch (Exception e){
            e.printStackTrace(System.out);
            Log.d(LOG_TAG, "exception");
        }
        return temp;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String createBattleField(String size)
            throws Exception {

        String index;

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getMainServlet())
                .addQueryParameter(Data.getBattleFieldServletParameter(),size)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.d(LOG_TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            index = response.body().string();
            Log.d(LOG_TAG, "" + index);
            return index;
        }

//        return Integer.parseInt(index);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getElement(String rowIndex, String columnIndex, String battleFieldIndex)
            throws Exception {

        String resp = "";

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getBattleFieldServlet())
                .addQueryParameter("rowIndex",rowIndex)
                .addQueryParameter("columnIndex",columnIndex)
                .addQueryParameter("battleFieldIndex",battleFieldIndex)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.d(LOG_TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            resp = response.body().string();
            Log.d(LOG_TAG, resp);
        }
        return resp;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String createRoom(String name, String battleFieldSize)
            throws Exception {

        String index = "";

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getCreateRoomServlet())
                .addQueryParameter("roomName", name)
                .addQueryParameter("battleFieldSize", battleFieldSize)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        Log.d(LOG_TAG, "room name URL = " + httpUrl.toString());

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            index = response.body().string();
            Log.d(Data.getLOG_TAG(), "" + index);
            return index;
        }

//        return Integer.parseInt(index);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String roomListRequest()
            throws Exception {

        String roomList = "";

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getRoomListRequestServlet())
                .addQueryParameter("roomList", "all")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        Log.d(LOG_TAG, "room name URL = " + httpUrl.toString());

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            roomList = response.body().string();
            Log.d(Data.getLOG_TAG(), "" + roomList);
            return roomList;
        }

//        return Integer.parseInt(index);
    }

    private String LOG_TAG = "flagmemorine";
    private final OkHttpClient client = new OkHttpClient();
}