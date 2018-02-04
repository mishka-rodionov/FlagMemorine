package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.CountryList;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class RoomBattleFieldActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_field);

        //******************************************************************************************
        Intent intent = getIntent();
        timer = new Timer();
        roomName = intent.getStringExtra("roomName");
        roomIndex = Integer.parseInt(intent.getStringExtra("roomIndex"));
        playerName = intent.getStringExtra("playerName");
        httpClient = new HttpClient();
        record = getPreferences(MODE_PRIVATE);
        topRecord = Integer.parseInt(record.getString("REC", "10000"));
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayoutbattlefield);
        userChoice = new ArrayList<>(2);
        viewTag = new ArrayList<>(2);
        CountryList.loadCountryMap();
        view  = getLayoutInflater().inflate(R.layout.layout_6_6, null);
        initImageButton(view);
        for (int i = 0; i < imageButtonArrayList.size(); i++) {
            imageButtonArrayList.get(i).setBackgroundColor(Color.WHITE);
        }
        clickable = new HashMap<Integer, Boolean>();
        for (int i = 0; i < battleFieldSize*battleFieldSize; i++) {
            clickable.put(i, true);
        }
        result = (TextView) view.findViewById(R.id.result);
        test1 = (TextView) view.findViewById(R.id.test1);
        test2 = (TextView) view.findViewById(R.id.test2);
        if(topRecord == 10000)
            test2.setText("" + 0);
        else
            test2.setText("" + topRecord);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                imageButtonArrayList.get(msg.arg1)
                        .setBackgroundColor(Color.WHITE);
                imageButtonArrayList.get(msg.arg1)
                        .setImageResource(msg.arg2);
