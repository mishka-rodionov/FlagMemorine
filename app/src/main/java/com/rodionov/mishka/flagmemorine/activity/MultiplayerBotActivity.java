package com.rodionov.mishka.flagmemorine.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.logic.BattleField;
import com.rodionov.mishka.flagmemorine.logic.CountryList;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.HttpClient;
import com.rodionov.mishka.flagmemorine.service.FlipListener;
import com.rodionov.mishka.flagmemorine.service.MultiplayerBot;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import eu.davidea.flipview.FlipView;
import okhttp3.OkHttpClient;

public class MultiplayerBotActivity extends AppCompatActivity implements FlipListener{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        menu.findItem(R.id.action_restart).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_restart:
//                recreate();
//                break;
            case R.id.home:
//                removeRoom(Integer.toString(roomIndex));
                Intent startActivityIntent = new Intent(MultiplayerBotActivity.this, StartActivity.class);
                startActivity(startActivityIntent);
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                    Log.i(Data.getLOG_TAG(), "onClick: The interstitial loaded yet!!!!!!!!!!!!!!!");
                }else {
                    Log.i(Data.getLOG_TAG(), "onClick: The interstitial wasn't loaded yet.");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem menuItem;
        getMenuInflater().inflate(R.menu.main, menu);
        if (action){
            menu.findItem(R.id.action_restart).setIcon(R.drawable.ic_play_arrow_white_48dp);
//            menuItem = menu.getItem(1);
//            menuItem.setIcon(R.drawable.ic_play_arrow_white_48dp);
        }else{
//            menuItem = menu.getItem(1);
//            menuItem.setIcon(R.drawable.ic_pause_white_48dp);
            menu.findItem(R.id.action_restart).setIcon(R.drawable.ic_pause_white_48dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        time1 = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battlefield);

        //region mobile ads
        MobileAds.initialize(this, /*"ca-app-pub-3940256099942544~3347511713");//*/"ca-app-pub-1313654392091353~6971891289");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(/*"ca-app-pub-3940256099942544/1033173712");//*/"ca-app-pub-1313654392091353/4903230758");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //endregion

        //Toolbar initialization
        hideSystemUI();
        battlefieldToolbar = (Toolbar) findViewById(R.id.battlefield_toolbar);
        setSupportActionBar(battlefieldToolbar);
        battlefieldActionBar = getSupportActionBar();
        battlefieldActionBar.setDisplayHomeAsUpEnabled(true);
        battlefieldActionBar.setTitle("");
        client = new OkHttpClient();
        httpClient = new HttpClient();

        //******************************************************************************************
        sqLiteTableManager = new SqLiteTableManager(MultiplayerBotActivity.this);
//        pullDB();

        gone = false;
        goneCount = 3;
        goneToast = Toast.makeText(MultiplayerBotActivity.this, "", Toast.LENGTH_SHORT);
        requestTimer = new Timer();
        record = getPreferences(MODE_PRIVATE);                                                      //
        timer = new Timer();                                                                        // Инициализация таймера для задержки переворота табличек
        userChoice = new ArrayList<>(2);                                               // Инициализация контейнера для хранения страны с выбранной таблички табличек
        viewTag = new ArrayList<>(2);                                                  // Инициализация контейнера для хранения тэгов табличек пользовательского выбора табличек
        CountryList.loadCountryMap();
        basicLayout = (LinearLayout) findViewById(R.id.basicLayout);
        //region get intent parameters
        battleFieldSize = Integer.parseInt(getIntent().getStringExtra(Data.getSize()));
        roomIndex = 1;
        playerNumber = "firstPlayer";
        anotherPlayerUsername = "bot";
        anotherPlayerOrigin = "Botswana";
        int level = getIntent().getIntExtra("level", Data.getHard());

//        if (playerNumber.equals("firstPlayer")){
//            secondPlayerName = getIntent().getStringExtra("anotherPlayer");
//        }
//        if (playerNumber.equals("secondPlayer")){
//            firstPlayerName = getIntent().getStringExtra("anotherPlayer");
//        }
//        String[] body = getIntent().getStringExtra("battlefieldBody").split(" ");
//        //endregion
//        topRecord = 10000/*topRecord(battleFieldSize)*/;
//        battlefieldBody = new ArrayList<String>();
//        for (int i = 0; i < body.length; i++) {
//            battlefieldBody.add(body[i].replaceAll("_", " "));
////            if(battlefieldBody.get(i).contains("_")){
////                battlefieldBody.add(i, battlefieldBody.get(i).replaceAll("_", " "));
////            }
//        }
//        battleField = new BattleField(battleFieldSize);

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
        localPlayerName = (TextView) findViewById(R.id.localPlayerName);
        enemyPlayerName = (TextView) findViewById(R.id.enemyPlayerName);
//        actionImage = (ImageView) findViewById(R.id.actionImage);
//        roomsMenu = getMenuInflater().inflate();
//        getMenuInflater().inflate(R.menu.main, roomsMenu);

        localPlayerName.setText(getIntent().getStringExtra("localPlayerName"));
        enemyPlayerName.setText(getIntent().getStringExtra("anotherPlayer"));
        scoreTV.setText(Integer.toString(score));
        currentScoreSecondPlayer.setText(Integer.toString(score));

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                flipViews.get(msg.arg1).setEnabled(true);
                flipViews.get(msg.arg2).setEnabled(true);
                flipViews.get(msg.arg1).setClickable(false);
                flipViews.get(msg.arg2).setClickable(false);
                if (flipViews.get(msg.arg1).isFlipped()) {
                    flipViews.get(msg.arg1).flip(false);
                    flipViews.get(msg.arg2).flip(false);
                } else {

                }
            }
        };

        botHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i(Data.getLOG_TAG(), "BOTS HANDLER" + flipViews.get(msg.arg1).isEnabled());
                flipViews.get(msg.arg1).setEnabled(true);
                flipViews.get(msg.arg2).setEnabled(true);
                flipViews.get(msg.arg1).setClickable(false);
                flipViews.get(msg.arg2).setClickable(false);
                Log.i(Data.getLOG_TAG(), "BOTS HANDLER NEW" + flipViews.get(msg.arg1).isEnabled());
                flipViews.get(msg.arg1).flip(true);
                flipViews.get(msg.arg2).flip(true);
            }
        };

        handlerClickable = new Handler() {
            @Override
            public void handleMessage(Message msg) {
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
                Integer res = score - scoreSecondPlayer;
                Intent endOfGameActivityIntent= new Intent(MultiplayerBotActivity.this, EndOfGameActivity.class);
                endOfGameActivityIntent.putExtra("score", Integer.toString(score));
                endOfGameActivityIntent.putExtra("scoreValueSecondPlayer", Integer.toString(scoreSecondPlayer));
                endOfGameActivityIntent.putExtra("step", Integer.toString(stepCounter));
                endOfGameActivityIntent.putExtra("stepValueSecondPlayer", Integer.toString(stepCounterSecondPlayer));
                endOfGameActivityIntent.putExtra("time", time.getText().toString());
                endOfGameActivityIntent.putExtra("size", Integer.toString(battleFieldSize));
                endOfGameActivityIntent.putExtra("result", Integer.toString(res));
                endOfGameActivityIntent.putExtra("activityName", "RoomBattlefield");
                endOfGameActivityIntent.putExtra("localPlayername", localPlayerName.getText().toString());
                endOfGameActivityIntent.putExtra("enemyPlayername", enemyPlayerName.getText().toString());
                endOfGameActivityIntent.putExtra("anotherPlayerUsername", anotherPlayerUsername);
                endOfGameActivityIntent.putExtra("anotherPlayerOrigin", anotherPlayerOrigin);
                startActivity(endOfGameActivityIntent);
                if (res < 0){
                    if (mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                        Log.i(Data.getLOG_TAG(), "onClick: The interstitial loaded yet!!!!!!!!!!!!!!!");
                    }else {
                        Log.i(Data.getLOG_TAG(), "onClick: The interstitial wasn't loaded yet.");
                    }
                }
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final Handler mainHandler = new Handler(Looper.getMainLooper());
                seconds++;
                if (seconds == 60){
                    minutes++;
                    seconds = 0;
                }
                if(gone && goneCount >= 1){
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            goneToast.setText("The opponent is gone! Go to main menu in " + goneCount--);
                            goneToast.show();
                            if (goneCount == 0){
                                goneToast.cancel();
                                startActivity(new Intent(MultiplayerBotActivity.this, StartActivity.class));
                            }
                        }
                    });
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
                final int index = Integer.parseInt(view.getTag().toString());                       // Вычисление индекса кнопки в контейнере кнопок по тэгу кнопки
                Log.d(Data.getLOG_TAG(), "pressed button " + index);
                flipViews.get(index).setEnabled(false);
                flipViews.get(index).setClickable(false);
