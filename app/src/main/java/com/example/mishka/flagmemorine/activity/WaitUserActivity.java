package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.BattleField;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.HttpClient;
import com.example.mishka.flagmemorine.service.SqLiteTableManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class WaitUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_user);
        hideSystemUI();
        waitUserToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.waituser_toolbar);
        setSupportActionBar(waitUserToolbar);
        ActionBar acBar  = getSupportActionBar();
//        acBar.setTitle("This is text");
        acBar.setDisplayHomeAsUpEnabled(true);

        sqLiteTableManager = new SqLiteTableManager(WaitUserActivity.this);

        client = new OkHttpClient();
        httpClient = new HttpClient();

        battlefieldBody = new ArrayList<String>();
        requestTimer = new Timer();

        firstPlayerName = (TextView) findViewById(R.id.firstPlayerName);
        secondPlayerName = (TextView) findViewById(R.id.secondPlayerName);

        battlefieldSize = Integer.parseInt(getIntent().getStringExtra(Data.getSize()));
        Log.d(Data.getLOG_TAG(), "onCreate: battlefieldSize = " + battlefieldSize);

        pullDB();
        connectToRoom(httpClient, playerName, username, origin, Integer.toString(battlefieldSize));

    }

    public void connectToRoom(final HttpClient httpClient, String playerName, String user, String origin, String size){

        final Handler mainHandler = new Handler(Looper.getMainLooper());

        client.newCall(httpClient.connectToRoom(playerName, user, origin, size)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final IOException ex = e;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Data.getLOG_TAG(), "run: " + "Fail!!!!!!!!!!!!");
                        Log.i(Data.getLOG_TAG(), "connectTORoom run: " + ex.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String answer = response.body().string();
                final String[] body = answer.split(" ");
                Log.i(Data.getLOG_TAG(), "onResponse run: " + answer);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        roomIndex = Integer.parseInt(body[0]);
                        Log.i(Data.getLOG_TAG(), "room index is = " + roomIndex);
                        playerNumber = body[1];
//                        if (playerNumber.equals("firstPlayer")){
//                            sending = true;
//                            receiving = false;
//                        }else{
//                            sending = false;
//                            receiving = true;
//                            requestTimer.schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    receiving(httpClient, Integer.toString(roomIndex));
//                                }
//                            }, delay, period);
//                        }

                        for (int i = 3; i < body.length; i+=2) {
//                            if(body[i].contains("_")){
//                                body[i] = body[i].replaceAll("_", " ");
//                            }
                            Log.i(Data.getLOG_TAG(), "body[i] " + body[i]);
                            battlefieldBody.add(body[i]);
                        }

                        if (playerNumber.equals("firstPlayer")) {
                            requestTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    waitUser(httpClient, Integer.toString(roomIndex));
                                }
                            }, delay, period);
                        }
                        if (playerNumber.equals("secondPlayer")){
                            waitUser(httpClient, Integer.toString(roomIndex));
                        }
//                        battleField = new BattleField(battlefieldBody);
//                        initFlipView(view, battleFieldSize);
//                        if(receiving){
//                            for (int i = 0; i < clickable.size(); i++) {
//                                if (clickable.get(i)) {
//                                    flipViews.get(i).setEnabled(false);
//                                }
//                            }
//                        }
                    }
                });
            }
        });
    }

    public void waitUser(HttpClient httpClient, final String roomIndex){
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        client.newCall(httpClient.waitUser(roomIndex)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                ;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        view.setText(battlefield);
                        Log.i(Data.getLOG_TAG(), "run: " + "Fail!!!!!!!!!!!!");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                final String[] answer = response.body().string().split(" ");
                final String[] answer = response.body().string().split(" ");
                Log.i(Data.getLOG_TAG(), "onResponse run for WAITUSER methods: " + answer);
                final String firstPlayer = answer[0];
                final String secondPlayer = answer[1];
                Log.i(Data.getLOG_TAG(), "onResponse secondplayer: " + secondPlayer);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        firstPlayerName.setText(firstPlayer);
                        if (!secondPlayer.equals("null")){
                            secondPlayerName.setText(secondPlayer);
                            String body = "";
                            for (int i = 0; i < battlefieldBody.size(); i++) {
                                body += battlefieldBody.get(i) + " ";
                            }
                            Intent roomBattlefieldIntent = new Intent(WaitUserActivity.this, RoomBattleFieldActivity.class);
                            roomBattlefieldIntent.putExtra(Data.getRoomIndexLabel(), roomIndex);
                            roomBattlefieldIntent.putExtra(Data.getPlayerName(), playerNumber);
                            if (playerNumber.equals("firstPlayer")){
                                roomBattlefieldIntent.putExtra("anotherPlayer", secondPlayer);
                            }
                            if (playerNumber.equals("secondPlayer")){
                                roomBattlefieldIntent.putExtra("anotherPlayer", firstPlayer);
                            }
                            roomBattlefieldIntent.putExtra("battlefieldBody", body);
                            roomBattlefieldIntent.putExtra(Data.getSize(), Integer.toString(battlefieldSize));
                            startActivity(roomBattlefieldIntent);
                        }
                    }
                });
            }
        });
    }

    private void pullDB(){
        playerName = sqLiteTableManager.getName() == null ?
                sqLiteTableManager.getLogin() : sqLiteTableManager.getName();
        origin = sqLiteTableManager.getCountry() == null ?
                "Olympics" : sqLiteTableManager.getCountry();
        username = sqLiteTableManager.getLogin();
        Log.i(Data.getLOG_TAG(), "pullDB: playerName = " + playerName);
        Log.i(Data.getLOG_TAG(), "pullDB: username = " + username);
        Log.i(Data.getLOG_TAG(), "pullDB: origin = " + origin);
    }

    private void hideSystemUI(){
        View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestTimer.cancel();
    }


    @Override
    protected void onStop() {
        super.onStop();
        requestTimer.cancel();
        Log.i(Data.getLOG_TAG(), "onStop: WaitUserActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestTimer = new Timer();
        requestTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                waitUser(httpClient, Integer.toString(roomIndex));
            }
        }, delay, period);
        Log.i(Data.getLOG_TAG(), "onRestart: WaitUserActivity");
    }

    private android.support.v7.widget.Toolbar waitUserToolbar;

    private SqLiteTableManager sqLiteTableManager;

    private Integer roomIndex;
    private Integer delay = 500;
    private Integer period = 1000;
    private Integer battlefieldSize;
    private String playerNumber;
    private String playerName;
    private String username;
    private String origin;
    private ArrayList<String> battlefieldBody;

    private HttpClient httpClient;
    private OkHttpClient client;

    private Timer requestTimer;

    private TextView firstPlayerName;
    private TextView secondPlayerName;
}
