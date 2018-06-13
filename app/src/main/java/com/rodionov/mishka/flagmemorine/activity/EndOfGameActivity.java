package com.rodionov.mishka.flagmemorine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.Facts;
import com.rodionov.mishka.flagmemorine.logic.HttpClient;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class EndOfGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        hideSystemUI();

        endOfGameToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.endofgame_toolbar);
        setSupportActionBar(endOfGameToolbar);
        ActionBar acBar  = getSupportActionBar();
//        acBar.setTitle("This is text");
        acBar.setTitle("");
        acBar.setDisplayHomeAsUpEnabled(true);

        sqLiteTableManager = new SqLiteTableManager(EndOfGameActivity.this);
        client = new OkHttpClient();
        httpClient = new HttpClient();

        activityName = getIntent().getStringExtra("activityName");
        scoreValue = getIntent().getStringExtra("score");
        stepValue = getIntent().getStringExtra("step");
        timeValue = getIntent().getStringExtra("time");
        size = getIntent().getStringExtra("size");
        View view = getView(activityName);

        gameInformation = (LinearLayout) findViewById(R.id.gameInformation);
        gameInformation.addView(view);
        score = (TextView) view.findViewById(R.id.scoreEndOfGame);
        step = (TextView) view.findViewById(R.id.stepEndOfGame);
        time = (TextView) view.findViewById(R.id.timeEndOfGame);
        message = (TextView) view.findViewById(R.id.message);
        factsTextView = (TextView) findViewById(R.id.factsTextView);
        scoreEndOfGamePlayerFirst = (TextView) view.findViewById(R.id.scoreEndOfGamePlayerFirst);
        stepEndOfGamePlayerFirst = (TextView) view.findViewById(R.id.stepEndOfGamePlayerFirst);
        timeEndOfGamePlayerFirst = (TextView) view.findViewById(R.id.timeEndOfGamePlayerFirst);
        messageEndOfGamePlayerFirst = (TextView) view.findViewById(R.id.messageEndOfGamePlayerFirst);
        scoreEndOfGamePlayerSecond = (TextView) view.findViewById(R.id.scoreEndOfGamePlayerSecond);
        stepEndOfGamePlayerSecond = (TextView) view.findViewById(R.id.stepEndOfGamePlayerSecond);
        timeEndOfGamePlayerSecond = (TextView) view.findViewById(R.id.timeEndOfGamePlayerSecond);
        messageEndOfGamePlayerSecond = (TextView) view.findViewById(R.id.messageEndOfGamePlayerSecond);
        firstPlayername = (TextView) view.findViewById(R.id.firstPlayerName);
        secondPlayername = (TextView) view.findViewById(R.id.secondPlayerName);

        if (activityName.equals("RoomBattlefield")){
            roomIndex = getIntent().getStringExtra("roomIndex");
            result = getIntent().getStringExtra("result");
            scoreValueSecondPLayer = getIntent().getStringExtra("scoreValueSecondPlayer");
            stepValueSecondPLayer = getIntent().getStringExtra("stepValueSecondPlayer");
            String fpn = getIntent().getStringExtra("localPlayername");
            String spn = getIntent().getStringExtra("enemyPlayername");
            firstPlayername.setText(fpn);
            secondPlayername.setText(spn);
            anotherPlayerUsername = getIntent().getStringExtra("anotherPlayerUsername");
            anotherPlayerOrigin = getIntent().getStringExtra("anotherPlayerOrigin");
            pushResultToDB(anotherPlayerUsername, scoreValue, scoreValueSecondPLayer, result, Data.getCurrentDate(), sqLiteTableManager.getLogin());
            if (Integer.parseInt(result) < 0){
                createResult("Loser!", Color.RED, "Winner!", Color.GREEN);
            }
            if (Integer.parseInt(result) > 0){
                createResult("Winner!", Color.GREEN, "Loser!", Color.RED);
            }
            if (Integer.parseInt(result) == 0){
                createResult("Draw!", Color.GREEN, "Draw!", Color.GREEN);
            }
        }

        if (activityName.equals("Battlefield")){
            message.setText("Congratulations!");
            message.setTextColor(Color.GREEN);
            score.setText(score.getText().toString() + " " + scoreValue);
            step.setText(step.getText().toString() + " " + stepValue);
            time.setText(time.getText().toString() + " " + timeValue);
        }






        factsTextView.setText(Facts.randomRussiaFacts());


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityName.equals("RoomBattlefield")){
                    removeRoom(roomIndex);
                }
                switch(v.getId()){
                    case R.id.restartButton:
                        if (activityName.equals("Battlefield")){
                            Intent intentBattleFieldActivity = new Intent(EndOfGameActivity.this, BattleFieldActivity.class);
                            intentBattleFieldActivity.putExtra(Data.getSize(), size);
                            startActivity(intentBattleFieldActivity);
                        }
                        if (activityName.equals("RoomBattlefield")){
                            Intent waitUserIntent = new Intent(EndOfGameActivity.this, WaitUserActivity.class);
                            waitUserIntent.putExtra(Data.getSize(), size);
                            startActivity(waitUserIntent);
                        }
                        break;
                    case R.id.homeButton:
                        Intent intentStartActivity = new Intent(EndOfGameActivity.this, StartActivity.class);
                        startActivity(intentStartActivity);
                        break;
                    case R.id.statisticButton:
                        startActivity(new Intent(EndOfGameActivity.this, StatisticActivity.class));
                        break;
                }
            }
        };

        restart = (ImageButton) findViewById(R.id.restartButton);
        home = (ImageButton) findViewById(R.id.homeButton);
        statistic = (ImageButton) findViewById(R.id.statisticButton);

        restart.setOnClickListener(onClickListener);
        home.setOnClickListener(onClickListener);
        statistic.setOnClickListener(onClickListener);
    }

    private void createResult(String playerFirst, int colorPlayerFirst, String playerSecond, int colorPlayerSecond) {
        messageEndOfGamePlayerFirst.setText(playerFirst);
        messageEndOfGamePlayerFirst.setTextColor(colorPlayerFirst);
        scoreEndOfGamePlayerFirst.setText(scoreEndOfGamePlayerFirst.getText().toString() + " " + scoreValue);
        stepEndOfGamePlayerFirst.setText(stepEndOfGamePlayerFirst.getText().toString() + " " + stepValue);
        timeEndOfGamePlayerFirst.setText(timeEndOfGamePlayerFirst.getText().toString() + " " + timeValue);

        messageEndOfGamePlayerSecond.setText(playerSecond);
        messageEndOfGamePlayerSecond.setTextColor(colorPlayerSecond);
        scoreEndOfGamePlayerSecond.setText(scoreEndOfGamePlayerSecond.getText().toString() + " " + scoreValueSecondPLayer);
        stepEndOfGamePlayerSecond.setText(stepEndOfGamePlayerSecond.getText().toString() + " " + stepValueSecondPLayer);
        timeEndOfGamePlayerSecond.setText(timeEndOfGamePlayerSecond.getText().toString() + " " + timeValue);
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

    private View getView(String activityName){
        View view;
        if (activityName.equals("Battlefield")){
            view = getLayoutInflater().inflate(R.layout.singleplayer_end_of_game, null);
        }
        else{
            view = getLayoutInflater().inflate(R.layout.multiplayer_end_of_game, null);
        }
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_restart).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    public void removeRoom(String roomIndex){
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        client.newCall(httpClient.removeRoom(roomIndex)).enqueue(new Callback() {
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
                final String answer = response.body().string();
                Log.i(Data.getLOG_TAG(), "onResponse run for REMOVE_ROOM methods: rooms size = " + answer);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public void pushResultToDB(String enemyUsername, String score, String enemyScore, String result, String date, String username){
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        client.newCall(httpClient.pushResultToDB( enemyUsername,
                 score,  enemyScore,  result,  date,  username)).enqueue(new Callback() {
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
                final String answer = response.body().string();
                Log.i(Data.getLOG_TAG(), "onResponse run for REMOVE_ROOM methods: rooms size = " + answer);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    private TextView score;
    private TextView step;
    private TextView time;
    private TextView message;
    private TextView scoreEndOfGamePlayerFirst;
    private TextView stepEndOfGamePlayerFirst;
    private TextView timeEndOfGamePlayerFirst;
    private TextView messageEndOfGamePlayerFirst;
    private TextView scoreEndOfGamePlayerSecond;
    private TextView stepEndOfGamePlayerSecond;
    private TextView timeEndOfGamePlayerSecond;
    private TextView messageEndOfGamePlayerSecond;
    private TextView factsTextView;
    private TextView firstPlayername;
    private TextView secondPlayername;
    private android.support.v7.widget.Toolbar endOfGameToolbar;

    private LinearLayout gameInformation;

    private ImageButton restart;
    private ImageButton home;
    private ImageButton statistic;

    private HttpClient httpClient;
    private OkHttpClient client;

    private String scoreValue;
    private String stepValue;
    private String timeValue;
    private String scoreValueSecondPLayer;
    private String stepValueSecondPLayer;
    private String timeValueSecondPLayer;
    private String size;
    private String result;
    private String activityName;
    private String roomIndex;
    private String anotherPlayerUsername;
    private String anotherPlayerOrigin;

    private SqLiteTableManager sqLiteTableManager;

}
