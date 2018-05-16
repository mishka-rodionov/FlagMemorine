package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;

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

        scoreValue = getIntent().getStringExtra("score");
        stepValue = getIntent().getStringExtra("step");
        timeValue = getIntent().getStringExtra("time");
        size = getIntent().getStringExtra("size");
        result = getIntent().getStringExtra("result");

        score = (TextView) findViewById(R.id.scoreEndOfGame);
        step = (TextView) findViewById(R.id.stepEndOfGame);
        time = (TextView) findViewById(R.id.timeEndOfGame);
        message = (TextView) findViewById(R.id.message);

        if (Integer.parseInt(result) < 0){
            message.setText("Lose!");
            message.setTextColor(Color.RED);
        }
        if (Integer.parseInt(result) > 0){
            message.setText("Congratulations!");
            message.setTextColor(Color.GREEN);
        }
        if (Integer.parseInt(result) == 0){
            message.setText("Equals!");
            message.setTextColor(Color.GRAY);
        }

        score.setText(score.getText().toString() + " " + scoreValue);
        step.setText(step.getText().toString() + " " + stepValue);
        time.setText(time.getText().toString() + " " + timeValue);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.restartButton:
                        Intent intentBattleFieldActivity = new Intent(EndOfGameActivity.this, BattleFieldActivity.class);
                        intentBattleFieldActivity.putExtra("size", size);
                        startActivity(intentBattleFieldActivity);
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

    private TextView score;
    private TextView step;
    private TextView time;
    private TextView message;
    private android.support.v7.widget.Toolbar endOfGameToolbar;

    private ImageButton restart;
    private ImageButton home;

    private String scoreValue;
    private String stepValue;
    private String timeValue;
    private String size;
    private String result;

}
