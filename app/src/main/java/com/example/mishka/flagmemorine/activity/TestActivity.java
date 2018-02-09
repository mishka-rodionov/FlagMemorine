package com.example.mishka.flagmemorine.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import eu.davidea.flipview.FlipView;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //******************************************************************************************
        httpClient = new HttpClient();
        record = getPreferences(MODE_PRIVATE);
        timer = new Timer();
        topRecord = Integer.parseInt(record.getString("REC", "10000"));
//        relativeLayout = (LinearLayout) findViewById(R.id.relativelayoutbattlefield);
        userChoice = new ArrayList<>(2);
        viewTag = new ArrayList<>(2);
        CountryList.loadCountryMap();
        view = getLayoutInflater().inflate(R.layout.layout_4x4, null);
//        initImageButton(view);
        initFlipView(view);
//        for (int i = 0; i < imageButtonArrayList.size(); i++) {
//            imageButtonArrayList.get(i).setBackgroundColor(Color.WHITE);
//        }
        clickable = new HashMap<Integer, Boolean>();
        for (int i = 0; i < battleFieldSize * battleFieldSize; i++) {
            clickable.put(i, true);
        }
        result = (TextView) findViewById(R.id.result);
        test1 = (TextView) findViewById(R.id.test1);
        test2 = (TextView) findViewById(R.id.test2);
        time = (TextView) findViewById(R.id.timeValue);

        if (topRecord == 10000)
            test2.setText("" + 0);
        else
            test2.setText("" + topRecord);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
//                imageButtonArrayList.get(msg.arg1)
//                        .setBackgroundColor(Color.WHITE);
//                imageButtonArrayList.get(msg.arg1)
//                        .setImageResource(msg.arg2);
//                flipViews.get(msg.arg1).setRearImage(msg.arg2);
//                flipViews.get(msg.arg1).flip(true);
//                flipViews.get(msg.arg1).getRearImageView();
                if (flipViews.get(msg.arg1).isFlipped()) {
                    flipViews.get(msg.arg1).flip(false);
                    Log.d(Data.getLOG_TAG(), "flip = false " + flipViews.get(msg.arg1).isClickable());
                } else {
//                    flipViews.get(msg.arg1).flip(true);
                    Log.d(Data.getLOG_TAG(), "flip = true " + flipViews.get(msg.arg1).isClickable());
                }

//                view.setClickable(true);
//                for (int i = 0; i < clickable.size(); i++) {
//                    if(clickable.get(i)){
//                        imageButtonArrayList.get(i).setClickable(true);
//                    }
//                }
            }
        };

        handlerTime = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                time.setText("" + msg.arg1 + ":" + msg.arg2);
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds == 60){
                    minutes++;
                    seconds = 0;
                }
                Message msg = handlerTime.obtainMessage(1, minutes, seconds);
                handlerTime.sendMessage(msg);
            }
        }, 0, 1000);

//        relativeLayout.removeAllViewsInLayout();
        stepCounter = 0;

