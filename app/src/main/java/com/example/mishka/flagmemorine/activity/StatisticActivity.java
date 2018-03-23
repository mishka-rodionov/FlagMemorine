package com.example.mishka.flagmemorine.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.cView.CRecyclerViewAdapter;
import com.example.mishka.flagmemorine.logic.CountryList;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.Player;
import com.example.mishka.flagmemorine.service.DBHelper;
import com.example.mishka.flagmemorine.service.SqLiteTableManager;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        sqLiteTableManager = new SqLiteTableManager(StatisticActivity.this);

        CountryList.loadCountryMap();

        Player.initPlayers();
        Player player1 = new Player(sqLiteTableManager.getName() == null ? sqLiteTableManager.getLogin() : sqLiteTableManager.getName(),
                sqLiteTableManager.getCountry() == null ? "Olympics" : sqLiteTableManager.getCountry(),
                sqLiteTableManager.getScore(Data.getXsmallSize()),
                sqLiteTableManager.getScore(Data.getSmallSize()),
                sqLiteTableManager.getScore(Data.getMediumSize()),
                sqLiteTableManager.getScore(Data.getLargeSize()),
                sqLiteTableManager.getScore(Data.getXlargeSize()),
                sqLiteTableManager.getScore(Data.getXxlargeSize()));
        Player player2 = new Player("Petya", "Ukraine",  10, 0, 0, 0 ,0 ,0);
        Player player3 = new Player("Vasya", "Belarus",  20, 10, 0, 0 ,0 ,0);
        Player player4 = new Player("Borya", "Armenia",  60, 0, 0, 0 ,0 ,0);
        Player player5 = new Player("Fedya", "Azerbaijan",  30, 10, 0, 0 ,0 ,0);
        Player player6 = new Player("Kolya", "Kazakhstan",  100, 0, 0, 0 ,0 ,0);
        Player.addPlayer(player1);
        Player.addPlayer(player2);
        Player.addPlayer(player3);
        Player.addPlayer(player4);
        Player.addPlayer(player5);
        Player.addPlayer(player6);

        Player.sortPlayers();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(StatisticActivity.this);
        recyclerView.setLayoutManager(llm);
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);
        xS = (ToggleButton) findViewById(R.id.xsTB);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.xsTB:
                        Player.clearPlayers();
                        ArrayList<String> pl = sqLiteTableManager.getGroup(Data.getXsmallSize(),"time");
                        for (int i = 0; i < pl.size(); i++) {
                            Player.addPlayer(new Player(sqLiteTableManager.getName() == null ? sqLiteTableManager.getLogin() : sqLiteTableManager.getName(),
                                    sqLiteTableManager.getCountry() == null ? "Olympics" : sqLiteTableManager.getCountry(),
                                    Integer.parseInt(pl.get(i)),0, 0, 0, 0, 0));
                        }
                        Player.sortPlayers();
                        adapter = new CRecyclerViewAdapter(Player.getPlayers());
                        recyclerView.setAdapter(adapter);
                        break;
                }
            }
        };
        xS.setOnClickListener(onClickListener);

    }

    private SqLiteTableManager sqLiteTableManager;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private CRecyclerViewAdapter adapter;
    private ToggleButton xS;
}
