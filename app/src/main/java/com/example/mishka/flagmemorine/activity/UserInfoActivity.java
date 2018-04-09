package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.cView.CSpinnerAdapter;
import com.example.mishka.flagmemorine.logic.CountryList;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.service.SqLiteTableManager;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        userinfoToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.userinfo_toolbar);
        setSupportActionBar(userinfoToolbar);
        ActionBar acBar  = getSupportActionBar();
        acBar.setTitle("This is text");
        acBar.setDisplayHomeAsUpEnabled(true);

//        contentValues = new ContentValues();
//        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteTableManager = new SqLiteTableManager(UserInfoActivity.this);

        // Чтение из таблицы UserInfo логина, записанного в последней строке
        login = sqLiteTableManager.readLastRowFromUserInfo()[1];

        userNameEditText = (EditText) findViewById(R.id.userName);
        userInfoApplyButton = (Button) findViewById(R.id.userInfoApply);
        countrySpinner = (Spinner) findViewById(R.id.spinner);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                insertIntoUserInfoTable();
//                sqLiteTableManager.insertIntoUserInfoTable(
//                        userNameEditText.getText().toString(),
//                        login,
////                        userCountryEditText.getText().toString(),
//                        countrySpinner.getSelectedItem().toString(),
//                        Data.getCurrentDate());
                Log.i(Data.getLOG_TAG(), "onClick calc position: " + cSpinnerAdapter.getUsername());
                Log.i(Data.getLOG_TAG(), "onClick calc position: " + cSpinnerAdapter.getPosition());
                Log.i(Data.getLOG_TAG(), "onClick: " + userNameEditText.getText().toString());
//                Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
//                startActivity(startActivityIntent);
            }
        };

        userInfoApplyButton.setOnClickListener(onClickListener);

        CountryList.loadCountryMap();
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CountryList.getCountries());
        cSpinnerAdapter = new CSpinnerAdapter(this, CountryList.getCountries(), CountryList.getCountryResources());
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
        countrySpinner.setOnItemClickListener();
        countrySpinner.setAdapter(cSpinnerAdapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private ArrayAdapter<String> spinnerAdapter;
    private Button userInfoApplyButton;
    private CSpinnerAdapter cSpinnerAdapter;
    private EditText userNameEditText;
    private EditText userCountryEditText;
    private android.support.v7.widget.Toolbar userinfoToolbar;

//    private ContentValues contentValues;
//
//    private SQLiteDatabase sqLiteDatabase;
//    private DBHelper dbHelper = new DBHelper(UserInfoActivity.this);
    private SqLiteTableManager sqLiteTableManager;
    private String login;
    private Spinner countrySpinner;

}
