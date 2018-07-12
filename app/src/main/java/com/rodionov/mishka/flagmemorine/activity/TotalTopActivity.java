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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class TotalTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_top);

        hideSystemUI();

        statisticToolbar = (Toolbar) findViewById(R.id.totalStatistic_toolbar);
        setSupportActionBar(statisticToolbar);
        statisticActionBar = getSupportActionBar();
        statisticActionBar.setDisplayHomeAsUpEnabled(true);
        statisticActionBar.setTitle("");
        // Инициализация менеджера по работе с БД.
        sqLiteTableManager = new SqLiteTableManager(TotalTopActivity.this);

        CountryList.loadCountryMap();
        jsonString = getIntent().getStringExtra("total");
//        JSONObject jsonObject = new JSONObject(jsonString);

        // Инициализация контейнера плейеров.
        Player.initPlayers();

        // Инициализация структуры отображения данных
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewTopTotal);
//        recyclerView.setHasFixedSize(true);
        // Менеджер компоновки для данного активити.
        llm = new LinearLayoutManager(TotalTopActivity.this);
        recyclerView.setLayoutManager(llm);
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);
        specification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_restart).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    // 24.03.18
    // Метод детализации отображения статистических данных. Ограничивает выборку, используя критерий
    // размера поля.
    private void specification() {
        Player.clearPlayers();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            int size = jsonObject.getInt("size");
//            ArrayList<String> pl = sqLiteTableManager.getGroup(size, period);
            String[] row = new String[2];
            for (int i = 1; i < size; i++) {
                JSONArray array = jsonObject.getJSONArray("" + i);
                Player.addPlayer(new Player(new String(array.getString(1).getBytes("UTF-8"), "UTF-8"),
                        array.getString(2),
                        Integer.parseInt(array.getString(3)),0, 0, 0, 0, 0,
                        ""));
                Log.i(Data.getLOG_TAG(), "specification: " + array.getString(1));
            }
        }catch (JSONException jex){
            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + jex.toString());
        }catch (UnsupportedEncodingException uns){
            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + uns.toString());
        }

        Player.sortPlayers();
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);
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

    private String jsonString;

    private SqLiteTableManager sqLiteTableManager;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private CRecyclerViewAdapter adapter;
    private Toolbar statisticToolbar;
    private ActionBar statisticActionBar;
}
