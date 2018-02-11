package com.example.mishka.flagmemorine.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;

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

        RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getId()){
                    case R.id.rg1:
//                        rg2.clearCheck();
                        Log.d(Data.getLOG_TAG(), "rg1");
                        break;
                    case R.id.rg2:
//                        rg1.clearCheck();
                        Log.d(Data.getLOG_TAG(), "rg2");
                        break;
                }
            }
        };

        rg1.setOnCheckedChangeListener(onCheckedChangeListener);
        rg2.setOnCheckedChangeListener(onCheckedChangeListener);

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
}
