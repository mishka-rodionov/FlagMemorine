package com.rodionov.mishka.flagmemorine.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.cView.CRecyclerViewAdapter;
import com.rodionov.mishka.flagmemorine.cView.CRecyclerViewPersonalStatAdapter;
import com.rodionov.mishka.flagmemorine.logic.CountryList;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.HttpClient;
import com.rodionov.mishka.flagmemorine.logic.PersonalStat;
import com.rodionov.mishka.flagmemorine.logic.Player;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class TotalTopActivity extends AppCompatActivity {

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_top);

        hideSystemUI();

        statisticToolbar = (Toolbar) findViewById(R.id.totalStatistic_toolbar);
        setSupportActionBar(statisticToolbar);
        statisticActionBar = getSupportActionBar();
        statisticActionBar.setDisplayHomeAsUpEnabled(true);
        statisticActionBar.setTitle("");
        // Инициализация менеджера по работе с БД.
        sqLiteTableManager = new SqLiteTableManager(TotalTopActivity.this);

        client = new OkHttpClient();
        httpClient = new HttpClient();

        snackbarFlag = true;
        multiplayerFlag = false;

        localName = sqLiteTableManager.getName();
        localOrigin = sqLiteTableManager.getOrigin();

        CountryList.loadCountryMap();
        PersonalStat.initPersonalStatList();
        jsonString = getIntent().getStringExtra("total");
//        JSONObject jsonObject = new JSONObject(jsonString);

        // Инициализация контейнера плейеров.
        Player.initPlayers();

        topTotalLayout = (LinearLayout) findViewById(R.id.topTotalLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewTopTotal);
//        recyclerView.setHasFixedSize(true);
        // Менеджер компоновки для данного активити.
//        llm = new LinearLayoutManager(TotalTopActivity.this);
        llm = new LinearLayoutManager(topTotalLayout.getContext());
        recyclerView.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),llm.getOrientation());
        dividerItemDecoration.setDrawable(recyclerView.getContext().getResources().getDrawable(R.drawable.learninfo_linedivider, null));
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        specification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.multiplayer_total_menu, menu);
        menu.findItem(R.id.action_restart).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.personal_multiplayer_stat:
                topTotalLayout.removeAllViews();
//                llm = new LinearLayoutManager(topTotalLayout.getContext());
//                recyclerView.setLayoutManager(llm);
//                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),llm.getOrientation());
//                dividerItemDecoration.setDrawable(recyclerView.getContext().getResources().getDrawable(R.drawable.learninfo_linedivider, null));
//                recyclerView.addItemDecoration(dividerItemDecoration);
                getPersonalStat(sqLiteTableManager.getLogin());
                break;
            case R.id.all_multiplayer_stat:
                llm = new LinearLayoutManager(topTotalLayout.getContext());
                recyclerView.setLayoutManager(llm);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),llm.getOrientation());
                dividerItemDecoration.setDrawable(recyclerView.getContext().getResources().getDrawable(R.drawable.learninfo_linedivider, null));
                adapter = new CRecyclerViewAdapter(Player.getPlayers());
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(dividerItemDecoration);
                specification();
                topTotalLayout.removeAllViews();
                topTotalLayout.addView(recyclerView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 24.03.18
    // Метод детализации отображения статистических данных. Ограничивает выборку, используя критерий
    // размера поля.
    private void specification() {
        Player.clearPlayers();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            int size = jsonObject.getInt("size");
//            ArrayList<String> pl = sqLiteTableManager.getGroup(size, period);
            String[] row = new String[2];
            for (int i = 1; i < size; i++) {
                JSONArray array = jsonObject.getJSONArray("" + i);
                Player.addPlayer(new Player(new String(array.getString(1).getBytes("UTF-8"), "UTF-8"),
                        array.getString(2),
                        Integer.parseInt(array.getString(3)),0, 0, 0, 0, 0,
                        ""));
                Log.i(Data.getLOG_TAG(), "specification: " + array.getString(1));
            }
        }catch (JSONException jex){
            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + jex.toString());
        }catch (UnsupportedEncodingException uns){
            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + uns.toString());
        }

        Player.sortPlayers();
        adapter = new CRecyclerViewAdapter(Player.getPlayers());
        recyclerView.setAdapter(adapter);
    }

    private void personalStatSpecification(String prsnlJSONstring){
        PersonalStat.clearPersonalStatList();
        try{
            JSONObject jsonObject = new JSONObject(prsnlJSONstring);
            int size = jsonObject.getInt("size");
            for (int i = 1; i < size; i++) {
                JSONArray array = jsonObject.getJSONArray("" + i);
                Log.i(Data.getLOG_TAG(), "personalStatSpecification: array.getString(0) = " + array.getString(0));
                if (array.getString(0).equals("bot")){
                    int index = -1;
                    int sz = PersonalStat.getPersonalStatList().size();
                    if (sz > 0){
                        for (int j = 0; j < sz; j++) {
                            if (PersonalStat.getPersonalStatList().get(j).getEnemyUsername().equals("bot")){
                                index = j;
                                break;
                            }
                        }
                    }
                    if (index != -1){
                        if (Integer.parseInt(array.getString(5)) >= 0){
                            PersonalStat.getPersonalStatList().get(index).addScore();
                            PersonalStat.getPersonalStatList().get(index).addGameCount();
                        }else {
                            PersonalStat.getPersonalStatList().get(index).addEnemyScore();
                            PersonalStat.getPersonalStatList().get(index).addGameCount();
                        }
                    }else{
                        PersonalStat personalStat = new PersonalStat(localName, localOrigin, "bot", "bot", "Botswana");
                        if (Integer.parseInt(array.getString(5)) >= 0){
                            personalStat.addScore();
                            personalStat.addGameCount();
                        }else {
                            personalStat.addEnemyScore();
                            personalStat.addGameCount();
                        }
                        PersonalStat.addPersonalStat(personalStat);
                    }

                }else{
                    int index = -1;
                    int sz = PersonalStat.getPersonalStatList().size();
                    String enmUsrnm = array.getString(0);
                    String enmNm = array.getString(1);
                    String enmOrgn = array.getString(2);
                    if (sz > 0){
                        for (int j = 0; j < sz; j++) {
                            if (PersonalStat.getPersonalStatList().get(j).getEnemyUsername().equals(enmUsrnm)){
                                index = j;
                                break;
                            }
                        }
                    }
                    if (index != -1){
                        if (Integer.parseInt(array.getString(5)) >= 0){
                            PersonalStat.getPersonalStatList().get(index).addScore();
                            PersonalStat.getPersonalStatList().get(index).addGameCount();
                        }else {
                            PersonalStat.getPersonalStatList().get(index).addEnemyScore();
                            PersonalStat.getPersonalStatList().get(index).addGameCount();
                        }
                    }else{
                        PersonalStat personalStat = new PersonalStat(localName, localOrigin, enmUsrnm, enmNm, enmOrgn);
                        if (Integer.parseInt(array.getString(5)) >= 0){
                            personalStat.addScore();
                            personalStat.addGameCount();
                        }else {
                            personalStat.addEnemyScore();
                            personalStat.addGameCount();
                        }
                        PersonalStat.addPersonalStat(personalStat);
                    }
                }
                Log.i(Data.getLOG_TAG(), "personalStatSpecification: SIZE = " + PersonalStat.getPersonalStatList().size());
//                Player.addPlayer(new Player(new String(array.getString(1).getBytes("UTF-8"), "UTF-8"),
//                        array.getString(2),
//                        Integer.parseInt(array.getString(3)),0, 0, 0, 0, 0,
//                        ""));
//                Log.i(Data.getLOG_TAG(), "specification: " + array.getString(1));
            }
        }catch (JSONException jex){
            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + jex.toString());
        }
        personalStatAdapter = new CRecyclerViewPersonalStatAdapter(PersonalStat.getPersonalStatList());
        recyclerView.setAdapter(personalStatAdapter);
        topTotalLayout.addView(recyclerView);
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

    public void getPersonalStat(String un){
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final Snackbar snackbar = Snackbar.make(topTotalLayout, "Server is not available!", Snackbar.LENGTH_INDEFINITE);;
        View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };

        snackbar.setAction("OK", snackbarOnClickListener);
        Log.i(Data.getLOG_TAG(), "getTotalTop: SENDING USERNAME IS ---------> " + un);
        client.newCall(httpClient.getTotalTop(un, Data.getPersonalStat())).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        view.setText(battlefield);
                        Log.i(Data.getLOG_TAG(), "StartActivity run: " + "Fail!!!!!!!!!!!!");
