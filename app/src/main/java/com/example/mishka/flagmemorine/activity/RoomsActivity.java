package com.example.mishka.flagmemorine.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RoomsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        //******************************************************************************************
        userNameET = (EditText) findViewById(R.id.userNameET);
        battleFieldSize4x4 = (RadioButton) findViewById(R.id.radioButton4x4);
        battleFieldSize6x6 = (RadioButton) findViewById(R.id.radioButton6x6);
        roomList = (ListView) findViewById(R.id.roomList);
        roomListAdapter = new ArrayAdapter<String>(RoomsActivity.this, android.R.layout.simple_list_item_1);
        roomName = new HashMap<>();
        httpClient = new HttpClient();
        httpClient.execute("roomListRequest");
        String[] recievingRoomList = {};
        try{
            recievingRoomList = httpClient.get().split(" ");
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        if(recievingRoomList.length > 1) {
            for (int i = 0; i < recievingRoomList.length; i += 2) {
                roomName.put(recievingRoomList[i], recievingRoomList[i + 1]);
            }
        }
        roomListAdapter.addAll(roomName.keySet());
        roomList.setAdapter(roomListAdapter);
        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(Data.getLOG_TAG(), " position = " + position);
                Log.d(Data.getLOG_TAG(), " id = " + id);
                String name = parent.getItemAtPosition(position).toString();
                Log.d(Data.getLOG_TAG(), "room name = " + name);
                httpClient = new HttpClient();
                httpClient.execute("connectToRoom", userNameET.getText().toString(), roomName.get(name));
                String connectToRoom = "";
                try{
                    connectToRoom = httpClient.get();
                }catch (InterruptedException e){
                    e.printStackTrace(System.out);
                }catch (ExecutionException e){
                    e.printStackTrace(System.out);
                }
                Log.d(Data.getLOG_TAG(), "connectToRoom answer = " + connectToRoom);
                Intent intent = new Intent(RoomsActivity.this, RoomBattleFieldActivity.class);
                //ConnectToRoomServlet
                intent.putExtra("roomName", name);
                intent.putExtra("roomIndex", roomName.get(name));
                Log.d(Data.getLOG_TAG(), "room index = " + roomName.get(name).toString());
                intent.putExtra("playerName", userNameET.getText().toString());
                startActivity(intent);
            }
        });
//        for (int i = 0; i < 5; i++) {
//            roomName.add("room" + i);
//        }
//        roomListAdapter.clear();
//        roomListAdapter.addAll(roomName);

        //******************************************************************************************

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //**********************************************************************************
                Log.d(Data.getLOG_TAG(), "onClick fab" + battleFieldSize6x6.getText().toString());
                httpClient = new HttpClient();
                httpClient.execute("createRoom", userNameET.getText().toString(), "6" /*battleFieldSize6x6.getText().toString()*/);
                String roomIndex = "";
                try{
                    roomIndex = httpClient.get();
                }catch (InterruptedException e){
                    e.printStackTrace(System.out);
                }catch (ExecutionException e){
                    e.printStackTrace(System.out);
                }
                Intent intent = new Intent(RoomsActivity.this, RoomBattleFieldActivity.class);
                //ConnectToRoomServlet
                intent.putExtra("roomName", userNameET.getText().toString());
                intent.putExtra("roomIndex", roomIndex);
                intent.putExtra("playerName", userNameET.getText().toString());
                startActivity(intent);
                //**********************************************************************************
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rooms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private EditText userNameET;
    private RadioButton battleFieldSize6x6;
    private RadioButton battleFieldSize4x4;
    private String userName;
    private int battleFieldSize;
    private int battleFieldIndex;
    private ListView roomList;
    private ArrayAdapter<String> roomListAdapter;
    private HashMap<String, String> roomName;
    private HttpClient httpClient;
}
