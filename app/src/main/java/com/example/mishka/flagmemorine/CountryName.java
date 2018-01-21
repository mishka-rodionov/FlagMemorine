package com.example.mishka.flagmemorine;

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
 * Created by mishka on 21/01/18.
 */
public class CountryName extends AsyncTask<Integer, Void, String> {
    @Override
    protected String doInBackground(Integer... params) {
        String temp = "";
        try {
            temp = doGet(params[0], params[1], params[2]);
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
    public String doGet(int rowIndex, int columnIndex, int battleFieldIndex)
            throws Exception {

        String resp = "";

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(customURL)
                .port(8080)
                .addPathSegments("TestGet/getElement")
                .addQueryParameter("rowIndex",Integer.toString(rowIndex))
                .addQueryParameter("columnIndex",Integer.toString(columnIndex))
                .addQueryParameter("battleFieldIndex",Integer.toString(battleFieldIndex))
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

    private String LOG_TAG = "flagmemorine";
    private OkHttpClient client = new OkHttpClient();
    private String customURL = "82.202.246.170";
}

