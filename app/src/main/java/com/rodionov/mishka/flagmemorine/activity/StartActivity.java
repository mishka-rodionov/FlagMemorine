package com.rodionov.mishka.flagmemorine.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
//import android.support.design.BuildConfig;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.rodionov.mishka.flagmemorine.BuildConfig;
import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.HttpClient;
import com.rodionov.mishka.flagmemorine.service.DBHelper;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StartActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        if(countFinish > 0){
            countFinish = 0;
            finish();
        }else{
            countFinish++;
            Toast.makeText(this, " Click once again to quit.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //region mobile ads
        MobileAds.initialize(this, /*"ca-app-pub-3940256099942544~3347511713");//*/"ca-app-pub-1313654392091353~6971891289");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(/*"ca-app-pub-3940256099942544/1033173712");//*/"ca-app-pub-1313654392091353/4903230758");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //endregion

        hideSystemUI();

        countFinish = 0;

        contentValues = new ContentValues();

//        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteTableManager = new SqLiteTableManager(StartActivity.this);
        client = new OkHttpClient();
        httpClient = new HttpClient();

        activityStartLayout = (LinearLayout) findViewById(R.id.activity_start_layout);
        botLinearLayout = (LinearLayout) findViewById(R.id.botLinearLayout);
        botLevelList = getLayoutInflater().inflate(R.layout.bot_level_list, null);
        easySwitch = (Switch) botLevelList.findViewById(R.id.easySwitch);
        normalSwitch = (Switch) botLevelList.findViewById(R.id.normalSwitch);
        hardSwitch = (Switch) botLevelList.findViewById(R.id.hardSwitch);
        veryhardSwitch = (Switch) botLevelList.findViewById(R.id.veryhardSwitch);

        snackbarFlag = true;
        multiplayerFlag = false;

        requestTimer = new Timer();

        checkFirstRun();
        username = sqLiteTableManager.getLogin();

        xSmall = (RadioButton) findViewById(R.id.xsmall);
        small = (RadioButton) findViewById(R.id.small);
        medium = (RadioButton) findViewById(R.id.medium);
        large = (RadioButton) findViewById(R.id.large);
        xLarge = (RadioButton) findViewById(R.id.xlarge);
        xxLarge = (RadioButton) findViewById(R.id.xxlarge);

        xsmallAvailableUsers = (TextView) findViewById(R.id.xsmallAvailableUsers);
        smallAvailableUsers = (TextView) findViewById(R.id.smallAvailableUsers);
        mediumAvailableUsers = (TextView) findViewById(R.id.mediumAvailableUsers);
        largeAvailableUsers = (TextView) findViewById(R.id.largeAvailableUsers);
        xlargeAvailableUsers = (TextView) findViewById(R.id.xlargeAvailableUsers);
        xxlargeAvailableUsers = (TextView) findViewById(R.id.xxlargeAvailableUsers);
        onlineValue = (TextView) findViewById(R.id.onlineValue);
//        testImage = (ImageView) findViewById(R.id.testImage);
//        testImage2 = (ImageView) findViewById(R.id.testImage2);

//        rg1 = (RadioGroup) findViewById(R.id.rg1);
//        rg2 = (RadioGroup) findViewById(R.id.rg2);

        play = (Button) findViewById(R.id.play);
        multiplayer = (Button) findViewById(R.id.multiplayer);
        statistic = (Button) findViewById(R.id.statistic);
        stopAds = (Button) findViewById(R.id.stopAds);
        userInfo = (Button) findViewById(R.id.userInfo);
        totalTop = (Button) findViewById(R.id.totalTop);
        botplay = (Button) findViewById(R.id.botplay);
        learnInfo = (Button) findViewById(R.id.leanrInfo);

        // region RadioButton Listener
        View.OnClickListener onClickListenerRB = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.xsmall:
//                        rg2.clearCheck();
                        small.setChecked(false);
                        medium.setChecked(false);
                        large.setChecked(false);
                        xLarge.setChecked(false);
                        xxLarge.setChecked(false);
                        size = Integer.toString(Data.getXsmallSize());
                        break;
                    case R.id.small:
//                        rg2.clearCheck();
                        xSmall.setChecked(false);
                        medium.setChecked(false);
                        large.setChecked(false);
                        xLarge.setChecked(false);
                        xxLarge.setChecked(false);
                        size = Integer.toString(Data.getSmallSize());
                        break;
                    case R.id.medium:
//                        rg2.clearCheck();
                        xSmall.setChecked(false);
                        small.setChecked(false);
                        large.setChecked(false);
                        xLarge.setChecked(false);
                        xxLarge.setChecked(false);
                        size = Integer.toString(Data.getMediumSize());
                        break;
                    case R.id.large:
//                        rg1.clearCheck();
                        xSmall.setChecked(false);
                        small.setChecked(false);
                        medium.setChecked(false);
                        xLarge.setChecked(false);
                        xxLarge.setChecked(false);
                        size = Integer.toString(Data.getLargeSize());
                        break;
                    case R.id.xlarge:
//                        rg1.clearCheck();
                        xSmall.setChecked(false);
                        small.setChecked(false);
                        medium.setChecked(false);
                        large.setChecked(false);
                        xxLarge.setChecked(false);
                        size = Integer.toString(Data.getXlargeSize());;
                        break;
                    case R.id.xxlarge:
//                        rg1.clearCheck();
                        xSmall.setChecked(false);
                        small.setChecked(false);
                        medium.setChecked(false);
                        large.setChecked(false);
                        xLarge.setChecked(false);
                        size = Integer.toString(Data.getXxlargeSize());;
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
// endregion RadioButton Listener

        String answer = "temp";

        //region Button Listener
        View.OnClickListener onClickListenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.play:
                        removeBotLevelList();
                        Intent intent = new Intent(StartActivity.this, SingleplayerActivity.class);
                        intent.putExtra("size", size);
                        intent.putExtra("localPlayerName", sqLiteTableManager.getName());
                        startActivity(intent);
                        break;
                    case R.id.statistic:
                        removeBotLevelList();
                        Intent statisticActivityIntent = new Intent(StartActivity.this, StatisticActivity.class);
                        startActivity(statisticActivityIntent);
                        break;
                    case R.id.userInfo:
                        removeBotLevelList();
                        Intent userInfoActivityIntent = new Intent(StartActivity.this, UserInfoActivity.class);
                        userInfoActivityIntent.putExtra("login", "User1");
                        userInfoActivityIntent.putExtra("activityName", "StartActivity");
                        startActivity(userInfoActivityIntent);
                        if (mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                            Log.i(Data.getLOG_TAG(), "onClick: The interstitial loaded yet!!!!!!!!!!!!!!!");
                        }else {
                            Log.i(Data.getLOG_TAG(), "onClick: The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.multiplayer:
                        removeBotLevelList();
                        if (multiplayerFlag){
                            Intent waitUserIntent = new Intent(StartActivity.this, WaitUserActivity.class);
                            waitUserIntent.putExtra(Data.getSize(), size);
                            startActivity(waitUserIntent);
                        }else {
                            final Snackbar snackbar = Snackbar.make(activityStartLayout, "Server is not available!", Snackbar.LENGTH_INDEFINITE);;
                            View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            };
                            snackbar.setAction("OK", snackbarOnClickListener);
                            snackbar.show();
                        }

                        break;
                    case R.id.totalTop:
                        removeBotLevelList();
                        getTotalTop(username);

                        break;
                    case R.id.botplay:
                        if (easySwitch.isChecked()){
                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
                            multiplayerBotActivity.putExtra(Data.getSize(), size);
                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getEasy());
                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
                            startActivity(multiplayerBotActivity);
                        }else if (normalSwitch.isChecked()){
                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
                            multiplayerBotActivity.putExtra(Data.getSize(), size);
                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getNormal());
                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
                            startActivity(multiplayerBotActivity);
                        }else if (hardSwitch.isChecked()){
                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
                            multiplayerBotActivity.putExtra(Data.getSize(), size);
                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getHard());
                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
                            startActivity(multiplayerBotActivity);
                        }else if (veryhardSwitch.isChecked()){
                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
                            multiplayerBotActivity.putExtra(Data.getSize(), size);
                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getVeryhard());
                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
