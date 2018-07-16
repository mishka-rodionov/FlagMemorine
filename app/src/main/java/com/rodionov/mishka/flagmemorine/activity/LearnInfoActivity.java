package com.rodionov.mishka.flagmemorine.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.cView.CRecyclerViewAdapter;
import com.rodionov.mishka.flagmemorine.cView.CRecyclerViewLearnInfoAdapter;
import com.rodionov.mishka.flagmemorine.logic.CountryInfo;
import com.rodionov.mishka.flagmemorine.logic.CountryList;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.Player;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

public class LearnInfoActivity extends AppCompatActivity {

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent startActivityIntent = new Intent(LearnInfoActivity.this, StartActivity.class);
                startActivity(startActivityIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_info);

        hideSystemUI();

        statisticToolbar = (Toolbar) findViewById(R.id.learninfo_toolbar);
        setSupportActionBar(statisticToolbar);
        statisticActionBar = getSupportActionBar();
        statisticActionBar.setDisplayHomeAsUpEnabled(true);
        statisticActionBar.setTitle("");
        // Инициализация менеджера по работе с БД.
        sqLiteTableManager = new SqLiteTableManager(LearnInfoActivity.this);

        CountryList.loadCountryMap();
//        JSONObject jsonObject = new JSONObject(jsonString);

        // Инициализация контейнера плейеров.
        CountryInfo.initCountryInfo();

        // Инициализация структуры отображения данных
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewLearnInfo);
//        recyclerView.setHasFixedSize(true);
        // Менеджер компоновки для данного активити.
        llm = new LinearLayoutManager(LearnInfoActivity.this);
        recyclerView.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),llm.getOrientation());
        dividerItemDecoration.setDrawable(recyclerView.getContext().getResources().getDrawable(R.drawable.learninfo_linedivider, null));
        adapter = new CRecyclerViewLearnInfoAdapter(CountryInfo.getCountries());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

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

    private String jsonString;

    private SqLiteTableManager sqLiteTableManager;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private CRecyclerViewLearnInfoAdapter adapter;
    private Toolbar statisticToolbar;
    private ActionBar statisticActionBar;
}
