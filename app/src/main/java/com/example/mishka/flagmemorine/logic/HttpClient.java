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
                .addQueryParameter("playerName", playerName)
                .addQueryParameter("user", user)
                .addQueryParameter("origin", origin)
                .addQueryParameter("size", size)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

                Log.d(LOG_TAG, "connect to room URL = " + httpUrl.toString());

//        final Handler mainHandler = new Handler(Looper.getMainLooper());
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                battlefield = "Fail!!!!!!!!!!!!";
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
////                        view.setText(battlefield);
//                        Log.i(Data.getLOG_TAG(), "run: " + battlefield);
//                    }
//                });
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.i(Data.getLOG_TAG(), "onResponse run: " + response.body().string());
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
////                        Log.i(Data.getLOG_TAG(), "onResponse run: " + response.body().string());
//                    }
//                });
//            }
//        });

//        Handler mainHandler = new Handler(Looper.getMainLooper());
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
//            battlefield = response.body().string();
//            Log.d(Data.getLOG_TAG(), "" + battlefield);
//            return battlefield;
//        }
        return request;
//        return Integer.parseInt(index);
    }

    public Request sendValue(String roomIndex, String step1, String step2, String sendStart,
                             String sendFinish, String readStart, String readFinish)
            {


        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getWaitServlet())
                .addQueryParameter(Data.getRoomIndexLabel(), roomIndex)
                .addQueryParameter(Data.getStep1(), step1)
                .addQueryParameter(Data.getStep2(), step2)
                .addQueryParameter(Data.getSendStart(), sendStart)
                .addQueryParameter(Data.getSendFinish(), sendFinish)
                .addQueryParameter(Data.getReadStart(), readStart)
                .addQueryParameter(Data.getReadFinish(), readFinish)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        Log.d(LOG_TAG, "send value URL = " + httpUrl.toString());
//        String answer = "";
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            answer = response.body().string();
//            Log.d(Data.getLOG_TAG(), "" + answer);
//            return answer;
//        }
        return request;
//        return Integer.parseInt(index);
    }

    public Request stepWait(String roomIndex, String activePlayer)
            {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getWaitServlet())
                .addQueryParameter("roomIndex", roomIndex)
                .addQueryParameter("activePlayer", activePlayer)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        Log.d(LOG_TAG, "step wait URL = " + httpUrl.toString());
        String answer = "";

//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            answer = response.body().string();
//            Log.d(Data.getLOG_TAG(), "" + answer);
//            return answer;
//        }

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

        Log.d(LOG_TAG, "step wait URL = " + httpUrl.toString());
        String answer = "";

//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            answer = response.body().string();
//            Log.d(Data.getLOG_TAG(), "" + answer);
//            return answer;
//        }

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

        Log.d(LOG_TAG, "step wait URL = " + httpUrl.toString());
        String answer = "";

//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                Log.d(Data.getLOG_TAG(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//            answer = response.body().string();
//            Log.d(Data.getLOG_TAG(), "" + answer);
//            return answer;
//        }

        return request;
    }
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public String getElementRoom(String rowIndex, String columnIndex, String roomIndex, String player)
//            throws Exception {
//
//        String resp = "";
//
//        HttpUrl httpUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host(Data.getCustomURL())
//                .port(8080)
//                .addPathSegment(Data.getServerAppName())
//                .addPathSegment(Data.getActivePlayerServlet())
//                .addQueryParameter("rowIndex",rowIndex)
//                .addQueryParameter("columnIndex",columnIndex)
//                .addQueryParameter("roomIndex",roomIndex)
//                .addQueryParameter("player",player)
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
//            Log.d(LOG_TAG, "get element room = " + resp);
//        }
//        return resp;
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public String testAnotherPlayerChoice(String roomIndex, String player)
//            throws Exception {
//
//        String resp = "";
//
//        HttpUrl httpUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host(Data.getCustomURL())
//                .port(8080)
//                .addPathSegment(Data.getServerAppName())
//                .addPathSegment(Data.getTestAnotherPlayerChoiceServlet())
//                .addQueryParameter("roomIndex",roomIndex)
//                .addQueryParameter("player",player)
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
//            Log.d(LOG_TAG, "test player choice = " + resp);
//        }
//        return resp;
//    }

    private String LOG_TAG = "flagmemorine";
    private final OkHttpClient client = new OkHttpClient();
    private String battlefield;
}