//                view.setClickable(true);
//                for (int i = 0; i < clickable.size(); i++) {
//                    if(clickable.get(i)){
//                        imageButtonArrayList.get(i).setClickable(true);
//                    }
//                }
            }
        };

        relativeLayout.removeAllViewsInLayout();
        stepCounter = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                httpClient = new HttpClient();
                httpClient.execute("testAnotherPlayerChoice", Integer.toString(roomIndex), playerName);
                String[] temp;
                try{
                    temp = httpClient.get().split(" ");
                    Log.d(Data.getLOG_TAG(), " country is before if=" + temp[3] + "abc " + temp.length);
                    if (!temp[3].equals("dummy")) {
                        Log.d(Data.getLOG_TAG(), " country is = " + temp[3]);
                        int tag = Integer.parseInt(temp[1]) * 6 + Integer.parseInt(temp[2]);
                        int resource = CountryList.getCountry(temp[3]);
                        Message msg;
                        msg = handler.obtainMessage(1, tag, resource);
                        handler.sendMessage(msg);
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }catch (ArrayIndexOutOfBoundsException ar){
                    ar.printStackTrace(System.out);
                }
            }
        }, 0, 1000);

        //******************************************************************************************

        for (int i = 0; i < battleFieldSize*battleFieldSize; i++) {
            clickable.put(i, true);
        }
        Log.d(LOG_TAG, "index of container battle field = " + roomIndex);
        relativeLayout.addView(view);
        for (int i = 0; i < imageButtonArrayList.size(); i++) {
            imageButtonArrayList.get(i).setBackgroundColor(Color.WHITE);
            imageButtonArrayList.get(i).setImageResource(R.drawable.ic_help_outline_black_36dp);
            imageButtonArrayList.get(i).setClickable(true);
        }
        // Метод обработки нажатий на кнопки на игровом поле. При нажатии на кнопку происходит
        // отправка данных на сервер (индекс строки и столбца (два целочисленных значения)).
        // По этим данным возвращается запращиваемое значение (название страны).Используя это
        // значение, из контейнера выбирается необходимый ресурс для отображения на кнопке и
        // отображается на ней. Если происходит второй подряд клик на кнопке, то определяется,
        // совпадают ли флаги на этих кнопка. Если флаги совпадают, то кнопки становятся
        // некликабельными, дальше происходит обычная работа. Если флаги несовпадют, то они
        // отображаются секунду, после чего закрываются.
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "press button");
                final int rowIndex = rowIndexCalc(view.getTag().toString());                    // Вычисление индекса строки.
                final int columnIndex = columnIndexCalc(view.getTag().toString());              // Вычисление индекса столбца.
                httpClient = new HttpClient();
                httpClient.execute("getElementRoom", Integer.toString(rowIndex), Integer.toString(columnIndex),   // GET-запрос на сервер. Создается новый поток (AsyncTask).
                        Integer.toString(roomIndex), playerName);
                try{
//                    String country  = countryName.get();                                        // Получение ответа от AsynkTask
                    String country  = httpClient.get();                                        // Получение ответа от AsynkTask
                    Log.d(LOG_TAG, "Try to get result");
                    result.setText(country);                                                    // Отображение значения в тестовом текстовом поле
                    final int resource = CountryList.getCountry(country);                       // Получение целочисленного значения сооветствующего ресурсу с флагом
                    final int index = Integer.parseInt(view.getTag().toString());               // Вычисление индекса кнопки в контейнере кнопок по тэгу кнопки
                    Thread t = new Thread(new Runnable() {                                      // Создание нового потока для отображения флага на кнопке.
                        Message msg;
                        @Override
                        public void run() {
                            msg = handler.obtainMessage(1, index, resource);                    // Формирование сообщения для хэндлера.
                            handler.sendMessage(msg);                                           // Отправка сообщения хэндлеру.
                        }
                    });
                    t.start();
                    userChoice.add(country);                                                    // Добавление выбранного значения в контейнер пользовательского выбора.
                    viewTag.add(view.getTag().toString());                                      // Добавление тега выбранной кнопки в контейнер пользовательского выбора.
                    imageButtonArrayList.get(Integer.parseInt(view.getTag().toString()))
                            .setClickable(false);
                    Log.d(LOG_TAG, "userCoice size = " + userChoice.size());
                    Log.d(LOG_TAG, "userCoice 1 = " + userChoice.get(0));

                    if(userChoice.size() == 2 ){                                                // Проверка количества элементов в контейнере пользовательского выбора.
                        stepCounter++;
                        test1.setText("" + stepCounter);
//                            view.setClickable(false);
                        if(!userChoice.get(0).equals(userChoice.get(1))){                       // Если значения пользовательского выбора не равны, то
                            userChoice.clear();                                                 // очищаем контейнер
                            final int but0 = Integer.parseInt(viewTag.get(0));                  // вычисляем тег первой нажатой кнопки
                            final int but1 = Integer.parseInt(viewTag.get(1));                  // вычисляем тег второй нажатой кнопки
//                                for (int i = 0; i < clickable.size(); i++) {
//                                    if(clickable.get(i)){
//                                        imageButtonArrayList.get(i).setClickable(false);
//                                    }
//                                }
                            imageButtonArrayList.get(but0).setClickable(true);
                            imageButtonArrayList.get(but1).setClickable(true);
                            final int paint = R.drawable.ic_help_outline_black_36dp;            // вычисляем целочисленное значение файла ресурса с флагом
                            Thread t1 = new Thread(new Runnable() {                             // создаем новый поток для закрытия первого, из выбранных пользователем флагов, рубашкой
                                Message msg;
                                @Override
                                public void run() {
                                    msg = handler.obtainMessage(1, but0, paint);                // подготавливаем сообщение
                                    handler.sendMessageDelayed(msg, 1000);                      // помещаем в очередь хэндлера отложенное на секунду сообщение
                                }
                            });
                            t1.start();
                            Thread t2 = new Thread(new Runnable() {                             // то же самое делаем для второй кнопки
                                Message msg;
                                @Override
                                public void run() {
                                    msg = handler.obtainMessage(1, but1, paint);
                                    handler.sendMessageDelayed(msg, 1000);
                                }
                            });
                            t2.start();
                            viewTag.clear();                                                    // очищаем контейнер тегов
                        }else                                                                   // иначе
                            if(userChoice.get(0).equals(userChoice.get(1))){                        // Если значения пользовательского выбора равны, то
                                Log.d(LOG_TAG, "country equals");
                                imageButtonArrayList.get(Integer.parseInt(viewTag.get(0)))
                                        .setClickable(false);                                       // делаем выбранные кнопки не кликабельными
                                imageButtonArrayList.get(Integer.parseInt(viewTag.get(1)))
                                        .setClickable(false);
                                clickable.put(Integer.parseInt(viewTag.get(0)), false);
                                clickable.put(Integer.parseInt(viewTag.get(1)), false);
                                userChoice.clear();                                                 // очищаем контейнеры пользовательского выбора
                                viewTag.clear();
                                if(!clickable.containsValue(true)){
                                    clickable.clear();
                                    userRecord = Integer.parseInt(test1.getText().toString());
                                    Log.d(LOG_TAG, "user record = " + userRecord);
                                    if(userRecord < topRecord){
                                        test2.setText("" + userRecord);
                                        SharedPreferences.Editor editor = record.edit();
                                        editor.putString("REC", test1.getText().toString());
                                        editor.commit();
                                    }
                                }
                            }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
            }

        };

        Button send = (Button) view.findViewById(R.id.buttonSend);
        send.setOnClickListener(onClickListener);
        for (int i = 0; i < battleFieldSize*battleFieldSize; i++) {
            imageButtonArrayList.get(i).setOnClickListener(onClickListener);
        }

        //******************************************************************************************

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        getMenuInflater().inflate(R.menu.battle_field, menu);
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

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initImageButton(View view){
        imageButtonArrayList = new ArrayList<>(battleFieldSize*battleFieldSize);
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton2));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton3));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton4));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton5));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton6));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton7));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton8));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton9));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton10));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton11));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton12));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton13));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton14));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton15));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton16));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton17));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton18));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton19));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton20));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton21));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton22));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton23));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton24));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton25));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton26));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton27));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton28));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton29));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton30));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton31));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton32));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton33));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton34));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton35));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton36));
        imageButtonArrayList.add((ImageButton) view.findViewById(R.id.imageButton37));
    }

    //Вычисление индекса столбца по тэгу нажатой кнопки.
    public int rowIndexCalc(String viewTag){
        int tag = Integer.parseInt(viewTag);
        Log.d(LOG_TAG, "input tag = " + viewTag);
        Log.d(LOG_TAG, "row index = " + tag/battleFieldSize);
        return tag/battleFieldSize;
    }

    //Вычисление индекса столбца по тэгу нажатой кнопки.
    public int columnIndexCalc(String viewTag){
        int tag = Integer.parseInt(viewTag);
        if (tag < battleFieldSize){
            Log.d(LOG_TAG, "input tag = " + viewTag);
            Log.d(LOG_TAG, "column index = " + tag);
            return tag;
        }else{
            Log.d(LOG_TAG, "input tag = " + viewTag);
            Log.d(LOG_TAG, "column index = " + tag%battleFieldSize);
            return tag%battleFieldSize;
        }
    }

    private RelativeLayout relativeLayout;
    private String LOG_TAG = "flagmemorine";
    private ArrayList<ImageButton> imageButtonArrayList;
    private int battleFieldSize = 6;
    private int roomIndex = 0;
    private ArrayList<String> userChoice;
    private ArrayList<String> viewTag;
    private Handler handler;
    private View view;
    private TextView result;
    private TextView test1;
    private TextView test2;
    private HashMap<Integer, Boolean> clickable;
    private int stepCounter = 0;
    private int userRecord = 0;
    private int topRecord = 0;
    private SharedPreferences record;
    private HttpClient httpClient;
    private String roomName;
    private String playerName;
    private Timer timer;
}
