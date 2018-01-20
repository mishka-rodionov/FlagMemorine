package com.example.mishka.flagmemorine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //************************
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        userChoice = new ArrayList<>(2);
        CountryList.loadCountryMap();
        //************************

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            //При нажатии на кнопку Play на сервер отправляется get запрос на создание игрового
            // поля размером 6*6. Сервер возвращает индекс хранения текущего игрового поля в
            // контейнере игровых полей.
            new AsyncTask<Void, String, Integer>(){
                @Override
                protected Integer doInBackground(Void... params) {
                    int result = 0;
                    try {
                        result = doGet(battleFieldSize);
                        Log.d(LOG_TAG, "result is = " + result);
                    }catch (Exception e){
                        e.printStackTrace(System.out);
                        Log.d(LOG_TAG, "exception");
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    super.onPostExecute(integer);
                    Log.d(LOG_TAG, "integer is = " + integer);
                    battleFieldIndex = integer;
                }
            }.execute();

            Log.d(LOG_TAG, "index of container battle field = " + battleFieldIndex);

            View view  = getLayoutInflater().inflate(R.layout.layout_6_6, null);
            final TextView result = (TextView) view.findViewById(R.id.result);
            relativeLayout.addView(view);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Log.d(LOG_TAG, "press button");
                    final int rowIndex = rowIndexCalc(view.getTag().toString());
                    final int columnIndex = columnIndexCalc(view.getTag().toString());
                    String[] country = new String[1];
                        new AsyncTask<Void, String, String>(){
                            String temp = "";
                            @Override
                            protected String doInBackground(Void... params) {
                                try {
                                     temp = doGet(rowIndex, columnIndex, battleFieldIndex);
                                }catch (Exception e){
                                    e.printStackTrace(System.out);
                                    Log.d(LOG_TAG, "exception");
                                }
                                return temp;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                result.setText(s);
                                int resource = CountryList.getCountry(s);
                                imageButtonArrayList.get(Integer.parseInt(view.getTag().toString()))
                                        .setImageResource(resource);
                                userChoice.add(s);
                            }
                        }.execute();
                }
            };

            imageButtonArrayList = new ArrayList<>(battleFieldSize*battleFieldSize);
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton2));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton3));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton4));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton5));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton6));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton7));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton8));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton9));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton10));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton11));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton12));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton13));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton14));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton15));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton16));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton17));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton18));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton19));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton20));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton21));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton22));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton23));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton24));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton25));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton26));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton27));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton28));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton29));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton30));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton31));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton32));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton33));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton34));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton35));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton36));
            imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton37));
            Button send = (Button) view.findViewById(R.id.buttonSend);
            send.setOnClickListener(onClickListener);
            for (int i = 0; i < battleFieldSize*battleFieldSize; i++) {
                imageButtonArrayList.get(i).setOnClickListener(onClickListener);
            }
            imageButtonArrayList.get(1).setImageResource(R.drawable.denmark);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Вычисление индекса столбца по тэгу нажатой кнопки.
    public int rowIndexCalc(String viewTag){
        int tag = Integer.parseInt(viewTag);
        Log.d(LOG_TAG, "input tag = " + viewTag);
        Log.d(LOG_TAG, "row index = " + tag/battleFieldSize);
        return tag/battleFieldSize;
    }

    //Вычисление индекса столбца по тэгу нажатой кнопки.
    public int columnIndexCalc(String viewTag){
        int tag = Integer.parseInt(viewTag);
        if (tag < battleFieldSize){
            Log.d(LOG_TAG, "input tag = " + viewTag);
            Log.d(LOG_TAG, "column index = " + tag);
            return tag;
        }else{
            Log.d(LOG_TAG, "input tag = " + viewTag);
            Log.d(LOG_TAG, "column index = " + tag%battleFieldSize);
            return tag%battleFieldSize;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int doGet(int size)
            throws Exception {

        int index;

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(customURL)
                .port(8080)
                .addPathSegments("TestGet/hello")
                .addQueryParameter("size",Integer.toString(size))
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
            index = Integer.parseInt(response.body().string());
            Log.d(LOG_TAG, "" + index);
            return index;
        }

//        return Integer.parseInt(index);
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

    private RelativeLayout relativeLayout;
    private String LOG_TAG = "flagmemorine";
    private ArrayList<ImageButton> imageButtonArrayList;
    private String customURL = "82.202.246.170";
    private final OkHttpClient client = new OkHttpClient();
    private int battleFieldSize = 6;
    private int battleFieldIndex = 0;
    private ArrayList<String> userChoice;
}
