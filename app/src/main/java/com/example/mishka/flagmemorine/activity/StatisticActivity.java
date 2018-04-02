package com.example.mishka.flagmemorine.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.cView.CRecyclerViewAdapter;
import com.example.mishka.flagmemorine.logic.CountryList;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.Player;
import com.example.mishka.flagmemorine.service.SqLiteTableManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatisticActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TargetApi(26)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        // Инициализация менеджера по работе с БД.
        sqLiteTableManager = new SqLiteTableManager(StatisticActivity.this);

        CountryList.loadCountryMap();

        // Инициализация контейнера плейеров.
        Player.initPlayers();
//        Player player1 = new Player(sqLiteTableManager.getName() == null ? sqLiteTableManager.getLogin() : sqLiteTableManager.getName(),
//                sqLiteTableManager.getCountry() == null ? "Olympics" : sqLiteTableManager.getCountry(),
//                sqLiteTableManager.getScore(Data.getXsmallSize()),
//                sqLiteTableManager.getScore(Data.getSmallSize()),
//                sqLiteTableManager.getScore(Data.getMediumSize()),
//                sqLiteTableManager.getScore(Data.getLargeSize()),
//                sqLiteTableManager.getScore(Data.getXlargeSize()),
//                sqLiteTableManager.getScore(Data.getXxlargeSize()));
//        Player player2 = new Player("Petya", "Ukraine",  10, 0, 0, 0 ,0 ,0);
//        Player player3 = new Player("Vasya", "Belarus",  20, 10, 0, 0 ,0 ,0);
//        Player player4 = new Player("Borya", "Armenia",  60, 0, 0, 0 ,0 ,0);
//        Player player5 = new Player("Fedya", "Azerbaijan",  30, 10, 0, 0 ,0 ,0);
//        Player player6 = new Player("Kolya", "Kazakhstan",  100, 0, 0, 0 ,0 ,0);
//        Player.addPlayer(player1);
//        Player.addPlayer(player2);
//        Player.addPlayer(player3);
//        Player.addPlayer(player4);
//        Player.addPlayer(player5);
//        Player.addPlayer(player6);
//
//        Player.sortPlayers();

        // Инициализация структуры отображения данных
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
        // Менеджер компоновки для данного активити.
        llm = new LinearLayoutManager(StatisticActivity.this);
        recyclerView.setLayoutManager(llm);
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);

        xS = (ToggleButton) findViewById(R.id.xsTB);
        S = (ToggleButton) findViewById(R.id.sTB);
        M = (ToggleButton) findViewById(R.id.mTB);
        L = (ToggleButton) findViewById(R.id.lTB);
        xL = (ToggleButton) findViewById(R.id.xlTB);
        xxL = (ToggleButton) findViewById(R.id.xxlTB);

        hour = (ToggleButton) findViewById(R.id.hourTB);
        day = (ToggleButton) findViewById(R.id.dayTB);
        all = (ToggleButton) findViewById(R.id.allTB);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @TargetApi(26)
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.xsTB:
                        if (hour.isChecked()){
                            specification(Data.getXsmallSize(), Data.getMillisInHour());
                        }else if (day.isChecked()){
                            specification(Data.getXsmallSize(), Data.getMillisInDay());
                        }else {
                            Date currentDate = new Date();
                            Long currentTime = currentDate.getTime();
                            Log.i(Data.getLOG_TAG(), "onClick current time: " + currentTime);
                            specification(Data.getXsmallSize(), currentTime);
                        }
                        break;
                    case R.id.sTB:
                        if (hour.isChecked()){
                            specification(Data.getSmallSize(), Data.getMillisInHour());
                        }else if (day.isChecked()){
                            specification(Data.getSmallSize(), Data.getMillisInDay());
                        }else {
                            Date currentDate = new Date();
                            Long currentTime = currentDate.getTime();
                            Log.i(Data.getLOG_TAG(), "onClick current time: " + currentTime);
                            specification(Data.getSmallSize(), currentTime);
                        }
                        break;
                    case R.id.mTB:
                        if (hour.isChecked()){
                            specification(Data.getMediumSize(), Data.getMillisInHour());
                        }else if (day.isChecked()){
                            specification(Data.getMediumSize(), Data.getMillisInDay());
                        }else {
                            Date currentDate = new Date();
                            Long currentTime = currentDate.getTime();
                            Log.i(Data.getLOG_TAG(), "onClick current time: " + currentTime);
                            specification(Data.getMediumSize(), currentTime);
                        }
                        break;
                    case R.id.lTB:
                        if (hour.isChecked()){
                            specification(Data.getLargeSize(), Data.getMillisInHour());
                        }else if (day.isChecked()){
                            specification(Data.getLargeSize(), Data.getMillisInDay());
                        }else {
                            Date currentDate = new Date();
                            Long currentTime = currentDate.getTime();
                            Log.i(Data.getLOG_TAG(), "onClick current time: " + currentTime);
                            specification(Data.getLargeSize(), currentTime);
                        }
                        break;
                    case R.id.xlTB:
                        if (hour.isChecked()){
                            specification(Data.getXlargeSize(), Data.getMillisInHour());
                        }else if (day.isChecked()){
                            specification(Data.getXlargeSize(), Data.getMillisInDay());
                        }else {
                            Date currentDate = new Date();
                            Long currentTime = currentDate.getTime();
                            Log.i(Data.getLOG_TAG(), "onClick current time: " + currentTime);
                            specification(Data.getXlargeSize(), currentTime);
                        }
                        break;
                    case R.id.xxlTB:
                        if (hour.isChecked()){
                            specification(Data.getXxlargeSize(), Data.getMillisInHour());
                        }else if (day.isChecked()){
                            specification(Data.getXxlargeSize(), Data.getMillisInDay());
                        }else {
                            Date currentDate = new Date();
                            Long currentTime = currentDate.getTime();
                            Log.i(Data.getLOG_TAG(), "onClick current time: " + currentTime);
                            specification(Data.getXxlargeSize(), currentTime);
                        }
                        break;
                }
            }
        };

        View.OnClickListener periodToggleButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.hourTB:
                        if (hour.isChecked()){
                            day.setChecked(false);
                            all.setChecked(false);
                        }else{
                            all.setChecked(true);
                        }
                        break;
                    case R.id.dayTB:
                        if (day.isChecked()){
                            hour.setChecked(false);
                            all.setChecked(false);
                        }else{
                            all.setChecked(true);
                        }
                        break;
                    case R.id.allTB:
                        if (all.isChecked()){
                            day.setChecked(false);
                            hour.setChecked(false);
                        }else {
                            hour.setChecked(true);
                        }

                        break;
                }
            }
        };

        // Установка слушателя на кнопки, с помощью которых можно выбрать результаты конкретно для
        // определенного игрового поля.
        xS.setOnClickListener(onClickListener);
        S.setOnClickListener(onClickListener);
        M.setOnClickListener(onClickListener);
        L.setOnClickListener(onClickListener);
        xL.setOnClickListener(onClickListener);
        xxL.setOnClickListener(onClickListener);

        hour.setOnClickListener(periodToggleButton);
        day.setOnClickListener(periodToggleButton);
        all.setOnClickListener(periodToggleButton);

        //Start conditions
        all.setChecked(true);
        xS.setChecked(true);
        specification(Data.getXsmallSize(), new Date().getTime());

    }

    // 24.03.18
    // Метод детализации отображения статистических данных. Ограничивает выборку, используя критерий
    // размера поля.
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void specification(Integer size, Long period) {
        Player.clearPlayers();
        ArrayList<String> pl = sqLiteTableManager.getGroup(size, period);
        for (int i = 0; i < pl.size(); i++) {
            Player.addPlayer(new Player(sqLiteTableManager.getName() == null ?
                    sqLiteTableManager.getLogin() : sqLiteTableManager.getName(),
                    sqLiteTableManager.getCountry() == null ? "Olympics" : sqLiteTableManager.getCountry(),
                    Integer.parseInt(pl.get(i)),0, 0, 0, 0, 0));
        }
        Player.sortPlayers();
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);
    }

    private SqLiteTableManager sqLiteTableManager;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private CRecyclerViewAdapter adapter;
    private ToggleButton xS;
    private ToggleButton S;
    private ToggleButton M;
    private ToggleButton L;
    private ToggleButton xL;
    private ToggleButton xxL;
    private ToggleButton hour;
    private ToggleButton day;
    private ToggleButton all;
}
