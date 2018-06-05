package com.rodionov.mishka.flagmemorine.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.cView.CRecyclerViewAdapter;
import com.rodionov.mishka.flagmemorine.logic.CountryList;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.Player;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import java.util.ArrayList;
import java.util.Date;

public class StatisticActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TargetApi(26)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        hideSystemUI();

        statisticToolbar = (Toolbar) findViewById(R.id.statistic_toolbar);
        setSupportActionBar(statisticToolbar);
        statisticActionBar = getSupportActionBar();
        statisticActionBar.setDisplayHomeAsUpEnabled(true);
        statisticActionBar.setTitle("");
        // Инициализация менеджера по работе с БД.
        sqLiteTableManager = new SqLiteTableManager(StatisticActivity.this);

        CountryList.loadCountryMap();

        // Инициализация контейнера плейеров.
        Player.initPlayers();

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

        // Обработчик нажатия на кнопку выбора размкра поля
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @TargetApi(26)
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.xsTB:
                        setBackgroundColorTB(xS);
                        statisticTimeInterval(Data.getXsmallSize());
                        break;
                    case R.id.sTB:
                        setBackgroundColorTB(S);
                        statisticTimeInterval(Data.getSmallSize());
                        break;
                    case R.id.mTB:
                        setBackgroundColorTB(M);
                        statisticTimeInterval(Data.getMediumSize());
                        break;
                    case R.id.lTB:
                        setBackgroundColorTB(L);
                        statisticTimeInterval(Data.getLargeSize());
                        break;
                    case R.id.xlTB:
                        setBackgroundColorTB(xL);
                        statisticTimeInterval(Data.getXlargeSize());
                        break;
                    case R.id.xxlTB:
                        setBackgroundColorTB(xxL);
                        statisticTimeInterval(Data.getXxlargeSize());
                        break;
                }
            }
        };

        // Обработчик нажатия на кнопку выбора периода детализации статистики (час, день, полный период).
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
        setBackgroundColorTB(xS);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void statisticTimeInterval(Integer size){
        if (hour.isChecked()){
            specification(size, Data.getMillisInHour());
        }else if (day.isChecked()){
            specification(size, Data.getMillisInDay());
        }else {
            Date currentDate = new Date();
            Long currentTime = currentDate.getTime();
            Log.i(Data.getLOG_TAG(), "onClick current time: " + currentTime);
            specification(size, currentTime);
        }
    }

    // 24.03.18
    // Метод детализации отображения статистических данных. Ограничивает выборку, используя критерий
    // размера поля.
    private void specification(Integer size, Long period) {
        Player.clearPlayers();
        ArrayList<String> pl = sqLiteTableManager.getGroup(size, period);
        String[] row = new String[2];
        for (int i = 0; i < pl.size(); i++) {
            row = pl.get(i).split(" ");
            Log.i(Data.getLOG_TAG(), "specification: row[1] = " + row[1]);
            Player.addPlayer(new Player(sqLiteTableManager.getName() == null ?
                    sqLiteTableManager.getLogin() : sqLiteTableManager.getName(),
                    sqLiteTableManager.getCountry() == null ? "Olympics" : sqLiteTableManager.getCountry(),
                    Integer.parseInt(row[0]),0, 0, 0, 0, 0,
                     row[3] + " " + row[2] + " " + row[6] + " " + row[4]));
        }
        Player.sortPlayers();
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);
    }

    private void setBackgroundColorTB(View v){
        switch (v.getId()){
            case R.id.xsTB:
                xS.setTextColor(Color.GREEN);
                S.setTextColor(Color.BLACK);
                M.setTextColor(Color.BLACK);
                L.setTextColor(Color.BLACK);
                xL.setTextColor(Color.BLACK);
                xxL.setTextColor(Color.BLACK);
                break;
            case R.id.sTB:
                xS.setTextColor(Color.BLACK);
                S.setTextColor(Color.GREEN);
                M.setTextColor(Color.BLACK);
                L.setTextColor(Color.BLACK);
                xL.setTextColor(Color.BLACK);
                xxL.setTextColor(Color.BLACK);
                break;
            case R.id.mTB:
                xS.setTextColor(Color.BLACK);
                S.setTextColor(Color.BLACK);
                M.setTextColor(Color.GREEN);
                L.setTextColor(Color.BLACK);
                xL.setTextColor(Color.BLACK);
                xxL.setTextColor(Color.BLACK);
                break;
            case R.id.lTB:
                xS.setTextColor(Color.BLACK);
                S.setTextColor(Color.BLACK);
                M.setTextColor(Color.BLACK);
                L.setTextColor(Color.GREEN);
                xL.setTextColor(Color.BLACK);
                xxL.setTextColor(Color.BLACK);
                break;
            case R.id.xlTB:
                xS.setTextColor(Color.BLACK);
                S.setTextColor(Color.BLACK);
                M.setTextColor(Color.BLACK);
                L.setTextColor(Color.BLACK);
                xL.setTextColor(Color.GREEN);
                xxL.setTextColor(Color.BLACK);
                break;
            case R.id.xxlTB:
                xS.setTextColor(Color.BLACK);
                S.setTextColor(Color.BLACK);
                M.setTextColor(Color.BLACK);
                L.setTextColor(Color.BLACK);
                xL.setTextColor(Color.BLACK);
                xxL.setTextColor(Color.GREEN);
                break;
        }
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
    private Toolbar statisticToolbar;
    private ActionBar statisticActionBar;
}
