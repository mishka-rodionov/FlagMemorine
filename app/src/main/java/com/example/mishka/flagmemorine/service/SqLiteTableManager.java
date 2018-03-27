package com.example.mishka.flagmemorine.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mishka.flagmemorine.activity.StartActivity;
import com.example.mishka.flagmemorine.logic.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * Created by Lab1 on 19.03.2018.
 */

public class SqLiteTableManager {

    public SqLiteTableManager(Context c){
        context = c;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void createTableStatistic(){
        sqLiteDatabase.execSQL("create table if not exists " + Data.getDbStatisticTable()
                + " ("
                + "id integer primary key autoincrement,"
                + Data.getDbUserNameColumn() + " text,"
                + Data.getDbLoginColumn() + " text,"
                + Data.getDbCountryColumn() + " text,"
                + Data.getDbBFColumn() + " text,"
                + Data.getDbGameTimeColumn() + " text,"
                + Data.getDbStepColumn() + " text,"
                + Data.getDbScoreColumn() + " text,"
                + Data.getDbDateColumn() + " text"
                + ");");
    }

    public void createTableRecord(){
        sqLiteDatabase.execSQL("create table if not exists " + Data.getDbRecordTable()
                + " ("
                + "id integer primary key autoincrement,"
                + Data.getDbUserNameColumn() + " text,"
                + Data.getDbLoginColumn() + " text,"
                + Data.getDbCountryColumn() + " text,"
                + Data.getDbBFColumn() + " text,"
                + Data.getDbGameTimeColumn() + " text,"
                + Data.getDbStepColumn() + " text,"
                + Data.getDbScoreColumn() + " text,"
                + Data.getDbDateColumn() + " text"
                + ");");
    }

    public void createTableUserInfo(){
        sqLiteDatabase.execSQL("create table if not exists " + Data.getDbUserInfoTable()
                + " ("
                + "id integer primary key autoincrement,"
                + Data.getDbUserNameColumn() + " text,"
                + Data.getDbLoginColumn() + " text,"
                + Data.getDbCountryColumn() + " text,"
                + Data.getDbDateColumn() + " text"
                + ");");
    }

    public void insertIntoUserInfoTable(String name, String login, String country, String date){
        contentValues = new ContentValues();

        cursor = sqLiteDatabase.query(Data.getDbUserInfoTable(), null, null, null, null, null, null);
        if (cursor.moveToLast()){
            this.name = cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn()));
            this.login = cursor.getString(cursor.getColumnIndex(Data.getDbLoginColumn()));
            this.country = cursor.getString(cursor.getColumnIndex(Data.getDbCountryColumn()));
            this.date = cursor.getString(cursor.getColumnIndex(Data.getDbDateColumn()));
        }
        cursor.close();

        if (name == null)
        {
            contentValues.put(Data.getDbUserNameColumn(), this.name);
        }else{
            contentValues.put(Data.getDbUserNameColumn(), name);
        }
        if (login == null){
            contentValues.put(Data.getDbLoginColumn(), this.login);
        }else{
            contentValues.put(Data.getDbLoginColumn(), login);
        }
        if(country == null)
        {
            contentValues.put(Data.getDbCountryColumn(), this.country);
        }else{
            contentValues.put(Data.getDbCountryColumn(), country);
        }
        if (date == null)
        {
            contentValues.put(Data.getDbDateColumn(), this.date);
        }else{
            contentValues.put(Data.getDbDateColumn(), date);
        }

