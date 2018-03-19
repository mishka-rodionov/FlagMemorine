package com.example.mishka.flagmemorine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.service.SqLiteTableManager;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

//        contentValues = new ContentValues();
//        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteTableManager = new SqLiteTableManager(UserInfoActivity.this);

        login = getIntent().getStringExtra("login");

        userCountryEditText = (EditText) findViewById(R.id.userCountry);
        userNameEditText = (EditText) findViewById(R.id.userName);
        userInfoApplyButton = (Button) findViewById(R.id.userInfoApply);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                insertIntoUserInfoTable();
                sqLiteTableManager.insertIntoUserInfoTable(
                        userNameEditText.getText().toString(),
                        login,
                        userCountryEditText.getText().toString(),
                        Data.getCurrentDate());
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

    private Button userInfoApplyButton;
    private EditText userNameEditText;
    private EditText userCountryEditText;

//    private ContentValues contentValues;
//
//    private SQLiteDatabase sqLiteDatabase;
//    private DBHelper dbHelper = new DBHelper(UserInfoActivity.this);
    private SqLiteTableManager sqLiteTableManager;
    private String login;

}