//                new Thread(){
//                    public void run(){
//                        mp = MediaPlayer.create(BattleFieldActivity.this, R.raw.flip_click);
//                        mp.start();
//                    }
//                }.start();
                String country  = battleField.getElement(Integer.parseInt(view.getTag().toString()));
                userChoice.add(country);                                                            // Добавление выбранного значения в контейнер пользовательского выбора.
                if (userChoice.size() == 1 && flipFlag){
                    result.setTextColor(Color.WHITE);
                    result.setText(country);
                }
                viewTag.add(view.getTag().toString());                                              // Добавление тега выбранной кнопки в контейнер пользовательского выбора.
                Log.d(Data.getLOG_TAG(), "userCoice size = " + userChoice.size());
                Log.d(Data.getLOG_TAG(), "userCoice 1 = " + userChoice.get(0));

                if (sending && flipFlag){
//                    sending(httpClient, Integer.toString(roomIndex), Integer.toString(index));
                }

                clickHandler(country);
            }
        };

        getView(battleFieldSize);
        if (playerNumber.equals("firstPlayer")){
            sending = true;
            receiving = false;
            action = true;
            invalidateOptionsMenu();
//            actionImage.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        }else{
            sending = false;
            receiving = true;
            action = false;
            invalidateOptionsMenu();
//            actionImage.setImageResource(R.drawable.ic_pause_white_48dp);
            requestTimer.schedule(new TimerTask() {
                @Override
                public void run() {
//                    receiving(httpClient, Integer.toString(roomIndex));
                }
            }, delay, period);
        }
        battleField = new BattleField(battleFieldSize);
        //region bot
        multiplayerBot = new MultiplayerBot(battleFieldSize, level, battleField);
        //endregion
        initFlipView(view, battleFieldSize);
        if(receiving){
            for (int i = 0; i < clickable.size(); i++) {
                if (clickable.get(i)) {
                    flipViews.get(i).setEnabled(false);
                }
            }
        }
        basicLayout.addView(view);
        time2 = System.currentTimeMillis();
        Log.d(Data.getLOG_TAG(), "loading time is = " + (time2 - time1));
        //******************************************************************************************
    }

    private void clickHandler(String country) {
        if (userChoice.size() == 2 && flipFlag) {                                                   // Если выбрано 2 флага и происходит прямой переворот
            if (!userChoice.get(0).equals(userChoice.get(1))) {                                     // Если значения пользовательского выбора не равны, то
                Log.i(Data.getLOG_TAG(), "clickHandler: forward flip, flag NO equals");
                mistake(country, flipFlag);
                userChoice.clear();                                                                 // Очищаем контейнер
                viewTag.clear();                                                                    // Очищаем контейнер тегов
            } else
                // region else
                if (userChoice.get(0).equals(userChoice.get(1))) {                                  // Если значения пользовательского выбора равны, то
                    Log.i(Data.getLOG_TAG(), "clickHandler: forward flip, flag equals");
                    goal(country);
                    userChoice.clear();                                                             // Очищаем контейнеры пользовательского выбора
                    viewTag.clear();
                    isFinish();
                }
//                endregion
        } else
        if (userChoice.size() == 2 && !flipFlag) {                                                  // Если выбрано 2 флага и происходит обратный переворот
            if (!userChoice.get(0).equals(userChoice.get(1))) {                                     // Если значения пользовательского выбора не равны, то
                Log.i(Data.getLOG_TAG(), "clickHandler: backward flip, flag NO equals");
                mistake(country, flipFlag);
                userChoice.clear();                                                                 // очищаем контейнер
                viewTag.clear();                                                                    // очищаем контейнер тегов
            }
        }
    }

    private void goal(String country) {
        result.setTextColor(Color.GREEN);
        result.setText(country);
        scoreDisplay(true);
        Log.i(Data.getLOG_TAG(), "BOTS TAG = " + viewTag.get(0));
        Log.i(Data.getLOG_TAG(), "BOTS TAG = " + viewTag.get(1));
        multiplayerBot.deactivatePoint(Integer.parseInt(viewTag.get(0)));
        multiplayerBot.deactivatePoint(Integer.parseInt(viewTag.get(1)));
        flipFlag = true;
        flipViews.get(Integer.parseInt(viewTag.get(0))).setEnabled(false);
        flipViews.get(Integer.parseInt(viewTag.get(1))).setEnabled(false);
        if (receiving){
            ArrayList<Integer> botChoice = multiplayerBot.botFlip();
            int one = botChoice.get(0);
            int two = botChoice.get(1);
            Log.i(Data.getLOG_TAG(), "BOTS RETURN IN RECEIVING = " + one + " " + two);
            delayedBotTask(one, two);
        }

        clickable.put(Integer.parseInt(viewTag.get(0)), false);
        clickable.put(Integer.parseInt(viewTag.get(1)), false);
    }

    private void mistake(String country, boolean flag) {
        if (flag){                                                              //Прямой переворот
            result.setTextColor(Color.RED);
            result.setText(country);
            scoreDisplay(false);
            final int but0 = Integer.parseInt(viewTag.get(0));                  // вычисляем тег первой нажатой кнопки
            final int but1 = Integer.parseInt(viewTag.get(1));                  // вычисляем тег второй нажатой кнопки
            multiplayerBot.flipEvent(but0, userChoice.get(0));
            multiplayerBot.flipEvent(but1, userChoice.get(1));
            delayedTask(but0, but1);
            delayedClickable(but0, but1);
            flipFlag = false;
            Log.d(Data.getLOG_TAG(), "flipFlag = " + flipFlag);
            if (sending){
                sending = false;
                receiving = true;
                action = false;
                invalidateOptionsMenu();
//                actionImage.setImageResource(R.drawable.ic_pause_white_48dp);
                for (int i = 0; i < clickable.size(); i++) {
                    if (clickable.get(i)) {
                        flipViews.get(i).setEnabled(false);
                    }
                }
                ArrayList<Integer> botChoice = multiplayerBot.botFlip();
                int one = botChoice.get(0);
                int two = botChoice.get(1);
                Log.i(Data.getLOG_TAG(), "BOTS RETURN IN MISTAKE = " + one + " " + two);
                delayedBotTask(one, two);
//                requestTimer = new Timer();
//                requestTimer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
////                        receiving(httpClient, Integer.toString(roomIndex));
//                    }
//                }, delay, period);
            }else if (receiving){
                sending = true;
                receiving = false;
                action = true;
                invalidateOptionsMenu();
//                actionImage.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                for (int i = 0; i < clickable.size(); i++) {
                    if (clickable.get(i)) {
                        flipViews.get(i).setEnabled(true);
                    }
                }
                requestTimer.cancel();
            }
        }else{
            flipFlag = true;
            Log.d(Data.getLOG_TAG(), "flipFlag = " + flipFlag);
            if (sending){
                for (int i = 0; i < clickable.size(); i++) {
                    if (clickable.get(i)) {
                        flipViews.get(i).setEnabled(true);
                    }
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

    private void scoreDisplay(Boolean state) {
        if(sending){
            stepCounter++;
            test1.setText("" + stepCounter);
            scoreCount(state);
            scoreTV.setText(Integer.toString(score));
        }
        if (receiving){
            stepCounterSecondPlayer++;
            scoreCount(state);
            currentScoreSecondPlayer.setText(Integer.toString(scoreSecondPlayer));
            currentStepCountSecondPlayer.setText("" + stepCounterSecondPlayer);
        }
    }

    public void initFlipView(View view, int battleFieldSize){
        flipViews = new ArrayList<>(battleFieldSize);
        Log.i(Data.getLOG_TAG(), "initFlipView: flipviews SIZE = " + flipViews.size());
        if (battleFieldSize == Data.getXsmallSize()){
            for (int i = 0; i < Data.getXsmallSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxsmall(i)));
                Log.i(Data.getLOG_TAG(), "initFlipView: COUNTRY = " + battleField.getElement(i));
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
                try{
                    flipViews.add((FlipView) view.findViewById(Data.getIdxxlarge(i)));
                    flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
                }catch (NullPointerException np){
                    Log.i(Data.getLOG_TAG(), "initFlipView: BAD COUNTRY = " + battleField.getElement(i));
                    np.toString();
                }

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
//            battlefieldActionBar.setTitle("xSmall field");
        }else if (size == Data.getSmallSize()){
            view = getLayoutInflater().inflate(R.layout.layout_small, null);
//            battlefieldActionBar.setTitle("Small field");
        }else if (size == Data.getMediumSize()){
            view = getLayoutInflater().inflate(R.layout.layout_medium, null);
//            battlefieldActionBar.setTitle("Medium field");
        }else if (size == Data.getLargeSize()){
            view = getLayoutInflater().inflate(R.layout.layout_large, null);
//            battlefieldActionBar.setTitle("Large field");
        }else if (size == Data.getXlargeSize()){
            view = getLayoutInflater().inflate(R.layout.layout_xlarge, null);
//            battlefieldActionBar.setTitle("xLarge field");
        }else if (size == Data.getXxlargeSize()){
//            view = getLayoutInflater().inflate(R.layout.layout_xxlarge, null);
//            battlefieldActionBar.setTitle("xxLarge field");
            view = getLayoutInflater().inflate(R.layout.layout_xxlarge, null);
        }
    }

    public void scoreCount(Boolean state){
        if (sending) {
            if (state) {
                score += 100;
            } else if (!state) {
                if (score > 0) {
                    score -= 10;
                }
            }
        }
        if (receiving){
            if (state) {
                scoreSecondPlayer += 100;
            } else if (!state) {
                if (scoreSecondPlayer > 0) {
                    scoreSecondPlayer -= 10;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(Data.getLOG_TAG(), "onBackPressed: back is pressed");
//        removeRoom(Integer.toString(roomIndex));
        Intent startActivityIntent = new Intent(MultiplayerBotActivity.this, StartActivity.class);
        startActivity(startActivityIntent);
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
            Log.i(Data.getLOG_TAG(), "onClick: The interstitial loaded yet!!!!!!!!!!!!!!!");
        }else {
            Log.i(Data.getLOG_TAG(), "onClick: The interstitial wasn't loaded yet.");
        }
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

    public void delayedBotTask(final int but0, final int but1/*, final int resource*/){
        Thread thread = new Thread(new Runnable() {                             // создаем новый поток для закрытия первого, из выбранных пользователем флагов, рубашкой
            Message msg;
            @Override
            public void run() {
                msg = botHandler.obtainMessage(1, but0, but1);                // подготавливаем сообщение
                botHandler.sendMessageDelayed(msg, 3000);                      // помещаем в очередь хэндлера отложенное на секунду сообщение
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

    @Override
    public void flipEvent(int tag, String value) {

    }

    @Override
    public void deactivatePoint(int tag) {

    }

    @Override
    public ArrayList botFlip() {
        return new ArrayList();
    }

    //region Private fields
    private ArrayList<FlipView> flipViews;
    private ArrayList<String> userChoice;
    private ArrayList<String> viewTag;

    private BattleField battleField;
    private FlipView.OnFlippingListener onFlippingListener;

    private Handler handler;
    private Handler handlerClickable;
    private Handler handlerTime;
    private Handler handlerIntent;
    private Handler botHandler;
    private HashMap<Integer, Boolean> clickable;

    private HttpClient httpClient;
    private OkHttpClient client;

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
    private TextView localPlayerName;
    private TextView enemyPlayerName;

    private ImageView actionImage;

    private Menu roomsMenu;

    private Timer timer;
    private Timer requestTimer;
    private TimerTask requestTask;

    private View view;
    private Toolbar battlefieldToolbar;
    private ActionBar battlefieldActionBar;
    private Toast goneToast;

    private FlipListener multiplayerBot;

    private String BF;
    private int battleFieldSize = 6;
    private int battleFieldIndex = 0;
    private int minutes = 0;
    private int stepCounter = 0;
    private int stepCounterSecondPlayer = 0;
    private int userRecord = 0;
    private int topRecord = 0;
    private int seconds = 0;
    private int roomIndex;
    private int dummy = -1;
    private int score =  100;
    private int scoreSecondPlayer =  100;
    private long time1 = 0;
    private long time2 = 0;
    private long delay = 1000;
    private long period = 200;
    private long goneCount;
    private boolean flipFlag = true;
    private boolean gone;
    private boolean action;
    private Boolean sending;
    private Boolean receiving;

    private String playerName;
    private String playerNumber;
    private String username;
    private String origin;
    private String firstPlayerName;
    private String secondPlayerName;
    private String anotherPlayerUsername;
    private String anotherPlayerOrigin;

    private ArrayList<String> battlefieldBody;

    private InterstitialAd mInterstitialAd;

    //endregion

}