        sqLiteDatabase.insert(Data.getDbUserInfoTable(), null, contentValues);
        contentValues.clear();
    }

    public void insertIntoStatisticTable(String name, String login, String country, String BFsize,
                                         String time, Integer step, Integer score, String date){
        contentValues = new ContentValues();

        cursor = sqLiteDatabase.query(Data.getDbStatisticTable(), null, null, null, null, null, null);
        if (cursor.moveToLast()){
            this.name = cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn()));
            this.login = cursor.getString(cursor.getColumnIndex(Data.getDbLoginColumn()));
            this.country = cursor.getString(cursor.getColumnIndex(Data.getDbCountryColumn()));
            this.BFsize = cursor.getString(cursor.getColumnIndex(Data.getDbBFColumn()));
            this.time = cursor.getString(cursor.getColumnIndex(Data.getDbGameTimeColumn()));
            this.step = cursor.getInt(cursor.getColumnIndex(Data.getDbStepColumn()));
            this.score = cursor.getInt(cursor.getColumnIndex(Data.getDbScoreColumn()));
            this.date = cursor.getString(cursor.getColumnIndex(Data.getDbDateColumn()));
        }
        cursor.close();

        if (name == null)
        {
            contentValues.put(Data.getDbUserNameColumn(), this.name);
        }else{
            contentValues.put(Data.getDbUserNameColumn(), name);
        }
        if (login == null){
            contentValues.put(Data.getDbLoginColumn(), this.login);
        }else{
            contentValues.put(Data.getDbLoginColumn(), login);
        }
        if(country == null)
        {
            contentValues.put(Data.getDbCountryColumn(), this.country);
        }else{
            contentValues.put(Data.getDbCountryColumn(), country);
        }
        if (BFsize == null)
        {
            contentValues.put(Data.getDbBFColumn(), this.BFsize);
        }else{
            contentValues.put(Data.getDbBFColumn(), BFsize);
        }
        if (time == null)
        {
            contentValues.put(Data.getDbGameTimeColumn(), this.time);
        }else{
            contentValues.put(Data.getDbGameTimeColumn(), time);
        }
        if (step == null)
        {
            contentValues.put(Data.getDbStepColumn(), this.step);
        }else{
            contentValues.put(Data.getDbStepColumn(), step);
        }
        if (score == null)
        {
            contentValues.put(Data.getDbScoreColumn(), this.score);
        }else{
            contentValues.put(Data.getDbScoreColumn(), score);
        }
        if (date == null)
        {
            contentValues.put(Data.getDbDateColumn(), this.date);
        }else{
            contentValues.put(Data.getDbDateColumn(), date);
        }

        sqLiteDatabase.insert(Data.getDbStatisticTable(), null, contentValues);
        contentValues.clear();
    }

    public void insertIntoRecordTable(String name, String login, String country, String BFsize,
                                         String time, Integer step, Integer score, String date){
        contentValues = new ContentValues();

        cursor = sqLiteDatabase.query(Data.getDbRecordTable(), null, null, null, null, null, null);
        if (cursor.moveToLast()){
            this.name = cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn()));
            this.login = cursor.getString(cursor.getColumnIndex(Data.getDbLoginColumn()));
            this.country = cursor.getString(cursor.getColumnIndex(Data.getDbCountryColumn()));
            this.BFsize = cursor.getString(cursor.getColumnIndex(Data.getDbBFColumn()));
            this.time = cursor.getString(cursor.getColumnIndex(Data.getDbGameTimeColumn()));
            this.step = cursor.getInt(cursor.getColumnIndex(Data.getDbStepColumn()));
            this.score = cursor.getInt(cursor.getColumnIndex(Data.getDbScoreColumn()));
            this.date = cursor.getString(cursor.getColumnIndex(Data.getDbDateColumn()));
        }
        cursor.close();

        if (name == null)
        {
            contentValues.put(Data.getDbUserNameColumn(), this.name);
        }else{
            contentValues.put(Data.getDbUserNameColumn(), name);
        }
        if (login == null){
            contentValues.put(Data.getDbLoginColumn(), this.login);
        }else{
            contentValues.put(Data.getDbLoginColumn(), login);
        }
        if(country == null)
        {
            contentValues.put(Data.getDbCountryColumn(), this.country);
        }else{
            contentValues.put(Data.getDbCountryColumn(), country);
        }
        if (BFsize == null)
        {
            contentValues.put(Data.getDbBFColumn(), this.BFsize);
        }else{
            contentValues.put(Data.getDbBFColumn(), BFsize);
        }
        if (time == null)
        {
            contentValues.put(Data.getDbGameTimeColumn(), this.time);
        }else{
            contentValues.put(Data.getDbGameTimeColumn(), time);
        }
        if (step == null)
        {
            contentValues.put(Data.getDbStepColumn(), this.step);
        }else{
            contentValues.put(Data.getDbStepColumn(), step);
        }
        if (score == null)
        {
            contentValues.put(Data.getDbScoreColumn(), this.score);
        }else{
            contentValues.put(Data.getDbScoreColumn(), score);
        }
        if (date == null)
        {
            contentValues.put(Data.getDbDateColumn(), this.date);
        }else{
            contentValues.put(Data.getDbDateColumn(), date);
        }

        sqLiteDatabase.insert(Data.getDbRecordTable(), null, contentValues);
        contentValues.clear();
    }

    public String[] readLastRowFromUserInfo(){
        cursor = sqLiteDatabase.query(Data.getDbUserInfoTable(), null, null, null, null, null, null);
        String[] row = new String[4];
        if (cursor.moveToLast()){
            row[0] = cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn()));
            row[1] = cursor.getString(cursor.getColumnIndex(Data.getDbLoginColumn()));
            row[2] = cursor.getString(cursor.getColumnIndex(Data.getDbCountryColumn()));
            row[3] = cursor.getString(cursor.getColumnIndex(Data.getDbDateColumn()));
        }
        cursor.close();
        return row;
    }

    public String getName(){
        cursor = sqLiteDatabase.query(Data.getDbUserInfoTable(), null, null, null, null, null, null);
        String username = "";
        if (cursor.moveToLast()){
            username = cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn()));
        }
        cursor.close();
        return username;
    }

    public String getLogin(){
        cursor = sqLiteDatabase.query(Data.getDbUserInfoTable(), null, null, null, null, null, null);
        String userLogin = "";
        if (cursor.moveToLast()){
            userLogin = cursor.getString(cursor.getColumnIndex(Data.getDbLoginColumn()));
        }
        cursor.close();
        return userLogin;
    }

    public String getCountry(){
        cursor = sqLiteDatabase.query(Data.getDbUserInfoTable(), null, null, null, null, null, null);
        String userCountry = "";
        if (cursor.moveToLast()){
            userCountry = cursor.getString(cursor.getColumnIndex(Data.getDbCountryColumn()));
        }
        cursor.close();
        return userCountry;
    }

    public String getDate(){
        cursor = sqLiteDatabase.query(Data.getDbStatisticTable(), null, null, null, null, null, " id DESC");
        String username = "";
        Date lastDate = new Date(); //Определяем текущую дату и время
        Date fromBd = new Date();
        DateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);
        if (cursor.moveToFirst()){
            do{
                try{
                    fromBd = format.parse(cursor.getString(cursor.getColumnIndex(Data.getDbDateColumn()))); // Берем дату из БД
                }catch (ParseException e){
                    e.printStackTrace();
                }
                if ((lastDate.getTime() - fromBd.getTime()) < Data.getMillisInHour()) //Если их разница меньше часа, то нужно вывести все счета за это время
                {

                }
            }while (cursor.moveToNext());
            username = cursor.getString(cursor.getColumnIndex(Data.getDbUserNameColumn()));
        }
        cursor.close();
        return username;
    }

    public Integer getScore(Integer size){
        cursor = sqLiteDatabase.query(Data.getDbStatisticTable(), null, Data.getDbBFColumn() + " = " + size, null, null, null, null);
        Integer scoreCount = 0;
        if (cursor.moveToFirst()){
            do {
                scoreCount += Integer.parseInt(cursor.getString(cursor.getColumnIndex(Data.getDbScoreColumn())));
            }
            while (cursor.moveToNext());
        }
        Log.i(Data.getLOG_TAG(), "getScore: " + scoreCount);
        return scoreCount;
    }

    public ArrayList<String> getGroup(Integer size, String time){
        ArrayList<String> rows = new ArrayList<>();
        cursor = sqLiteDatabase.query(
                Data.getDbStatisticTable(),
                null,
                Data.getDbBFColumn() + " = " + size,
                null, null, null, null);
        Integer scoreCount = 0;
        if (cursor.moveToFirst()){
            do {
                scoreCount += Integer.parseInt(cursor.getString(cursor.getColumnIndex(Data.getDbScoreColumn())));
                rows.add(cursor.getString(cursor.getColumnIndex(Data.getDbScoreColumn())));
                Log.i(Data.getLOG_TAG(), "rows: " + rows.get(rows.size() - 1));
            }
            while (cursor.moveToNext());
        }
        return rows;
    }

    public void setContext(Context c){
        context = c;
    }

    private ContentValues contentValues;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private DBHelper dbHelper;
    private Cursor cursor;

    private String name;
    private String login;
    private String country;
    private String BFsize;
    private String time;
    private Integer step;
    private Integer score;
    private String date;
}
