package com.rodionov.mishka.flagmemorine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.cView.CSpinnerAdapter;
import com.rodionov.mishka.flagmemorine.logic.CountryList;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.HttpClient;
import com.rodionov.mishka.flagmemorine.logic.Player;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

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
        acBar.setTitle("");
//        Log.i(Data.getLOG_TAG(), "onCreate: TITLE IS = " + acBar.getTitle());

        sqLiteTableManager = new SqLiteTableManager(UserInfoActivity.this);
        httpClient = new HttpClient();
        client = new OkHttpClient();

        userNameEditText = (EditText) findViewById(R.id.userName);
//        userInfoApplyButton = (Button) findViewById(R.id.userInfoApply);
        countrySpinner = (Spinner) findViewById(R.id.spinner);
        parentActivityName = getIntent().getStringExtra("activityName");
        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);

        String playername = sqLiteTableManager.getName();
        Log.i(Data.getLOG_TAG(), "onCreate: PLAYERNAME " + playername);
        if (!playername.equals(null)){
//            userNameEditText.setHint(playername);
            userNameEditText.setText(playername);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Чтение из таблицы UserInfo логина, записанного в последней строке
                if (userNameEditText.getText().toString().trim().equals("")){
                    Toast.makeText(UserInfoActivity.this, "Pease, enter your playername!", Toast.LENGTH_SHORT).show();
                }else {
                    getUsername(userNameEditText.getText().toString().trim(), country);
                }


//                if (parentActivityName.equals("StartActivity")){
//                    login = sqLiteTableManager.readLastRowFromUserInfo()[1];
//                    getUsername( userNameEditText.getText().toString(), country, login);
//                    sqLiteTableManager.insertIntoUserInfoTable(
//                            userNameEditText.getText().toString(),
//                            login,
//                            country,
//                            Data.getCurrentDate());
//                    Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
//                    startActivity(startActivityIntent);
//                }else {
//                    getUsername( userNameEditText.getText().toString(), country, Data.getVirgin());
//
//                }

            }
        };

//        userInfoApplyButton.setOnClickListener(onClickListener);
        fabDone.setOnClickListener(onClickListener);

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
        menu.findItem(R.id.action_restart).setVisible(false);
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

    public void getUsername(String playername, String origin){
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final Snackbar snackbar = Snackbar.make(getCurrentFocus(), "Server is not available!", Snackbar.LENGTH_INDEFINITE);;
        View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };

        final String username;

        snackbar.setAction("OK", snackbarOnClickListener);
        if (parentActivityName.equals("StartActivity")){
            username = sqLiteTableManager.readLastRowFromUserInfo()[1];
        }else{
            username = Data.getVirgin();
        }

        Log.i(Data.getLOG_TAG(), "getUsername: USERNAME in APPLICATION is = " + username);

        client.newCall(httpClient.getUsername(playername, origin, username)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        snackbar.show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String answer = response.body().string();
//                Log.i(Data.getLOG_TAG(), "onResponse run for USERINFO_ACTIVITY methods: " + answer.split(" ")[1]);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Data.getLOG_TAG(), "onResponse from USERINFO_ACTIVITY " + answer.split(" ")[0]);
                        if (parentActivityName.equals("StartActivity")){
                            login = username;
                            sqLiteTableManager.insertIntoUserInfoTable(
                                    userNameEditText.getText().toString(),
                                    login,
                                    country,
                                    Data.getCurrentDate());
                            Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
                            startActivity(startActivityIntent);
                        }else{
                            login = answer.split(" ")[0];
                            sqLiteTableManager.insertIntoUserInfoTable(
                                    userNameEditText.getText().toString(),
                                    login,
                                    country,
                                    Data.getCurrentDate());
                            Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
                            startActivity(startActivityIntent);
                        }


                    }
                });
            }
        });
    }

    private ArrayAdapter<String> spinnerAdapter;
//    private Button userInfoApplyButton;
    private FloatingActionButton fabDone;
    private CSpinnerAdapter cSpinnerAdapter;
    private EditText userNameEditText;
    private EditText userCountryEditText;
    private String country;
    private android.support.v7.widget.Toolbar userinfoToolbar;
    private SqLiteTableManager sqLiteTableManager;
    private String login;
    private String parentActivityName;
    private Spinner countrySpinner;
    private HttpClient httpClient;
    private OkHttpClient client;

}