//                        Toast.makeText(TotalTopActivity.this, "Network is not available!", Toast.LENGTH_SHORT).show();
                        multiplayerFlag = false;
                        if (snackbarFlag){
                            snackbar.show();
                            snackbarFlag = false;
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                final String[] answer = response.body().string().split(" ");
                multiplayerFlag = true;
                snackbarFlag = true;
                final String answer = response.body().string();
//                String bug = "";
//                for (int i = 0; i < answer.length; i++) {
//                    bug += " " + answer[i];
//
//                }
//                Log.i(Data.getLOG_TAG(), "onResponse run for TOPTOTAL methods player name: " + bug);
                personalStatJSONString = answer;
                Log.i(Data.getLOG_TAG(), "JSON_STRING " + personalStatJSONString);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        personalStatSpecification(personalStatJSONString);
//                        Intent totalTopIntent = new Intent(StartActivity.this, TotalTopActivity.class);
//                        totalTopIntent.putExtra("total", jsonString);
//                        startActivity(totalTopIntent);
//                        try{
//                            JSONObject jsonObject = new JSONObject(jsonString);
//                            int size = jsonObject.getInt("size");
//                            for (int i = 1; i < size; i++) {
//                                JSONArray array = jsonObject.getJSONArray("" + i);
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 0 " + array.getString(0));
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 1 " + array.getString(1));
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 2 " + array.getString(2));
//                                Log.i(Data.getLOG_TAG(), "JSON ARRAY INDEX 3 " + array.getString(3));
//                            }
//                        }catch(JSONException jex){
//                            Log.i(Data.getLOG_TAG(), "CREATE JSON OBJECT " + jex.toString());
//                        }
                    }
                });
            }
        });
    }

    private String jsonString;
    private String personalStatJSONString;
    private String localName;
    private String localOrigin;

    private HttpClient httpClient;
    private OkHttpClient client;

    private SqLiteTableManager sqLiteTableManager;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private LinearLayout topTotalLayout;
    private CRecyclerViewAdapter adapter;
    private CRecyclerViewPersonalStatAdapter personalStatAdapter;
    private Toolbar statisticToolbar;
    private ActionBar statisticActionBar;
    private Boolean snackbarFlag;
    private Boolean multiplayerFlag;
}
