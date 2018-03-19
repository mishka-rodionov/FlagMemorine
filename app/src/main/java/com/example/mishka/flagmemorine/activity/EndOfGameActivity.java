package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;

public class EndOfGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        scoreValue = getIntent().getStringExtra("score");
        stepValue = getIntent().getStringExtra("step");
        timeValue = getIntent().getStringExtra("time");
        size = getIntent().getStringExtra("size");

        score = (TextView) findViewById(R.id.scoreEndOfGame);
        step = (TextView) findViewById(R.id.stepEndOfGame);
        time = (TextView) findViewById(R.id.timeEndOfGame);

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

        restart = (Button) findViewById(R.id.restartButton);
        home = (Button) findViewById(R.id.homeButton);

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

    private TextView score;
    private TextView step;
    private TextView time;

    private Button restart;
    private Button home;

    private String scoreValue;
    private String stepValue;
    private String timeValue;
    private String size;

}
