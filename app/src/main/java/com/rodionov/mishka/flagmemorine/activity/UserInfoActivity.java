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
import android.view.MenuItem;
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
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if (userNameEditText.getText().toString().trim().equals("")){
            countBackPress++;
            Toast.makeText(UserInfoActivity.this, "Please, enter your playername!", Toast.LENGTH_SHORT).show();
            if(countBackPress > 1){
                playername = sqLiteTableManager.getName();
                Log.i(Data.getLOG_TAG(), "onCreate: PLAYERNAME " + playername);
                if (playername != null){
//            userNameEditText.setHint(playername);
                    userNameEditText.setText(playername);
                }else{
                    playername = "Player";
                    userNameEditText.setText(playername);
                    getUsername(playername, country);
                }
            }
        }else {
            Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
            startActivity(startActivityIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_restart:
//                recreate();
//                break;
            case android.R.id.home:
//                removeRoom(Integer.toString(roomIndex));
//                if (userNameEditText.getText().toString().trim().equals("")){
//                    Toast.makeText(UserInfoActivity.this, "Pease, enter your playername!", Toast.LENGTH_SHORT).show();
//                }else {
//                    Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
//                    startActivity(startActivityIntent);
//                }

                onBackPressed();
                break;
        }
        return true;
    }

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

        countBackPress = 0;
        countDoneButton = 0;

        sqLiteTableManager = new SqLiteTableManager(UserInfoActivity.this);
        httpClient = new HttpClient();
        client = new OkHttpClient();

        userNameEditText = (EditText) findViewById(R.id.userName);
//        userInfoApplyButton = (Button) findViewById(R.id.userInfoApply);
        countrySpinner = (Spinner) findViewById(R.id.spinner);
        parentActivityName = getIntent().getStringExtra("activityName");
        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);

        if (parentActivityName.equals("StartActivity")){
            username = sqLiteTableManager.readLastRowFromUserInfo()[1];
            origin = sqLiteTableManager.getOrigin();
            playername = sqLiteTableManager.getName();
        }else{
            username = Data.getVirgin();
            playername = Data.getVirgin();
        }

        if(username != null){

        }else{
            username = Data.getVirgin();
        }

        if (playername != null){
            Log.i(Data.getLOG_TAG(), "onCreate: PLAYERNAME " + playername);
        }else {
            Log.i(Data.getLOG_TAG(), "onCreate: PLAYERNAME NULL " + playername);
            playername = Data.getVirgin();
            Log.i(Data.getLOG_TAG(), "onCreate: PLAYERNAME VIRGIN " + playername);
        }
        if (!playername.equals(Data.getVirgin())){
//            userNameEditText.setHint(playername);
            userNameEditText.setText(playername);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Чтение из таблицы UserInfo логина, записанного в последней строке
                if (userNameEditText.getText().toString().trim().equals("")){
                    countDoneButton++;
                    Toast.makeText(UserInfoActivity.this, "Please, enter your playername!", Toast.LENGTH_SHORT).show();
                    if(countDoneButton > 1){
                        playername = sqLiteTableManager.getName();
                        Log.i(Data.getLOG_TAG(), "onCreate: PLAYERNAME " + playername);
                        if (!playername.equals(null)){
                            userNameEditText.setText(playername);
                        }else{
                            playername = "Player";
                            userNameEditText.setText(playername);
                            getUsername(playername, country);
                        }
                    }
                }else {
                    getUsername(userNameEditText.getText().toString().trim(), country);
                }
            }
        };

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
                country = "Afghanistan";
            }
        };
        countrySpinner.setOnItemSelectedListener(onItemSelectedListener);
        countrySpinner.setAdapter(cSpinnerAdapter);
        if (origin != null){
            int index = 0;
            String[] origins = CountryList.getCountries();
            for (int i = 0; i < origins.length; i++) {
                if (origins[i].equals(origin)){
                    index = i;
                }
            }
            Log.i(Data.getLOG_TAG(), "INDEX OF ORIGIN " + index);
            Log.i(Data.getLOG_TAG(), "ORIGIN " + origin);
            countrySpinner.setSelection(index);
        }
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

        snackbar.setAction("OK", snackbarOnClickListener);

        Log.i(Data.getLOG_TAG(), "getUsername: USERNAME in APPLICATION is = " + username);
        String json = "";
        JSONObject tableRow = new JSONObject();
        try{
            tableRow.put(Data.getPlayername(), playername);
            tableRow.put(Data.getOrigin(), origin);
            tableRow.put(Data.getUsername(), username);
            json = tableRow.toString();
        }catch (JSONException js){
            Log.i(Data.getLOG_TAG(), "postResultToDB: " + js.toString());
        }

        client.newCall(httpClient.getUsername(json)).enqueue(new Callback() {
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
                final String json = response.body().string();
                Log.i(Data.getLOG_TAG(), "onResponse run for USERINFO_ACTIVITY methods: " + json);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            Log.i(Data.getLOG_TAG(), "onResponse from USERINFO_ACTIVITY " + jsonObject.get(Data.getUsername()));
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
                                login = jsonObject.get(Data.getUsername()).toString();
                                sqLiteTableManager.insertIntoUserInfoTable(
                                        userNameEditText.getText().toString(),
                                        login,
                                        country,
                                        Data.getCurrentDate());
                                Intent startActivityIntent = new Intent(UserInfoActivity.this, StartActivity.class);
                                startActivity(startActivityIntent);
                            }
                        }catch (JSONException js){
                            js.printStackTrace();
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
    private String username;
    private String playername;
    private String origin;
    private String parentActivityName;
    private Spinner countrySpinner;
    private HttpClient httpClient;
    private OkHttpClient client;

    private int countBackPress;
    private int countDoneButton;

}
