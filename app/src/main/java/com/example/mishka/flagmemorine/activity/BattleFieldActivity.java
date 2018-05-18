package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.BattleField;
import com.example.mishka.flagmemorine.logic.CountryList;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.service.SqLiteTableManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import eu.davidea.flipview.FlipView;

public class BattleFieldActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_restart:
                recreate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        time1 = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battlefield);

        //Toolbar initialization
        hideSystemUI();
        battlefieldToolbar = (Toolbar) findViewById(R.id.battlefield_toolbar);
        setSupportActionBar(battlefieldToolbar);
        battlefieldActionBar = getSupportActionBar();
        battlefieldActionBar.setDisplayHomeAsUpEnabled(true);

        //******************************************************************************************
        sqLiteTableManager = new SqLiteTableManager(BattleFieldActivity.this);

        record = getPreferences(MODE_PRIVATE);                                                      //
        timer = new Timer();                                                                        // Инициализация таймера для задержки переворота табличек
        userChoice = new ArrayList<>(2);                                               // Инициализация контейнера для хранения страны с выбранной таблички табличек
        viewTag = new ArrayList<>(2);                                                  // Инициализация контейнера для хранения тэгов табличек пользовательского выбора табличек
        CountryList.loadCountryMap();
        basicLayout = (LinearLayout) findViewById(R.id.basicLayout);
        battleFieldSize = Integer.parseInt(getIntent().getStringExtra("size"));
        topRecord = 10000/*topRecord(battleFieldSize)*/;
        battleField = new BattleField(battleFieldSize);

        clickable = new HashMap<Integer, Boolean>();
        for (int i = 0; i < battleFieldSize; i++) {
            clickable.put(i, true);
        }

        result =    (TextView)  findViewById(R.id.result);
        result.setTextColor(Color.WHITE);
        test1 =     (TextView)  findViewById(R.id.currentStepCount);
        time =      (TextView)  findViewById(R.id.timeValue);
        scoreTV =   (TextView)  findViewById(R.id.currentScore);
        currentScoreSecondPlayer = (TextView) findViewById(R.id.currentScoreSecondPlayer);
        currentStepCountSecondPlayer = (TextView) findViewById(R.id.currentStepCountSecondPlayer);
        currentScoreSecondPlayer.setVisibility(View.INVISIBLE);
        currentStepCountSecondPlayer.setVisibility(View.INVISIBLE);
        scoreTV.setText(Integer.toString(score));

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i(Data.getLOG_TAG(), "handleMessage: " + System.currentTimeMillis());
                flipViews.get(msg.arg1).setEnabled(true);
                flipViews.get(msg.arg2).setEnabled(true);
                flipViews.get(msg.arg1).setClickable(false);
                flipViews.get(msg.arg2).setClickable(false);
                if (flipViews.get(msg.arg1).isFlipped()) {
                    flipViews.get(msg.arg1).flip(false);
                    flipViews.get(msg.arg2).flip(false);
//                    flipViews.get(msg.arg2).setClickable(true);
//                    Log.d(Data.getLOG_TAG(), "flip = false " + flipViews.get(msg.arg1).isClickable());
                } else {
//                    Log.d(Data.getLOG_TAG(), "flip = true " + flipViews.get(msg.arg1).isClickable());
                }
            }
        };

        handlerClickable = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i(Data.getLOG_TAG(), "handleClickableMessage: " + System.currentTimeMillis());
                flipViews.get(msg.arg1).setClickable(true);
                flipViews.get(msg.arg2).setClickable(true);
            }
        };

        handlerTime = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg2 < 10)
                    time.setText("" + msg.arg1 + " : 0" + msg.arg2);
                else
                    time.setText("" + msg.arg1 + " : " + msg.arg2);
            }
        };

        handlerIntent = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Intent endOfGameActivityIntent= new Intent(BattleFieldActivity.this, EndOfGameActivity.class);
                endOfGameActivityIntent.putExtra("score", Integer.toString(score));
                endOfGameActivityIntent.putExtra("step", Integer.toString(stepCounter));
                endOfGameActivityIntent.putExtra("time", time.getText().toString());
                endOfGameActivityIntent.putExtra("size", Integer.toString(battleFieldSize));
                endOfGameActivityIntent.putExtra("activityName", "Battlefield");
                startActivity(endOfGameActivityIntent);
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds == 60){
                    minutes++;
                    seconds = 0;
                }
                Message msg = handlerTime.obtainMessage(1, minutes, seconds);
                handlerTime.sendMessage(msg);
            }
        }, 0, 1000);

        stepCounter = 0;
        //******************************************************************************************
        onFlippingListener = new FlipView.OnFlippingListener() {
            @Override
            public void onFlipped(FlipView view, boolean checked) {
                Log.d(Data.getLOG_TAG(), "press button");
                final int index = Integer.parseInt(view.getTag().toString());               // Вычисление индекса кнопки в контейнере кнопок по тэгу кнопки
                flipViews.get(index).setEnabled(false);
                flipViews.get(index).setClickable(false);
//                new Thread(){
//                    public void run(){
//                        mp = MediaPlayer.create(BattleFieldActivity.this, R.raw.flip_click);
//                        mp.start();
//                    }
//                }.start();
                    String country  = battleField.getElement(Integer.parseInt(view.getTag().toString()));                                        // Получение ответа от AsynkTask
                    userChoice.add(country);                                                    // Добавление выбранного значения в контейнер пользовательского выбора.
                    if (userChoice.size() == 1 && flipFlag){
                        result.setTextColor(Color.WHITE);
                        result.setText(country);
                    }
                    viewTag.add(view.getTag().toString());                                      // Добавление тега выбранной кнопки в контейнер пользовательского выбора.
                    Log.d(Data.getLOG_TAG(), "userCoice size = " + userChoice.size());
                    Log.d(Data.getLOG_TAG(), "userCoice 1 = " + userChoice.get(0));

                clickHandler(country);
            }
        };

        getView(battleFieldSize);
        initFlipView(view, battleFieldSize);
        basicLayout.addView(view);
        time2 = System.currentTimeMillis();
        Log.d(Data.getLOG_TAG(), "loading time is = " + (time2 - time1));
        //******************************************************************************************
    }


    private void clickHandler(String country) {
        if (userChoice.size() == 2 && flipFlag) {                                                // Проверка количества элементов в контейнере пользовательского выбора.
            if (!userChoice.get(0).equals(userChoice.get(1))) {                       // Если значения пользовательского выбора не равны, то
                Log.i(Data.getLOG_TAG(), "clickHandler: forward flip, flag NO equals");
                mistake(country, flipFlag);
                userChoice.clear();                                                 // очищаем контейнер
                viewTag.clear();                                                    // очищаем контейнер тегов
            } else
                // region else
                if (userChoice.get(0).equals(userChoice.get(1))) {                        // Если значения пользовательского выбора равны, то
                    Log.i(Data.getLOG_TAG(), "clickHandler: forward flip, flag equals");
                    goal(country);
                    userChoice.clear();                                                 // очищаем контейнеры пользовательского выбора
                    viewTag.clear();
                    isFinish();
                }
//                endregion
        } else
            if (userChoice.size() == 2 && !flipFlag) {
                if (!userChoice.get(0).equals(userChoice.get(1))) {                     // Если значения пользовательского выбора не равны, то
                    Log.i(Data.getLOG_TAG(), "clickHandler: backward flip, flag NO equals");
                    mistake(country, flipFlag);
                    userChoice.clear();                                                 // очищаем контейнер
                    viewTag.clear();                                                    // очищаем контейнер тегов
                }
        }
    }

    private void goal(String country) {
        stepCounter++;
        test1.setText("" + stepCounter);
        result.setTextColor(Color.GREEN);
        result.setText(country);
        scoreCount(true);
        scoreTV.setText(Integer.toString(score));
        flipFlag = true;

        flipViews.get(Integer.parseInt(viewTag.get(0))).setEnabled(false);
        flipViews.get(Integer.parseInt(viewTag.get(1))).setEnabled(false);

        clickable.put(Integer.parseInt(viewTag.get(0)), false);
        clickable.put(Integer.parseInt(viewTag.get(1)), false);
    }

    private void mistake(String country, boolean flag) {
        if (flag){                                                              //Прямой переворот
            stepCounter++;
            result.setTextColor(Color.RED);
            result.setText(country);
            scoreCount(false);
            scoreTV.setText(Integer.toString(score));
            final int but0 = Integer.parseInt(viewTag.get(0));                  // вычисляем тег первой нажатой кнопки
            final int but1 = Integer.parseInt(viewTag.get(1));                  // вычисляем тег второй нажатой кнопки
            delayedTask(but0, but1);
            delayedClickable(but0, but1);
            flipFlag = false;
            Log.d(Data.getLOG_TAG(), "flipFlag = " + flipFlag);
            test1.setText("" + stepCounter);
            for (int i = 0; i < clickable.size(); i++) {
                if (clickable.get(i)) {
                    flipViews.get(i).setEnabled(false);
                }
            }
        }else{
            flipFlag = true;
            Log.d(Data.getLOG_TAG(), "flipFlag = " + flipFlag);
            for (int i = 0; i < clickable.size(); i++) {
                if (clickable.get(i)) {
                    flipViews.get(i).setEnabled(true);
                }
            }
        }
    }

    private void isFinish() {
        if (!clickable.containsValue(true)) {                               // данное условие выполняется когда все таблички перевернуты
            clickable.clear();

            userRecord = Integer.parseInt(test1.getText().toString());
            Log.d(Data.getLOG_TAG(), "All flags is plipped");
            timer.cancel();
            sqLiteTableManager.insertIntoStatisticTable(null,null,null, Integer.toString(battleFieldSize), time.getText().toString(), stepCounter, score, Data.getCurrentDate());
            Log.d(Data.getLOG_TAG(), "user record = " + userRecord);
            delayedIntent();

            if (userRecord < topRecord) {
                sqLiteTableManager.insertIntoRecordTable(null,null,null, Integer.toString(battleFieldSize), time.getText().toString(), stepCounter, score, Data.getCurrentDate());
                SharedPreferences.Editor editor = record.edit();
                editor.putString("REC", test1.getText().toString());
                editor.commit();
            }
        }
    }

    public void initFlipView(View view, int battleFieldSize){
        flipViews = new ArrayList<>(battleFieldSize);
        if (battleFieldSize == Data.getXsmallSize()){
            for (int i = 0; i < Data.getXsmallSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxsmall(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
                flipViews.get(i).isFlipped();
            }
        }else if (battleFieldSize == Data.getSmallSize()){
            for (int i = 0; i < Data.getSmallSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdsmall(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getMediumSize()){
            for (int i = 0; i < Data.getMediumSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdmedium(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getLargeSize()){
            for (int i = 0; i < Data.getLargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdlarge(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getXlargeSize()){
            for (int i = 0; i < Data.getXlargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxlarge(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getXxlargeSize()){
            for (int i = 0; i < Data.getXxlargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxxlarge(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }
        for (int i = 0; i < flipViews.size(); i++) {
            flipViews.get(i).setFrontImage(R.drawable.unknown);
            flipViews.get(i).setEnabled(true);
        }
        for (int i = 0; i < flipViews.size(); i++) {
            flipViews.get(i).setOnFlippingListener(onFlippingListener);
        }
        Log.d(Data.getLOG_TAG(), "flipview size = " + flipViews.size());
    }

    public void getView(int size){
        if(size == Data.getXsmallSize()){
            view = getLayoutInflater().inflate(R.layout.layout_xsmall, null);
            battlefieldActionBar.setTitle("xSmall field");
        }else if (size == Data.getSmallSize()){
            view = getLayoutInflater().inflate(R.layout.layout_small, null);
            battlefieldActionBar.setTitle("Small field");
        }else if (size == Data.getMediumSize()){
            view = getLayoutInflater().inflate(R.layout.layout_medium, null);
            battlefieldActionBar.setTitle("Medium field");
        }else if (size == Data.getLargeSize()){
            view = getLayoutInflater().inflate(R.layout.layout_large, null);
            battlefieldActionBar.setTitle("Large field");
        }else if (size == Data.getXlargeSize()){
            view = getLayoutInflater().inflate(R.layout.layout_xlarge, null);
            battlefieldActionBar.setTitle("xLarge field");
        }else if (size == Data.getXxlargeSize()){
//            view = getLayoutInflater().inflate(R.layout.layout_xxlarge, null);
            battlefieldActionBar.setTitle("xxLarge field");
            view = getLayoutInflater().inflate(R.layout.layout_xxlarge, null);
        }
    }

    public void scoreCount(Boolean state){
        if(state){
            score += 100;
        }else if (!state){
            if (score > 0){
                score -= 10;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startActivityIntent = new Intent(BattleFieldActivity.this, StartActivity.class);
        startActivity(startActivityIntent);
    }

    public void delayedTask(final int but0, final int but1/*, final int resource*/){
        Thread thread = new Thread(new Runnable() {                             // создаем новый поток для закрытия первого, из выбранных пользователем флагов, рубашкой
            Message msg;
            @Override
            public void run() {
                msg = handler.obtainMessage(1, but0, but1);                // подготавливаем сообщение
                handler.sendMessageDelayed(msg, 1000);                      // помещаем в очередь хэндлера отложенное на секунду сообщение
            }
        });
        thread.start();
    }

    public void delayedIntent(){
        Thread thread = new Thread(new Runnable() {
            Message msg;
            @Override
            public void run() {

                msg = handlerIntent.obtainMessage();
                handlerIntent.sendMessageDelayed(msg, 1000);
            }
        });
        thread.start();
    }

    public void delayedClickable(final int but0, final int but1){
        Thread thread = new Thread(new Runnable() {                             // создаем новый поток для закрытия первого, из выбранных пользователем флагов, рубашкой
            Message msg;
            @Override
            public void run() {
                msg = handlerClickable.obtainMessage(1, but0, but1);                // подготавливаем сообщение
                handlerClickable.sendMessageDelayed(msg, 1600);                      // помещаем в очередь хэндлера отложенное на секунду сообщение
            }
        });
        thread.start();
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

    private ArrayList<FlipView> flipViews;
    private ArrayList<String> userChoice;
    private ArrayList<String> viewTag;

    private BattleField battleField;
    private FlipView.OnFlippingListener onFlippingListener;

    private Handler handler;
    private Handler handlerClickable;
    private Handler handlerTime;
    private Handler handlerIntent;
    private HashMap<Integer, Boolean> clickable;

    private LinearLayout basicLayout;

    private MediaPlayer mp;

    private SharedPreferences record;

    private SqLiteTableManager sqLiteTableManager;

    private Button restart;
    private Button back;
    private TextView result;
    private TextView test1;
    private TextView test2;
    private TextView time;
    private TextView scoreTV;
    private TextView currentScoreSecondPlayer;
    private TextView currentStepCountSecondPlayer;
    private Timer timer;
    private View view;
    private Toolbar battlefieldToolbar;
    private ActionBar battlefieldActionBar;

    private String BF;
    private int battleFieldSize = 6;
    private int battleFieldIndex = 0;
    private int minutes = 0;
    private int stepCounter = 0;
    private int userRecord = 0;
    private int topRecord = 0;
    private int seconds = 0;
    private int score =  100;
    private long time1 = 0;
    private long time2 = 0;
    private boolean flipFlag = true;

    private String username;
    private String login;
    private String country;

    //endregion

}