//                            Log.i(Data.getLOG_TAG(), "onCheckedChanged: " + sqLiteTableManager.getName());
                            startActivity(multiplayerBotActivity);
                        }else {
                            if (botLinearLayout.indexOfChild(botLevelList) == -1){
                                botLinearLayout.addView(botLevelList);
                            }else {
                                botLinearLayout.removeViewAt(botLinearLayout.indexOfChild(botLevelList));
                            }
                        }
                        break;
                    case R.id.stopAds:
                        removeBotLevelList();
                        getPersonalStat(username);
//                        Animation tr = AnimationUtils.loadAnimation(StartActivity.this, R.anim.translate);
//                        onlineValue.setAnimation(tr);
//                        testImage.setAnimation(tr);
//                        postResultToDB("enemyname", "SCORE_NAH", "enemyscore", "result", "date", "username");
//                        Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
//                        multiplayerBotActivity.putExtra(Data.getSize(), size);
//                        startActivity(multiplayerBotActivity);
//                        Intent interactionIntent = new Intent(StartActivity.this, TestInteraction.class);
//                        startActivity(interactionIntent);
                        break;
                    case R.id.leanrInfo:
                        removeBotLevelList();
                        Intent learnInfoIntent = new Intent(StartActivity.this, LearnInfoActivity.class);
                        startActivity(learnInfoIntent);
                        break;
                }
            }
        };
        //endregion

        //region Switch Listener
        final CompoundButton.OnCheckedChangeListener switchChangedListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()){
                    case R.id.easySwitch:
                        if (isChecked){
//                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
//                            multiplayerBotActivity.putExtra(Data.getSize(), size);
//                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getEasy());
//                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
//                            startActivity(multiplayerBotActivity);
                            normalSwitch.setChecked(false);
                            hardSwitch.setChecked(false);
                            veryhardSwitch.setChecked(false);
                        }
                    break;
                    case R.id.normalSwitch:
                        if (isChecked){
//                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
//                            multiplayerBotActivity.putExtra(Data.getSize(), size);
//                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getNormal());
//                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
//                            startActivity(multiplayerBotActivity);
                            easySwitch.setChecked(false);
                            hardSwitch.setChecked(false);
                            veryhardSwitch.setChecked(false);
                        }
                    break;
                    case R.id.hardSwitch:
                        if (isChecked) {
//                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
//                            multiplayerBotActivity.putExtra(Data.getSize(), size);
//                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getHard());
//                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
//                            startActivity(multiplayerBotActivity);
                            normalSwitch.setChecked(false);
                            easySwitch.setChecked(false);
                            veryhardSwitch.setChecked(false);
                        }
                    break;
                    case R.id.veryhardSwitch:
                        if (isChecked){
//                            Intent multiplayerBotActivity = new Intent(StartActivity.this, MultiplayerBotActivity.class);
//                            multiplayerBotActivity.putExtra(Data.getSize(), size);
//                            multiplayerBotActivity.putExtra(Data.getLevel(), Data.getVeryhard());
//                            multiplayerBotActivity.putExtra("localPlayerName", sqLiteTableManager.getName());
////                            Log.i(Data.getLOG_TAG(), "onCheckedChanged: " + sqLiteTableManager.getName());
//                            startActivity(multiplayerBotActivity);
                            normalSwitch.setChecked(false);
                            hardSwitch.setChecked(false);
                            easySwitch.setChecked(false);
                        }
                    break;
                }
            }
        };
        //endregion


        easySwitch.setOnCheckedChangeListener(switchChangedListener);
        normalSwitch.setOnCheckedChangeListener(switchChangedListener);
        hardSwitch.setOnCheckedChangeListener(switchChangedListener);
        veryhardSwitch.setOnCheckedChangeListener(switchChangedListener);

        play.setOnClickListener(onClickListenerButton);
        multiplayer.setOnClickListener(onClickListenerButton);
        statistic.setOnClickListener(onClickListenerButton);
        userInfo.setOnClickListener(onClickListenerButton);
        stopAds.setOnClickListener(onClickListenerButton);
        totalTop.setOnClickListener(onClickListenerButton);
        botplay.setOnClickListener(onClickListenerButton);
        learnInfo.setOnClickListener(onClickListenerButton);

        requestTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                availableUsers(username, Boolean.toString(true));
            }
        }, delay, period);

    }

    private void statisticCursor(String tableName) {
        Cursor c = sqLiteDatabase.query(tableName, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(Data.getLOG_TAG(),
                        "ID = " + c.getInt(c.getColumnIndex("id")) +
                                ", user name = " + c.getString(c.getColumnIndex(Data.getDbUserNameColumn())) +
                                ", login = " + c.getString(c.getColumnIndex(Data.getDbLoginColumn())) +
                                ", country = " + c.getString(c.getColumnIndex(Data.getDbCountryColumn())) +
                                ", BF = " + c.getString(c.getColumnIndex(Data.getDbBFColumn())) +
                                ", Date = " + c.getString(c.getColumnIndex(Data.getDbDateColumn())) +
                                ", Step = " + c.getString(c.getColumnIndex(Data.getDbStepColumn())) +
                                ", Score = " + c.getString(c.getColumnIndex(Data.getDbScoreColumn())) +
                                ", Game Time = " + c.getString(c.getColumnIndex(Data.getDbGameTimeColumn()))
                );
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(Data.getLOG_TAG(), "0 rows in " + tableName);
        c.close();
    }

    private void removeBotLevelList(){
        if (botLinearLayout.indexOfChild(botLevelList) != -1){
            botLinearLayout.removeViewAt(botLinearLayout.indexOfChild(botLevelList));
        }
    }

    private void checkFirstRun() {

        Log.d(Data.getLOG_TAG(), "inside onCheckFirstRun");

        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -2;
        final int currentVersionCode = BuildConfig.VERSION_CODE;
        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Получение текущей версии кода
        Log.d(Data.getLOG_TAG(), "currentVersionCode = " + currentVersionCode);
        Log.d(Data.getLOG_TAG(), "current time = " + Data.getCurrentDate());


        // Получение сохраненной версии кода
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);
        Log.d(Data.getLOG_TAG(), "savedVersionCode = " + savedVersionCode);

        // Проверка на первый вход или апгрейд
        if (currentVersionCode == savedVersionCode) {
            // При выполнении данного условия происходит обычная загрузка приложения
            Log.d(Data.getLOG_TAG(), "currentVersionCode == savedVersionCode");
            username = sqLiteTableManager.getLogin();

            return;
        } else if (savedVersionCode == DOESNT_EXIST) {
            Log.d(Data.getLOG_TAG(), "savedVersionCode == DOESNT_EXIST");
            //Создание таблиц при первом запуске приложения
//            sqLiteTableManager.dropTable(Data.getDbStatisticTable());
            sqLiteTableManager.createTableStatistic();
            sqLiteTableManager.createTableRecord();
            sqLiteTableManager.createTableUserInfo();

            //**********
            // Запись свободного логина (типа User1234) в таблицу UserInfo при первом запуске приложения.
            // Свободный логин получается от сервера. Позже будет введена реализация этого.
            Intent userInfointent = new Intent(StartActivity.this, UserInfoActivity.class);
            userInfointent.putExtra("activityName", "StartActivityFirstRun");
            startActivity(userInfointent);
//            String freeLogin = "User1234";
//            sqLiteTableManager.insertIntoUserInfoTable(
//                    null,
//                    freeLogin,
//                    null,
//                    Data.getCurrentDate());
//            //Обновление настроек для закрытия ветки первого включения.
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
            Log.d(Data.getLOG_TAG(), "It's next sending after intent!");
//            // TODO This is a new install (or the user cleared the shared preferences)


        } else if (currentVersionCode > savedVersionCode) {

            Log.d(Data.getLOG_TAG(), "currentVersionCode > savedVersionCode");
            // TODO This is an upgrade
        }

//        // Update the shared preferences with the current version code
//        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
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

    public void availableUsers(String un, String online){
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final Snackbar snackbar = Snackbar.make(activityStartLayout, "Server is not available!", Snackbar.LENGTH_INDEFINITE);;
        View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };

        snackbar.setAction("OK", snackbarOnClickListener);
        Log.i(Data.getLOG_TAG(), "availableUsers: SENDING USERNAME IS ---------> " + un + " " + online);
        client.newCall(httpClient.availableUsers(un, online)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        view.setText(battlefield);
                        Log.i(Data.getLOG_TAG(), "StartActivity run: " + "Fail!!!!!!!!!!!!");
//                        Toast.makeText(StartActivity.this, "Network is not available!", Toast.LENGTH_SHORT).show();
                        multiplayerFlag = false;
                        if (snackbarFlag){
                            snackbar.show();
                            snackbarFlag = false;
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                final String[] answer = response.body().string().split(" ");
                multiplayerFlag = true;
                final String[] answer = response.body().string().split(" ");
                String debug = "";
                for (int i = 0; i < answer.length; i++) {
                    debug += answer[i] + " ";
                }
                Log.i(Data.getLOG_TAG(), "onResponse run for RECEIVING methods: " + debug);
                xsUsers = Integer.parseInt(answer[0]);
                sUsers = Integer.parseInt(answer[1]);
                mUsers = Integer.parseInt(answer[2]);
                lUsers = Integer.parseInt(answer[3]);
                xlUsers = Integer.parseInt(answer[4]);
                xxlUsers = Integer.parseInt(answer[5]);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        xsmallAvailableUsers.setText(answer[0]);
                        smallAvailableUsers.setText(answer[1]);
                        mediumAvailableUsers.setText(answer[2]);
                        largeAvailableUsers.setText(answer[3]);
                        xlargeAvailableUsers.setText(answer[4]);
                        xxlargeAvailableUsers.setText(answer[5]);
                        onlineValue.setText(answer[6]);
                    }
                });
            }
        });
    }

    public void getTotalTop(String un){
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final Snackbar snackbar = Snackbar.make(activityStartLayout, "Server is not available!", Snackbar.LENGTH_INDEFINITE);;
        View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };

        snackbar.setAction("OK", snackbarOnClickListener);
        Log.i(Data.getLOG_TAG(), "getTotalTop: SENDING USERNAME IS ---------> " + un);
        client.newCall(httpClient.getTotalTop(un, Data.getTopTotal())).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        view.setText(battlefield);
                        Log.i(Data.getLOG_TAG(), "StartActivity run: " + "Fail!!!!!!!!!!!!");
