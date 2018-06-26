package com.rodionov.mishka.flagmemorine.logic;

import android.util.Log;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Lab1 on 22.01.2018.
 */

public class HttpClient{

    public Request availableUsers(String username, String online)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getAvailableUsersServlet())
                .addQueryParameter(Data.getOnline(), online)
                .addQueryParameter(Data.getUsername(), username)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request connectToRoom(String playerName, String user, String origin, String size)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getMainServlet())
                .addQueryParameter(Data.getPlayername(), playerName)
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

    public Request getTotalTop(String username)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getTotalTopServlet())
                .addQueryParameter(Data.getUsername(), username)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request getUsername(String json)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getStartServlet())
                .build();

        final MediaType JSON = MediaType.parse("multipart/form-data");

        RequestBody body = RequestBody.create(JSON, json);
        Log.i(Data.getLOG_TAG(), "url: " + httpUrl.toString() + " " + body.toString());

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .post(body)
                .build();

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

    public Request sendValue(String name, String step, String roomIndex)
    {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getWaitServlet())
                .addQueryParameter(Data.getType(), Data.getSend())
                .addQueryParameter(Data.getPlayername(), name)
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
                .addQueryParameter(Data.getPlayername(), name)
                .addQueryParameter(Data.getRoomIndexLabel(), roomIndex)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .build();

        return request;
    }

    public Request postResultToDB(String json){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(Data.getCustomURL())
                .port(8080)
                .addPathSegment(Data.getServerAppName())
                .addPathSegment(Data.getPushResultToDB())
                .build();

        final MediaType JSON = MediaType.parse("multipart/form-data");

        RequestBody body = RequestBody.create(JSON, json);
        Log.i(Data.getLOG_TAG(), "url: " + httpUrl.toString() + " " + body.toString());

        Request request = new Request.Builder()
                .url(httpUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .post(body)
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

    private String LOG_TAG = "flagmemorine";
    private final OkHttpClient client = new OkHttpClient();
    private String battlefield;
}