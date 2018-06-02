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

public class HttpClient{
    public Request connectToRoom(String playerName, String user, String origin, String size)
            {

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