//                        Toast.makeText(StartActivity.this, "Network is not available!", Toast.LENGTH_SHORT).show();
                        multiplayerFlag = false;
                        if (snackbarFlag){
                            snackbar.show();
                            snackbarFlag = false;
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                final String[] answer = response.body().string().split(" ");
                multiplayerFlag = true;
                final String answer = response.body().string();
//                String bug = "";
//                for (int i = 0; i < answer.length; i++) {
//                    bug += " " + answer[i];
//
//                }
//                Log.i(Data.getLOG_TAG(), "onResponse run for TOPTOTAL methods player name: " + bug);
                jsonString = answer;
                Log.i(Data.getLOG_TAG(), "JSON_STRING " + jsonString);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent totalTopIntent = new Intent(StartActivity.this, TotalTopActivity.class);
                        totalTopIntent.putExtra("total", jsonString);
                        startActivity(totalTopIntent);
                        try{
                            JSONObject jsonObject = new JSONObject(jsonString);
                            int size = jsonObject.getInt("size");
                            for (int i = 1; i < size; i++) {
                                JSONArray array = jsonObject.getJSONArray("" + i);
                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 0 " + array.getString(0));
                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 1 " + array.getString(1));
                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 2 " + array.getString(2));
                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 3 " + array.getString(3));
                            }
                        }catch(JSONException jex){
                            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + jex.toString());
                        }
                    }
                });
            }
        });
    }

    public void getPersonalStat(String un){
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final Snackbar snackbar = Snackbar.make(activityStartLayout, "Server is not available!", Snackbar.LENGTH_INDEFINITE);;
        View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };

        snackbar.setAction("OK", snackbarOnClickListener);
        Log.i(Data.getLOG_TAG(), "getTotalTop: SENDING USERNAME IS ---------> " + un);
        client.newCall(httpClient.getTotalTop(un, Data.getPersonalStat())).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        view.setText(battlefield);
                        Log.i(Data.getLOG_TAG(), "StartActivity run: " + "Fail!!!!!!!!!!!!");
