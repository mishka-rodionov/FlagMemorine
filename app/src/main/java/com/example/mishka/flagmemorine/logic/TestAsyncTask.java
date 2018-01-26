package com.example.mishka.flagmemorine.logic;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lab1 on 22.01.2018.
 */

public class TestAsyncTask extends AsyncTask<ArrayList<String>, Void, ArrayList<String>>{
    @Override
    protected ArrayList<String> doInBackground(ArrayList<String>... strings) {
        String temp;
        try {
            if(strings[0].get(0).equals(strings[0].get(1))){
                temp = doGet("true");
            }else{
                temp = doGet("false");
            }

        }catch (Exception e){
            e.printStackTrace(System.out);
            Log.d(LOG_TAG, "exception");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
        super.onPostExecute(s);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String doGet(String result)
            throws Exception {

        String answer;

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(customURL)
                .port(8080)
                .addPathSegments("TestGet/result")
                .addQueryParameter("choice",result)
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
            answer = response.body().string();
            Log.d(LOG_TAG, "" + answer);
            return answer;
        }

//        return Integer.parseInt(index);
    }

    private String LOG_TAG = "flagmemorine";
    private final OkHttpClient client = new OkHttpClient();
    private String customURL = Data.customURL;
}