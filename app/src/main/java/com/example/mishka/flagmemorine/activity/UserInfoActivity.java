package com.example.mishka.flagmemorine.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.service.DBHelper;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        contentValues = new ContentValues();
        sqLiteDatabase = dbHelper.getWritableDatabase();

        login = getIntent().getStringExtra("login");

        userCountryEditText = (EditText) findViewById(R.id.userCountry);
        userNameEditText = (EditText) findViewById(R.id.userName);
        userInfoApplyButton = (Button) findViewById(R.id.userInfoApply);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertIntoUserInfoTable();
            }
        };

        userInfoApplyButton.setOnClickListener(onClickListener);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void insertIntoUserInfoTable(){
        contentValues.put(Data.getDbUserNameColumn(), userNameEditText.getText().toString());
        contentValues.put(Data.getDbLoginColumn(), login);
        contentValues.put(Data.getDbCountryColumn() , userCountryEditText.getText().toString());
        contentValues.put(Data.getDbDateColumn(), "Current date");
        sqLiteDatabase.insert("UserInfo", null, contentValues);
        contentValues.clear();
    }

    private Button userInfoApplyButton;
    private EditText userNameEditText;
    private EditText userCountryEditText;

    private ContentValues contentValues;

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper = new DBHelper(UserInfoActivity.this);

    private String login;

}
