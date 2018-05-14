//package com.example.mishka.flagmemorine.activity;
//
//import android.annotation.TargetApi;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.util.Log;
//import android.view.View;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.RadioButton;
//
//import com.example.mishka.flagmemorine.R;
//import com.example.mishka.flagmemorine.logic.Data;
//import com.example.mishka.flagmemorine.logic.HttpClient;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.concurrent.ExecutionException;
//
//import okhttp3.Headers;
//import okhttp3.HttpUrl;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class RoomsActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rooms);
//
//        //******************************************************************************************
//        userNameET = (EditText) findViewById(R.id.userNameET);
//        battleFieldSize4x4 = (RadioButton) findViewById(R.id.radioButton4x4);
//        battleFieldSize6x6 = (RadioButton) findViewById(R.id.radioButton6x6);
//        roomList = (ListView) findViewById(R.id.roomList);
//        roomListAdapter = new ArrayAdapter<String>(RoomsActivity.this, android.R.layout.simple_list_item_1);
//        roomName = new HashMap<>();
//        httpClient = new HttpClient();
//        // Запрос на сервер для составления списка комнат. Возвращаемый ответ - это строка, в
//        // которой через пробел перечислены все существующие на сервере комнаты и их индексы в
//        // контейнере комнат. Строка начинается с имени комнаты. Например: "room 1 newRoom 2"
//        httpClient.execute("roomListRequest");
//        String[] recievingRoomList = {};
//        try{
//        // Получение ответа от сервер и его размещение в массиве строк.
//            recievingRoomList = httpClient.get().split(" ");
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }catch (ExecutionException e){
//            e.printStackTrace();
//        }
//        // Заполение мэпа для хранения комнат и их индексов в контейнере комнат
//        if(recievingRoomList.length > 1) {
//            for (int i = 0; i < recievingRoomList.length; i += 2) {
//                roomName.put(recievingRoomList[i], recievingRoomList[i + 1]);
//            }
//        }
//        // Формирование адаптера для списка комнат на основе набора ключей из мэпа.
//        roomListAdapter.addAll(roomName.keySet());
//        roomList.setAdapter(roomListAdapter);
//        // Обработчик нажатия на комнату из списка. При нажатии отправляется запрос серверу на
//        // подключение к выбранной комнате. В запросе отправляется имя игрока (должно быть введено
//        // заранее в поле ввода) и индекс комнаты. В ответе сервера приходит имя соперника и индекс
//        // комнаты.
//        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String name = parent.getItemAtPosition(position).toString();
//                httpClient = new HttpClient();
//                //Запрос на подключение.
//                httpClient.execute("connectToRoom", userNameET.getText().toString(), roomName.get(name));
//                String connectToRoom = "";
//                try{
//                    // Получение ответа сервера.
//                    connectToRoom = httpClient.get();
//                }catch (InterruptedException e){
//                    e.printStackTrace(System.out);
//                }catch (ExecutionException e){
//                    e.printStackTrace(System.out);
//                }
//                Log.d(Data.getLOG_TAG(), "connectToRoom answer = " + connectToRoom);
//                // Переход на активити с игровым полем комнаты. В интенте на новое активити
//                // передается имя комнаты, индекс комнаты и имя игрока.
//                Intent intent = new Intent(RoomsActivity.this, RoomBattleFieldActivity.class);
//                intent.putExtra("roomName", name);
//                intent.putExtra("roomIndex", roomName.get(name));
//                intent.putExtra("playerName", userNameET.getText().toString());
//                startActivity(intent);
//            }
//        });
//        //******************************************************************************************
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // При нажатии на данную кнопку происходит создание новой комнаты для игры с соперником.
//        // Перед нажатием на эту кнопку должно быть заполнено поле ввода имени игрока. После нажатия
//        // игрок перенаправляется на активити с игровым полем комнаты.
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //**********************************************************************************
//                Log.d(Data.getLOG_TAG(), "onClick fab" + battleFieldSize6x6.getText().toString());
//                httpClient = new HttpClient();
//                // Запрос на создание комнаты. В параметрах запроса передается имя игрока и размер
//                // игрового поля. Имя комнаты соответствует имени игрока. Размер игрового поля
//                // определяется как квадрат передаваемого значения.
//                httpClient.execute("createRoom", userNameET.getText().toString(), "6" /*battleFieldSize6x6.getText().toString()*/);
//                String roomIndex = "";
//                try{
//                // В ответе на запрос возвращается индекс комнаты в контейнере комнат
//                    roomIndex = httpClient.get();
//                }catch (InterruptedException e){
//                    e.printStackTrace(System.out);
//                }catch (ExecutionException e){
//                    e.printStackTrace(System.out);
//                }
//                // Переход на активити с игровым полем для игры с соперником в комнате. В интенте
//                // передается имя комнаты, индекс комнаты в контейнере комнат и имя игрока.
//                Intent intent = new Intent(RoomsActivity.this, RoomBattleFieldActivity.class);
//                intent.putExtra("roomName", userNameET.getText().toString());
//                intent.putExtra("roomIndex", roomIndex);
//                intent.putExtra("playerName", userNameET.getText().toString());
//                startActivity(intent);
//                //**********************************************************************************
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.rooms, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle sending bar item clicks here. The sending bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    private EditText userNameET;                    // Поле ввода для имени игрока
//    private RadioButton battleFieldSize6x6;
//    private RadioButton battleFieldSize4x4;
//    private String userName;
//    private int battleFieldSize;
//    private int battleFieldIndex;
//    private ListView roomList;                      // Список комнат
//    private ArrayAdapter<String> roomListAdapter;   // Адаптер для списка комнат
//    private HashMap<String, String> roomName;       // Мэп для хранения имен комнат и их индексов в контейнере комнат на сервере
//    private HttpClient httpClient;                  // Клиент для работы с сервером
//}
