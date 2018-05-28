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
        hideSystemUI();

        userinfoToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.userinfo_toolbar);
        setSupportActionBar(userinfoToolbar);
        ActionBar acBar  = getSupportActionBar();
        acBar.setDisplayHomeAsUpEnabled(true);

        sqLiteTableManager = new SqLiteTableManager(UserInfoActivity.this);

        // Чтение из таблицы UserInfo логина, записанного в последней строке
        login = sqLiteTableManager.readLastRowFromUserInfo()[1];

        userNameEditText = (EditText) findViewById(R.id.userName);
        userInfoApplyButton = (Button) findViewById(R.id.userInfoApply);
        countrySpinner = (Spinner) findViewById(R.id.spinner);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteTableManager.insertIntoUserInfoTable(
                        userNameEditText.getText().toString(),
                        login,
                        country,
                        Data.getCurrentDate());
                Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
                startActivity(startActivityIntent);
            }
        };

        userInfoApplyButton.setOnClickListener(onClickListener);

        CountryList.loadCountryMap();
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CountryList.getCountries());
        cSpinnerAdapter = new CSpinnerAdapter(this, CountryList.getCountries(), CountryList.getCountryResources());
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(Data.getLOG_TAG(), "onItemClick: " + cSpinnerAdapter.getCountry(i));
                country = cSpinnerAdapter.getCountry(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i(Data.getLOG_TAG(), "onItemClick: " + "nothing");
                country = "Olympics";
            }
        };
        countrySpinner.setOnItemSelectedListener(onItemSelectedListener);
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

    private ArrayAdapter<String> spinnerAdapter;
    private Button userInfoApplyButton;
    private CSpinnerAdapter cSpinnerAdapter;
    private EditText userNameEditText;
    private EditText userCountryEditText;
    private String country;
    private android.support.v7.widget.Toolbar userinfoToolbar;
    private SqLiteTableManager sqLiteTableManager;
    private String login;
    private Spinner countrySpinner;

}