//        Intent intent = new Intent(MainActivity.this, BattleFieldActivity.class);
//        startActivity(intent);

        // При нажатии на кнопку Play на сервер отправляется get запрос на создание игрового
        // поля размером 6*6. Сервер возвращает индекс хранения текущего игрового поля в
        // контейнере игровых полей.
        httpClient.execute("createBattleField", Integer.toString(battleFieldSize));
        try {
            battleFieldIndex = Integer.parseInt(httpClient.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //******************************************************************************************

        for (int i = 0; i < battleFieldSize * battleFieldSize; i++) {
            clickable.put(i, true);
        }
        Log.d(Data.getLOG_TAG(), "index of container battle field = " + battleFieldIndex);

        for (int i = 0; i < flipViews.size(); i++) {
            flipViews.get(i).setFrontImage(R.drawable.unknown);
            flipViews.get(i).setClickable(true);
        }
//        relativeLayout.addView(view);

//        for (int i = 0; i < imageButtonArrayList.size(); i++) {
//            imageButtonArrayList.get(i).setBackgroundColor(Color.WHITE);
//            imageButtonArrayList.get(i).setImageResource(R.drawable.unknown);
//            imageButtonArrayList.get(i).setClickable(true);
//        }

        // Метод обработки нажатий на кнопки на игровом поле. При нажатии на кнопку происходит
        // отправка данных на сервер (индекс строки и столбца (два целочисленных значения)).
        // По этим данным возвращается запращиваемое значение (название страны).Используя это
        // значение, из контейнера выбирается необходимый ресурс для отображения на кнопке и
        // отображается на ней. Если происходит второй подряд клик на кнопке, то определяется,
        // совпадают ли флаги на этих кнопка. Если флаги совпадают, то кнопки становятся
        // некликабельными, дальше происходит обычная работа. Если флаги несовпадют, то они
        // отображаются секунду, после чего закрываются.
//        View.OnClickListener onClickListener = new View.OnClickListener() {
        FlipView.OnFlippingListener onFlippingListener = new FlipView.OnFlippingListener() {
            @Override
            public void onFlipped(FlipView view, boolean checked) {
                Log.d(Data.getLOG_TAG(), "press button");
                final int rowIndex = rowIndexCalc(view.getTag().toString());                    // Вычисление индекса строки.
                final int columnIndex = columnIndexCalc(view.getTag().toString());              // Вычисление индекса столбца.
                httpClient = new HttpClient();
                httpClient.execute("getElement", Integer.toString(rowIndex), Integer.toString(columnIndex),   // GET-запрос на сервер. Создается новый поток (AsyncTask).
                        Integer.toString(battleFieldIndex));
                try {
                    String country = httpClient.get();                                        // Получение ответа от AsynkTask
                    Log.d(Data.getLOG_TAG(), "Try to get result");
                    result.setText(country);                                                    // Отображение значения в тестовом текстовом поле
                    final int resource = CountryList.getCountry(country);                       // Получение целочисленного значения сооветствующего ресурсу с флагом
                    final int index = Integer.parseInt(view.getTag().toString());               // Вычисление индекса кнопки в контейнере кнопок по тэгу кнопки

                    flipViews.get(index).setRearImage(resource);
                    flipViews.get(index).setClickable(false);
                    userChoice.add(country);                                                    // Добавление выбранного значения в контейнер пользовательского выбора.
                    viewTag.add(view.getTag().toString());                                      // Добавление тега выбранной кнопки в контейнер пользовательского выбора.
                    Log.d(Data.getLOG_TAG(), "userCoice size = " + userChoice.size());
                    Log.d(Data.getLOG_TAG(), "userCoice 1 = " + userChoice.get(0));

                    if (userChoice.size() == 2 && flipFlag) {                                                // Проверка количества элементов в контейнере пользовательского выбора.
                        stepCounter++;
                        flipFlag = false;
                        Log.d(Data.getLOG_TAG(), "flipFlag = " + flipFlag);
                        test1.setText("" + stepCounter);
//                            view.setClickable(false);
                        if (!userChoice.get(0).equals(userChoice.get(1))) {                       // Если значения пользовательского выбора не равны, то
                            userChoice.clear();                                                 // очищаем контейнер
                            final int but0 = Integer.parseInt(viewTag.get(0));                  // вычисляем тег первой нажатой кнопки
                            final int but1 = Integer.parseInt(viewTag.get(1));                  // вычисляем тег второй нажатой кнопки
                            for (int i = 0; i < clickable.size(); i++) {
                                if (clickable.get(i)) {
                                    flipViews.get(i).setClickable(false);
                                }
                            }
                            flipViews.get(but0).setClickable(true);
                            flipViews.get(but1).setClickable(true);

                            flipViews.get(but0).setClickable(true);
                            flipViews.get(but1).setClickable(true);

                            final int paint = R.drawable.unknown;            // вычисляем целочисленное значение файла ресурса с флагом
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
                        } else                                                                   // иначе
                            if (userChoice.get(0).equals(userChoice.get(1))) {                        // Если значения пользовательского выбора равны, то
                                Log.d(Data.getLOG_TAG(), "country equals");
                                flipFlag = true;
                                flipViews.get(Integer.parseInt(viewTag.get(0)))
                                        .setClickable(false);                                       // делаем выбранные кнопки не кликабельными
                                flipViews.get(Integer.parseInt(viewTag.get(1)))
                                        .setClickable(false);

                                flipViews.get(Integer.parseInt(viewTag.get(0))).setClickable(false);
                                flipViews.get(Integer.parseInt(viewTag.get(1))).setClickable(false);

                                clickable.put(Integer.parseInt(viewTag.get(0)), false);
                                clickable.put(Integer.parseInt(viewTag.get(1)), false);
                                userChoice.clear();                                                 // очищаем контейнеры пользовательского выбора
                                viewTag.clear();
                                if (!clickable.containsValue(true)) {
                                    clickable.clear();
                                    userRecord = Integer.parseInt(test1.getText().toString());
                                    Log.d(Data.getLOG_TAG(), "user record = " + userRecord);
                                    if (userRecord < topRecord) {
                                        test2.setText("" + userRecord);
                                        SharedPreferences.Editor editor = record.edit();
                                        editor.putString("REC", test1.getText().toString());
                                        editor.commit();
                                    }
                                }
                            }
                    } else if (userChoice.size() == 2 && !flipFlag) {
                        flipFlag = true;
                        Log.d(Data.getLOG_TAG(), "flipFlag = " + flipFlag);
                        test1.setText("" + stepCounter);
//                            view.setClickable(false);
                        if (!userChoice.get(0).equals(userChoice.get(1))) {                       // Если значения пользовательского выбора не равны, то
                            userChoice.clear();                                                 // очищаем контейнер
                            final int but0 = Integer.parseInt(viewTag.get(0));                  // вычисляем тег первой нажатой кнопки
                            final int but1 = Integer.parseInt(viewTag.get(1));                  // вычисляем тег второй нажатой кнопки
                            for (int i = 0; i < clickable.size(); i++) {
                                if (clickable.get(i)) {
                                    flipViews.get(i).setClickable(true);
                                }
                            }
//                            imageButtonArrayList.get(but0).setClickable(true);
//                            imageButtonArrayList.get(but1).setClickable(true);

                            flipViews.get(but0).setClickable(true);
                            flipViews.get(but1).setClickable(true);

                            final int paint = R.drawable.unknown;            // вычисляем целочисленное значение файла ресурса с флагом
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
                        } else                                                                   // иначе
                            if (userChoice.get(0).equals(userChoice.get(1))) {                        // Если значения пользовательского выбора равны, то
                                Log.d(Data.getLOG_TAG(), "country equals");
                                flipViews.get(Integer.parseInt(viewTag.get(0)))
                                        .setClickable(false);                                       // делаем выбранные кнопки не кликабельными
                                flipViews.get(Integer.parseInt(viewTag.get(1)))
                                        .setClickable(false);

                                flipViews.get(Integer.parseInt(viewTag.get(0))).setClickable(false);
                                flipViews.get(Integer.parseInt(viewTag.get(1))).setClickable(false);

                                clickable.put(Integer.parseInt(viewTag.get(0)), false);
                                clickable.put(Integer.parseInt(viewTag.get(1)), false);
                                userChoice.clear();                                                 // очищаем контейнеры пользовательского выбора
                                viewTag.clear();
                                if (!clickable.containsValue(true)) {
                                    clickable.clear();
                                    userRecord = Integer.parseInt(test1.getText().toString());
                                    Log.d(Data.getLOG_TAG(), "user record = " + userRecord);
                                    if (userRecord < topRecord) {
                                        test2.setText("" + userRecord);
                                        SharedPreferences.Editor editor = record.edit();
                                        editor.putString("REC", test1.getText().toString());
                                        editor.commit();
                                    }
                                }
                            }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        };

//        Button send = (Button) view.findViewById(R.id.buttonSend);
//        send.setOnClickListener(onFlippingListener);
        for (int i = 0; i < battleFieldSize * battleFieldSize; i++) {
//            imageButtonArrayList.get(i).setOnClickListener(onClickListener);
//            flipViews.get(i).setOnClickListener(onClickListener);
            flipViews.get(i).setOnFlippingListener(onFlippingListener);
        }

        //******************************************************************************************

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initFlipView(View view){
        flipViews = new ArrayList<>(battleFieldSize*battleFieldSize);
        flipViews.add((FlipView) findViewById(R.id.image1));
        flipViews.add((FlipView) findViewById(R.id.image2));
        flipViews.add((FlipView) findViewById(R.id.image3));
        flipViews.add((FlipView) findViewById(R.id.image4));
        flipViews.add((FlipView) findViewById(R.id.image5));
        flipViews.add((FlipView) findViewById(R.id.image6));
        flipViews.add((FlipView) findViewById(R.id.image7));
        flipViews.add((FlipView) findViewById(R.id.image8));
        flipViews.add((FlipView) findViewById(R.id.image9));
        flipViews.add((FlipView) findViewById(R.id.image10));
        flipViews.add((FlipView) findViewById(R.id.image11));
        flipViews.add((FlipView) findViewById(R.id.image12));
        flipViews.add((FlipView) findViewById(R.id.image13));
        flipViews.add((FlipView) findViewById(R.id.image14));
        flipViews.add((FlipView) findViewById(R.id.image15));
        flipViews.add((FlipView) findViewById(R.id.image16));
        flipViews.add((FlipView) findViewById(R.id.image17));
        flipViews.add((FlipView) findViewById(R.id.image18));
        flipViews.add((FlipView) findViewById(R.id.image19));
        flipViews.add((FlipView) findViewById(R.id.image20));
        flipViews.add((FlipView) findViewById(R.id.image21));
        flipViews.add((FlipView) findViewById(R.id.image22));
        flipViews.add((FlipView) findViewById(R.id.image23));
        flipViews.add((FlipView) findViewById(R.id.image24));
        flipViews.add((FlipView) findViewById(R.id.image25));
        flipViews.add((FlipView) findViewById(R.id.image26));
        flipViews.add((FlipView) findViewById(R.id.image27));
        flipViews.add((FlipView) findViewById(R.id.image28));
        flipViews.add((FlipView) findViewById(R.id.image29));
        flipViews.add((FlipView) findViewById(R.id.image30));
        flipViews.add((FlipView) findViewById(R.id.image31));
        flipViews.add((FlipView) findViewById(R.id.image32));
        flipViews.add((FlipView) findViewById(R.id.image33));
        flipViews.add((FlipView) findViewById(R.id.image34));
        flipViews.add((FlipView) findViewById(R.id.image35));
        flipViews.add((FlipView) findViewById(R.id.image36));
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
        Log.d(Data.getLOG_TAG(), "input tag = " + viewTag);
        Log.d(Data.getLOG_TAG(), "row index = " + tag/battleFieldSize);
        return tag/battleFieldSize;
    }

    //Вычисление индекса столбца по тэгу нажатой кнопки.
    public int columnIndexCalc(String viewTag){
        int tag = Integer.parseInt(viewTag);
        if (tag < battleFieldSize){
            Log.d(Data.getLOG_TAG(), "input tag = " + viewTag);
            Log.d(Data.getLOG_TAG(), "column index = " + tag);
            return tag;
        }else{
            Log.d(Data.getLOG_TAG(), "input tag = " + viewTag);
            Log.d(Data.getLOG_TAG(), "column index = " + tag%battleFieldSize);
            return tag%battleFieldSize;
        }
    }

    private ArrayList<ImageButton> imageButtonArrayList;
    private ArrayList<FlipView> flipViews;
    private ArrayList<String> userChoice;
    private ArrayList<String> viewTag;

    private HttpClient httpClient;
    private Handler handler;
    private Handler handlerTime;
    private HashMap<Integer, Boolean> clickable;

    private LinearLayout relativeLayout;

    private SharedPreferences record;

    private TextView result;
    private TextView test1;
    private TextView test2;
    private TextView time;
    private Timer timer;
    private View view;

    private int battleFieldSize = 6;
    private int battleFieldIndex = 0;
    private int stepCounter = 0;
    private int userRecord = 0;
    private int topRecord = 0;
    private int seconds = 0;
    private int minutes = 0;
    private boolean flipFlag = true;

}
