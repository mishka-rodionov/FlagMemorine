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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

//        DBHelper dbHelper = new DBHelper(this);
//        sqLiteDatabase = dbHelper.getWritableDatabase();

        CountryList.loadCountryMap();

        Player.initPlayers();
        Player player1 = new Player("Mishka", "Russia", 0, sqLiteTableManager.getScore(Data.getXsmallSize()), 0, 0, 0 ,0 ,0);
        Player player2 = new Player("Petya", "Ukraine", 10, 10, 0, 0, 0 ,0 ,0);
        Player player3 = new Player("Vasya", "Belarus", 20, 10, 10, 0, 0 ,0 ,0);
        Player player4 = new Player("Borya", "Armenia", 10, 10, 0, 0, 0 ,0 ,0);
        Player player5 = new Player("Fedya", "Azerbaijan", 20, 10, 10, 0, 0 ,0 ,0);
        Player player6 = new Player("Kolya", "Kazakhstan", 100, 100, 0, 0, 0 ,0 ,0);
        Player.addPlayer(player1);
        Player.addPlayer(player2);
        Player.addPlayer(player3);
        Player.addPlayer(player4);
        Player.addPlayer(player5);
        Player.addPlayer(player6);

        Player.sortPlayers();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(StatisticActivity.this);
        recyclerView.setLayoutManager(llm);
        CRecyclerViewAdapter adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);

//        statisticLV = (ListView) findViewById(R.id.statisticLV);
//        statisticAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
////        createListView();
//        statisticAdapter.addAll(contentLV);
//        statisticLV.setAdapter(statisticAdapter);
    }


//    private void createListView(){
//        Cursor cursor = sqLiteDatabase.query(Data.getDbStatisticTable(), null, null, null, null,null, null);
//        contentLV = new ArrayList<>();
//        String row = "";
//        if (cursor.moveToFirst()){
//            do {
//                row =
////                cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn())) +
//                cursor.getString(cursor.getColumnIndex(Data.getDbLoginColumn())) + " " +
////                cursor.getString(cursor.getColumnIndex(Data.getDbCountryColumn())) +
//                cursor.getString(cursor.getColumnIndex(Data.getDbStepColumn())) + " " +
//                cursor.getString(cursor.getColumnIndex(Data.getDbScoreColumn())) + " " +
//                cursor.getString(cursor.getColumnIndex(Data.getDbGameTimeColumn())) + " ";
////                cursor.getString(cursor.getColumnIndex(Data.getDbDateColumn()));
//                contentLV.add(row);
//            }while (cursor.moveToNext());
//        }else{
//            Log.d(Data.getLOG_TAG(), "createListView(): 0 rows in cursor.");
//        }
//        cursor.close();
//
//    }

    private ListView statisticLV;
    private ArrayAdapter<String> statisticAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private SqLiteTableManager sqLiteTableManager;
    private ArrayList<String> contentLV;
    private RecyclerView recyclerView;
}
