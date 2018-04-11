package com.example.mishka.flagmemorine.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.BuildConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.service.DBHelper;
import com.example.mishka.flagmemorine.service.SqLiteTableManager;

import java.text.DateFormat;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        hideSystemUI();

        contentValues = new ContentValues();

//        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteTableManager = new SqLiteTableManager(StartActivity.this);

        checkFirstRun();

//        sqLiteDatabase.execSQL("create table if not exists " + Data.getDbRecordTable()
//                + " ("
//                + "id integer primary key autoincrement,"
//                + Data.getDbUserNameColumn() + " text,"
//                + Data.getDbLoginColumn() + " text,"
//                + Data.getDbCountryColumn() + " text,"
//                + Data.getDbBFColumn() + " text,"
//                + Data.getDbGameTimeColumn() + " text,"
//                + Data.getDbStepColumn() + " text,"
//                + Data.getDbScoreColumn() + " text,"
//                + Data.getDbDateColumn() + " text"
//                + ");");
//
//        sqLiteDatabase.execSQL("create table if not exists test " +
//                "(" +
//                "temp text," +
//                "second text" +
//                ");");
//
//        sqLiteDatabase.execSQL("create table if not exists " + Data.getDbStatisticTable()
//                + " ("
//                + "id integer primary key autoincrement,"
//                + Data.getDbUserNameColumn() + " text,"
//                + Data.getDbLoginColumn() + " text,"
//                + Data.getDbCountryColumn() + " text,"
//                + Data.getDbBFColumn() + " text,"
//                + Data.getDbGameTimeColumn() + " text,"
//                + Data.getDbStepColumn() + " text,"
//                + Data.getDbScoreColumn() + " text,"
//                + Data.getDbDateColumn() + " text"
//                + ");");
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

//        rg1.addView(xSmall);
//        rg1.addView(small);
//        rg1.addView(medium);
//        rg2.addView(large);
//        rg2.addView(xLarge);
//        rg2.addView(xxLarge);

        View.OnClickListener onClickListenerRB = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.xsmall:
                        rg2.clearCheck();
                        size = "8";
                        break;
                    case R.id.small:
                        rg2.clearCheck();
                        size = "12";
                        break;
                    case R.id.medium:
                        rg2.clearCheck();
                        size = "16";
                        break;
                    case R.id.large:
                        rg1.clearCheck();
                        size = "24";
                        break;
                    case R.id.xlarge:
                        rg1.clearCheck();
                        size = "30";
                        break;
                    case R.id.xxlarge:
                        rg1.clearCheck();
                        size = "36";
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
                    case R.id.statistic:
//                        Log.d(Data.getLOG_TAG(), "create table if not exists " + Data.getDbRecordTable()
//                                + " ("
//                                + "id integer primary key autoincrement,"
//                                + Data.getDbUserNameColumn() + " text,"
//                                + Data.getDbLoginColumn() + " text,"
//                                + Data.getDbCountryColumn() + " text,"
//                                + Data.getDbBFColumn() + " text,"
//                                + Data.getDbGameTimeColumn() + " text,"
//                                + Data.getDbStepColumn() + " text,"
//                                + Data.getDbScoreColumn() + " text,"
//                                + Data.getDbDateColumn() + " text"
//                                + ");");
//                        contentValues.put(Data.getDbUserNameColumn(), Data.getUserName());
//                        contentValues.put(Data.getDbLoginColumn(), Data.getLogin());
//                        contentValues.put(Data.getDbCountryColumn(), Data.getUserCountry());
//                        sqLiteDatabase.insert("Record", null, contentValues);
//                        contentValues.clear();
//                        contentValues.put("temp", Data.getUserName());
//                        contentValues.put("second", Data.getLogin());
//                        sqLiteDatabase.insert(Data.getDbRecordTable(), null, contentValues);
//                        contentValues.clear();
//                        statisticCursor(Data.getDbStatisticTable());
//                        statisticCursor(Data.getDbRecordTable());

                        Intent statisticActivityIntent = new Intent(StartActivity.this, StatisticActivity.class);
                        startActivity(statisticActivityIntent);
//                        statisticCursor("test");
                        break;
                    case R.id.userInfo:
                        Intent userInfoActivityIntent = new Intent(StartActivity.this, UserInfoActivity.class);
                        userInfoActivityIntent.putExtra("login", "User1");
                        startActivity(userInfoActivityIntent);
                        break;
                }
            }
        };

        play.setOnClickListener(onClickListenerButton);
        statistic.setOnClickListener(onClickListenerButton);
        userInfo.setOnClickListener(onClickListenerButton);

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
            String freeLogin = "User1234";
            sqLiteTableManager.insertIntoUserInfoTable(
                    null,
                    freeLogin,
                    null,
                    Data.getCurrentDate());
            //Обновление настроек для закрытия ветки первого включения.
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
            Log.d(Data.getLOG_TAG(), "It's next action after intent!");
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

    private ContentValues contentValues;

    private SQLiteDatabase sqLiteDatabase;
    private SqLiteTableManager sqLiteTableManager;
    private DBHelper dbHelper = new DBHelper(StartActivity.this);

    private String size = "36";
    private final String PREFS_NAME = "MyPrefsFile103";
}