//                        Toast.makeText(StartActivity.this, "Network is not available!", Toast.LENGTH_SHORT).show();
                        multiplayerFlag = false;
                        if (snackbarFlag){
                            snackbar.show();
                            snackbarFlag = false;
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                final String[] answer = response.body().string().split(" ");
                multiplayerFlag = true;
                final String answer = response.body().string();
//                String bug = "";
//                for (int i = 0; i < answer.length; i++) {
//                    bug += " " + answer[i];
//
//                }
//                Log.i(Data.getLOG_TAG(), "onResponse run for TOPTOTAL methods player name: " + bug);
                jsonString = answer;
                Log.i(Data.getLOG_TAG(), "JSON_STRING " + jsonString);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Intent totalTopIntent = new Intent(StartActivity.this, TotalTopActivity.class);
//                        totalTopIntent.putExtra("total", jsonString);
//                        startActivity(totalTopIntent);
//                        try{
//                            JSONObject jsonObject = new JSONObject(jsonString);
//                            int size = jsonObject.getInt("size");
//                            for (int i = 1; i < size; i++) {
//                                JSONArray array = jsonObject.getJSONArray("" + i);
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 0 " + array.getString(0));
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 1 " + array.getString(1));
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 2 " + array.getString(2));
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 3 " + array.getString(3));
//                            }
//                        }catch(JSONException jex){
//                            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + jex.toString());
//                        }
                    }
                });
            }
        });
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
        availableUsers(username, Boolean.toString(false));
        Log.i(Data.getLOG_TAG(), "onStop: StartActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        snackbarFlag = true;
        requestTimer = new Timer();
        requestTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                availableUsers(username, Boolean.toString(true));
            }
        }, delay, period);
        Log.i(Data.getLOG_TAG(), "onRestart: StartActivity");
    }

    public void postResultToDB(String enemyUsername, String score, String enemyScore, String result, String date, String username){
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        String json = "";
        JSONObject tableRow = new JSONObject();
        try{
            tableRow.put(Data.getEnemyUsername(), enemyUsername);
            tableRow.put(Data.getScore(), score);
            tableRow.put(Data.getEnemyScore(), enemyScore);
            tableRow.put(Data.getResult(), result);
            tableRow.put(Data.getDate(), date);
            tableRow.put(Data.getUsername(), username);
            json = tableRow.toString();
            Log.i(Data.getLOG_TAG(), "run: " + json);
        }catch (JSONException js){
            Log.i(Data.getLOG_TAG(), "postResultToDB: " + js.toString());
        }

        client.newCall(httpClient.postResultToDB(json)).enqueue(new Callback() {
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
                        Log.i(Data.getLOG_TAG(), "run: " + answer);
                    }
                });
            }
        });
    }

    private RadioButton xSmall;
    private RadioButton small;
    private RadioButton medium;
    private RadioButton large;
    private RadioButton xLarge;
    private RadioButton xxLarge;

    private TextView xsmallAvailableUsers;
    private TextView smallAvailableUsers;
    private TextView mediumAvailableUsers;
    private TextView largeAvailableUsers;
    private TextView xlargeAvailableUsers;
    private TextView xxlargeAvailableUsers;
    private TextView onlineValue;

    private RadioGroup rg1;
    private RadioGroup rg2;

    private Button play;
    private Button multiplayer;
    private Button statistic;
    private Button stopAds;
    private Button userInfo;
    private Button totalTop;
    private Button botplay;
    private Button learnInfo;

//    private ImageView testImage;
//    private ImageView testImage2;

    private Switch easySwitch;
    private Switch normalSwitch;
    private Switch hardSwitch;
    private Switch veryhardSwitch;

    private LinearLayout activityStartLayout;
    private LinearLayout botLinearLayout;
    private View botLevelList;

    private ContentValues contentValues;

    private SQLiteDatabase sqLiteDatabase;
    private SqLiteTableManager sqLiteTableManager;
    private DBHelper dbHelper = new DBHelper(StartActivity.this);

    private HttpClient httpClient;
    private OkHttpClient client;

    private String size = "36";
    private final String PREFS_NAME = "MyPrefsFile103";

    private Integer xsUsers;
    private Integer sUsers;
    private Integer mUsers;
    private Integer lUsers;
    private Integer xlUsers;
    private Integer xxlUsers;
    private int countFinish;
    private long delay = 1000;
    private long period = 2000;
    private Boolean snackbarFlag;
    private Boolean multiplayerFlag;
    private String username;
    private String jsonString;

    private Timer requestTimer;

    private InterstitialAd mInterstitialAd;

}
