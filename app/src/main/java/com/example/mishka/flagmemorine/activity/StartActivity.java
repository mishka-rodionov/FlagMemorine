package com.example.mishka.flagmemorine.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        contentValues = new ContentValues();

        sqLiteDatabase = dbHelper.getWritableDatabase();

        sqLiteDatabase.execSQL("create table if not exists " + Data.getDbRecordTable()
                + " ("
                + "id integer primary key autoincrement,"
                + Data.getDbUserNameColumn() + " text,"
                + Data.getDbLoginColumn() + " text,"
                + Data.getDbCountryColumn() + " text,"
                + Data.getDbBFColumn() + " text,"
                + Data.getDbDateColumn() + " text,"
                + Data.getDbStepColumn() + " text,"
                + Data.getDbScoreColumn() + " text"
                + ");");

        sqLiteDatabase.execSQL("create table if not exists test " +
                "(" +
                "temp text," +
                "second text" +
                ");");

        sqLiteDatabase.execSQL("create table if not exists " + Data.getDbStatisticTable()
                + " ("
                + "id integer primary key autoincrement,"
                + Data.getDbUserNameColumn() + " text,"
                + Data.getDbLoginColumn() + " text,"
                + Data.getDbCountryColumn() + " text,"
                + Data.getDbBFColumn() + " text,"
                + Data.getDbDateColumn() + " text,"
                + Data.getDbStepColumn() + " text,"
                + Data.getDbScoreColumn() + " text"
                + ");");


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
                        Log.d(Data.getLOG_TAG(), "create table if not exists " + Data.getDbRecordTable()
                                + " ("
                                + "id integer primary key autoincrement,"
                                + Data.getDbUserNameColumn() + " text,"
                                + Data.getDbLoginColumn() + " text,"
                                + Data.getDbCountryColumn() + " text,"
                                + Data.getDbBFColumn() + " text,"
                                + Data.getDbDateColumn() + " text,"
                                + Data.getDbStepColumn() + " text,"
                                + Data.getDbScoreColumn() + " text"
                                + ");");
//                        contentValues.put(Data.getDbUserNameColumn(), Data.getUserName());
//                        contentValues.put(Data.getDbLoginColumn(), Data.getLogin());
//                        contentValues.put(Data.getDbCountryColumn(), Data.getUserCountry());
//                        sqLiteDatabase.insert("Record", null, contentValues);
//                        contentValues.clear();
                        contentValues.put("temp", Data.getUserName());
                        contentValues.put("second", Data.getLogin());
                        sqLiteDatabase.insert("test", null, contentValues);
                        contentValues.clear();
                        statisticCursor(Data.getDbStatisticTable());
                        statisticCursor(Data.getDbRecordTable());
//                        statisticCursor("test");
                        break;
                }
            }
        };

        play.setOnClickListener(onClickListenerButton);
        statistic.setOnClickListener(onClickListenerButton);

    }

    private void statisticCursor(String tableName) {
        Cursor c = sqLiteDatabase.query(tableName, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
//            int idColIndex = c.getColumnIndex("id");
//            int nameColIndex = c.getColumnIndex("name");
//            int stateColIndex = c.getColumnIndex("state");
//            int stateOSColIndex = c.getColumnIndex("stateOS");
//            int temperatureColIndex = c.getColumnIndex("temperature");
//            int date_timeColIndex = c.getColumnIndex("date_time");
//            int idTemp = c.getColumnIndex("temp");
//            int idSecond = c.getColumnIndex("second");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(Data.getLOG_TAG(),
//                        "temp = " + c.getString(idTemp) +
//                                " second = " + c.getString(idSecond)
                        "ID = " + c.getInt(c.getColumnIndex("id")) +
                                ", user name = " + c.getString(c.getColumnIndex(Data.getDbUserNameColumn())) +
                                ", login = " + c.getString(c.getColumnIndex(Data.getDbLoginColumn())) +
                                ", country = " + c.getString(c.getColumnIndex(Data.getDbCountryColumn())) +
                                ", BF = " + c.getString(c.getColumnIndex(Data.getDbBFColumn())) +
                                ", Date = " + c.getString(c.getColumnIndex(Data.getDbDateColumn())) +
                                ", Step = " + c.getString(c.getColumnIndex(Data.getDbStepColumn())) +
                                ", Score = " + c.getString(c.getColumnIndex(Data.getDbScoreColumn()))
                );
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(Data.getLOG_TAG(), "0 rows in " + tableName);
        c.close();
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
    private DBHelper dbHelper = new DBHelper(StartActivity.this);

    private String size = "36";
}
