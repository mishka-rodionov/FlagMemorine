package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mishka.flagmemorine.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        xSmall = (RadioButton) findViewById(R.id.xsmall);
        small = (RadioButton) findViewById(R.id.small);
        medium = (RadioButton) findViewById(R.id.medium);
        large = (RadioButton) findViewById(R.id.large);
        xLarge = (RadioButton) findViewById(R.id.xlarge);
        xxLarge = (RadioButton) findViewById(R.id.xxlarge);

        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);

        play = (Button) findViewById(R.id.play);
        multiplayer = (Button) findViewById(R.id.multiplayer);
        statistic = (Button) findViewById(R.id.statistic);
        stopAds = (Button) findViewById(R.id.stopAds);
        userInfo = (Button) findViewById(R.id.userInfo);

        View.OnClickListener onClickListenerRB = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.xsmall:
                        rg2.clearCheck();
                        size = "1";
                        break;
                    case R.id.small:
                        rg2.clearCheck();
                        size = "2";
                        break;
                    case R.id.medium:
                        rg2.clearCheck();
                        size = "3";
                        break;
                    case R.id.large:
                        rg1.clearCheck();
                        size = "4";
                        break;
                    case R.id.xlarge:
                        rg1.clearCheck();
                        size = "5";
                        break;
                    case R.id.xxlarge:
                        rg1.clearCheck();
                        size = "6";
                        break;
                }
            }
        };

        xSmall.setOnClickListener(onClickListenerRB);
        small.setOnClickListener(onClickListenerRB);
        medium.setOnClickListener(onClickListenerRB);
        large.setOnClickListener(onClickListenerRB);
        xLarge.setOnClickListener(onClickListenerRB);
        xxLarge.setOnClickListener(onClickListenerRB);

        View.OnClickListener onClickListenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.play:
                        Intent intent = new Intent(StartActivity.this, BattleFieldActivity.class);
                        intent.putExtra("size", size);
                        startActivity(intent);
                        break;
                }
            }
        };

        play.setOnClickListener(onClickListenerButton);

    }

    private RadioButton xSmall;
    private RadioButton small;
    private RadioButton medium;
    private RadioButton large;
    private RadioButton xLarge;
    private RadioButton xxLarge;

    private RadioGroup rg1;
    private RadioGroup rg2;

    private Button play;
    private Button multiplayer;
    private Button statistic;
    private Button stopAds;
    private Button userInfo;

    private String size = "6";
}
