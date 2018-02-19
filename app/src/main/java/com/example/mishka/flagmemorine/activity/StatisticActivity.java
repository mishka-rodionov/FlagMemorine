package com.example.mishka.flagmemorine.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.service.DBHelper;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        DBHelper dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        statisticLV = (ListView) findViewById(R.id.statisticLV);
        statisticAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        createListView();
        statisticAdapter.addAll(contentLV);
        statisticLV.setAdapter(statisticAdapter);
    }

    private void createListView(){
        Cursor cursor = sqLiteDatabase.query(Data.getDbStatisticTable(), null, null, null, null,null, null);
        contentLV = new ArrayList<>();
        String row = "";
        if (cursor.moveToFirst()){
            do {
                row =
//                cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn())) +
                cursor.getString(cursor.getColumnIndex(Data.getDbLoginColumn())) + " " +
//                cursor.getString(cursor.getColumnIndex(Data.getDbCountryColumn())) +
                cursor.getString(cursor.getColumnIndex(Data.getDbStepColumn())) + " " +
                cursor.getString(cursor.getColumnIndex(Data.getDbScoreColumn())) + " " +
                cursor.getString(cursor.getColumnIndex(Data.getDbGameTimeColumn())) + " ";
//                cursor.getString(cursor.getColumnIndex(Data.getDbDateColumn()));
                contentLV.add(row);
            }while (cursor.moveToNext());
        }else{
            Log.d(Data.getLOG_TAG(), "createListView(): 0 rows in cursor.");
        }
        cursor.close();

    }

    private ListView statisticLV;
    private ArrayAdapter<String> statisticAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<String> contentLV;
}
