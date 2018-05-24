package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.Facts;

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
        acBar.setDisplayHomeAsUpEnabled(true);

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



        if (activityName.equals("RoomBattlefield")){
            result = getIntent().getStringExtra("result");
            scoreValueSecondPLayer = getIntent().getStringExtra("scoreValueSecondPlayer");
            stepValueSecondPLayer = getIntent().getStringExtra("stepValueSecondPlayer");
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






        factsTextView.setText(Facts.randomCountryFacts());


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }
            }
        };

        restart = (ImageButton) findViewById(R.id.restartButton);
        home = (ImageButton) findViewById(R.id.homeButton);

        restart.setOnClickListener(onClickListener);
        home.setOnClickListener(onClickListener);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
        return super.onCreateOptionsMenu(menu);
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
    private android.support.v7.widget.Toolbar endOfGameToolbar;

    private LinearLayout gameInformation;

    private ImageButton restart;
    private ImageButton home;

    private String scoreValue;
    private String stepValue;
    private String timeValue;
    private String scoreValueSecondPLayer;
    private String stepValueSecondPLayer;
    private String timeValueSecondPLayer;
    private String size;
    private String result;
    private String activityName;

}
