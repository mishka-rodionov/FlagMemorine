package com.rodionov.mishka.flagmemorine.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.support.design.BuildConfig;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

public class StartActivity extends AppCompatActivity {

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

        contentValues = new ContentValues();

//        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteTableManager = new SqLiteTableManager(StartActivity.this);
        client = new OkHttpClient();
        httpClient = new HttpClient();

        activityStartLayout = (LinearLayout) findViewById(R.id.activity_start_layout);
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

//        rg1 = (RadioGroup) findViewById(R.id.rg1);
//        rg2 = (RadioGroup) findViewById(R.id.rg2);

        play = (Button) findViewById(R.id.play);
        multiplayer = (Button) findViewById(R.id.multiplayer);
        statistic = (Button) findViewById(R.id.statistic);
        stopAds = (Button) findViewById(R.id.stopAds);
        userInfo = (Button) findViewById(R.id.userInfo);
        totalTop = (Button) findViewById(R.id.totalTop);

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

        View.OnClickListener onClickListenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.play:
                        Intent intent = new Intent(StartActivity.this, BattleFieldActivity.class);
                        intent.putExtra("size", size);
                        startActivity(intent);
                        break;
                    case R.id.statistic:
                        Intent statisticActivityIntent = new Intent(StartActivity.this, StatisticActivity.class);
                        startActivity(statisticActivityIntent);
                        break;
                    case R.id.userInfo:
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
                        getTotalTop(username);
                        break;
                    case R.id.stopAds:

//                        Intent interactionIntent = new Intent(StartActivity.this, TestInteraction.class);
//                        startActivity(interactionIntent);
                        break;
                }
            }
        };

        play.setOnClickListener(onClickListenerButton);
        multiplayer.setOnClickListener(onClickListenerButton);
        statistic.setOnClickListener(onClickListenerButton);
        userInfo.setOnClickListener(onClickListenerButton);
        stopAds.setOnClickListener(onClickListenerButton);
        totalTop.setOnClickListener(onClickListenerButton);

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
        client.newCall(httpClient.getTotalTop(un)).enqueue(new Callback() {
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
                Log.i(Data.getLOG_TAG(), "onResponse run for TOPTOTAL methods: " + answer);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String username = data.getStringExtra("username");
//        String playername = data.getStringExtra("playername");
//        sqLiteTableManager.insertIntoUserInfoTable(
//                null,
//                username,
//                null,
//                Data.getCurrentDate());
//        //Обновление настроек для закрытия ветки первого включения.
//
//        Log.d(Data.getLOG_TAG(), "It's next sending after intent!");
//    }

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

    private LinearLayout activityStartLayout;

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
    private long delay = 1000;
    private long period = 2000;
    private Boolean snackbarFlag;
    private Boolean multiplayerFlag;
    private String username;

    private Timer requestTimer;

    private InterstitialAd mInterstitialAd;

}
