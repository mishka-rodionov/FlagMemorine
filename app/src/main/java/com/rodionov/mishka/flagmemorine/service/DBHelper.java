package com.rodionov.mishka.flagmemorine.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lab1 on 15.02.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "FM", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
