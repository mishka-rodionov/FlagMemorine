package com.example.mishka.flagmemorine.activity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.BattleField;
import com.example.mishka.flagmemorine.logic.CountryList;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.service.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import eu.davidea.flipview.FlipView;

public class BattleFieldActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        time1 = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //******************************************************************************************
        DBHelper dbHelper = new DBHelper(BattleFieldActivity.this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();
        record = getPreferences(MODE_PRIVATE);
        timer = new Timer();
        topRecord = Integer.parseInt(record.getString("REC", "10000"));
        userChoice = new ArrayList<>(2);
        viewTag = new ArrayList<>(2);
        CountryList.loadCountryMap();
        basicLayout = (LinearLayout) findViewById(R.id.basicLayout);
        battleFieldSize = Integer.parseInt(getIntent().getStringExtra("size"));
        score = battleFieldSize * 2;
        battleField = new BattleField(battleFieldSize);
        getView(battleFieldSize);
        initFlipView(view, battleFieldSize);
        clickable = new HashMap<Integer, Boolean>();

        for (int i = 0; i < battleFieldSize; i++) {
            clickable.put(i, true);
        }

        result =    (TextView)  findViewById(R.id.result);
        test1 =     (TextView)  findViewById(R.id.currentStepCount);
        test2 =     (TextView)  findViewById(R.id.recordStepCount);
        time =      (TextView)  findViewById(R.id.timeValue);
        restart =   (Button)    findViewById(R.id.restart);
        scoreTV =   (TextView)  findViewById(R.id.currentScore);

        View.OnClickListener restartListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        };

        restart.setOnClickListener(restartListener);
        scoreTV.setText(Integer.toString(score));
        if (topRecord == 10000)
            test2.setText("" + 0);
        else
            test2.setText("" + topRecord);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (flipViews.get(msg.arg1).isFlipped()) {
                    flipViews.get(msg.arg1).flip(false);
                    Log.d(Data.getLOG_TAG(), "flip = false " + flipViews.get(msg.arg1).isClickable());
                } else {
                    Log.d(Data.getLOG_TAG(), "flip = true " + flipViews.get(msg.arg1).isClickable());
                }
            }
        };

        handlerTime = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg2 < 10)
                    time.setText("" + msg.arg1 + " : 0" + msg.arg2);
                else
                    time.setText("" + msg.arg1 + " : " + msg.arg2);
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

        stepCounter = 0;
        basicLayout.addView(view);

        //******************************************************************************************
        for (int i = 0; i < flipViews.size(); i++) {
            flipViews.get(i).setFrontImage(R.drawable.unknown);
            flipViews.get(i).setClickable(true);
        }

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
                new Thread(){
                    public void run(){
                        mp = MediaPlayer.create(BattleFieldActivity.this, R.raw.flip_click);
                        mp.start();
                    }
                }.start();
                final int rowIndex = rowIndexCalc(view.getTag().toString());                    // Вычисление индекса строки.
                final int columnIndex = columnIndexCalc(view.getTag().toString());              // Вычисление индекса столбца.
                    String country  = battleField.getElement(Integer.parseInt(view.getTag().toString()));                                        // Получение ответа от AsynkTask
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

                clickHandler();
            }
        };

        for (int i = 0; i < flipViews.size(); i++) {
            flipViews.get(i).setOnFlippingListener(onFlippingListener);
//            Log.d(Data.getLOG_TAG(), "add onFlipListener " + i + " " + flipViews.get(i).getId() + " xxlarge1 = " + R.id.xxlarge1);
        }
        time2 = System.currentTimeMillis();
        Log.d(Data.getLOG_TAG(), "loading time is = " + (time2 - time1));
        //******************************************************************************************
    }

    private void clickHandler() {
        if (userChoice.size() == 2 && flipFlag) {                                                // Проверка количества элементов в контейнере пользовательского выбора.
            stepCounter++;
            flipFlag = false;
            Log.d(Data.getLOG_TAG(), "flipFlag = " + flipFlag);
            test1.setText("" + stepCounter);
//                            view.setClickable(false);
            if (!userChoice.get(0).equals(userChoice.get(1))) {                       // Если значения пользовательского выбора не равны, то
                userChoice.clear();                                                 // очищаем контейнер
                score--;
                scoreTV.setText(Integer.toString(score));
                final int but0 = Integer.parseInt(viewTag.get(0));                  // вычисляем тег первой нажатой кнопки
                final int but1 = Integer.parseInt(viewTag.get(1));                  // вычисляем тег второй нажатой кнопки
                for (int i = 0; i < clickable.size(); i++) {
                    if (clickable.get(i)) {
                        flipViews.get(i).setClickable(false);
                    }
                }
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
                        Log.d(Data.getLOG_TAG(), "All flags is plipped");
                        timer.cancel();
                        pushToDB();
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
            if (!userChoice.get(0).equals(userChoice.get(1))) {                     // Если значения пользовательского выбора не равны, то
                userChoice.clear();                                                 // очищаем контейнер
                final int but0 = Integer.parseInt(viewTag.get(0));                  // вычисляем тег первой нажатой кнопки
                final int but1 = Integer.parseInt(viewTag.get(1));                  // вычисляем тег второй нажатой кнопки
                for (int i = 0; i < clickable.size(); i++) {
                    if (clickable.get(i)) {
                        flipViews.get(i).setClickable(true);
                    }
                }
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
                        Log.d(Data.getLOG_TAG(), "All flags is flipped");
                        timer.cancel();
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
    }

    private void pushToDB() {
        Log.d(Data.getLOG_TAG(), " params = " + Data.getDbUserNameColumn()+ " " + Data.getUserName());
        contentValues.put(Data.getDbUserNameColumn(), Data.getUserName());
        contentValues.put(Data.getDbLoginColumn(), Data.getLogin());
        contentValues.put(Data.getDbCountryColumn(), Data.getUserCountry());
        contentValues.put(Data.getDbBFColumn(), BF);
        Log.d(Data.getLOG_TAG(), " params = " + Data.getDbBFColumn()+ " " + BF);
        Log.d(Data.getLOG_TAG(), " params = " + Data.getDbDateColumn()+ " " + Data.getTime());
        contentValues.put(Data.getDbDateColumn(), Data.getTime());
        contentValues.put(Data.getDbStepColumn(), userRecord);
        contentValues.put(Data.getDbScoreColumn(), score);
        sqLiteDatabase.insert(Data.getDbStatisticTable(), null, contentValues);
    }

    public void initFlipView(View view, int battleFieldSize){
        flipViews = new ArrayList<>(battleFieldSize);
        if (battleFieldSize == Data.getXsmallSize()){
            for (int i = 0; i < Data.getXsmallSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxsmall(i)));
            }
        }else if (battleFieldSize == Data.getSmallSize()){
            for (int i = 0; i < Data.getSmallSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdsmall(i)));
            }
        }else if (battleFieldSize == Data.getMediumSize()){
            for (int i = 0; i < Data.getMediumSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdmedium(i)));
            }
        }else if (battleFieldSize == Data.getLargeSize()){
            for (int i = 0; i < Data.getLargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdlarge(i)));
            }
        }else if (battleFieldSize == Data.getXlargeSize()){
            for (int i = 0; i < Data.getXlargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxlarge(i)));
            }
        }else if (battleFieldSize == Data.getXxlargeSize()){
            for (int i = 0; i < Data.getXxlargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxxlarge(i)));
            }
        }
        Log.d(Data.getLOG_TAG(), "flipview size = " + flipViews.size());
    }

    //Вычисление индекса столбца по тэгу нажатой кнопки.
    public int rowIndexCalc(String viewTag){
        int tag = Integer.parseInt(viewTag);
        Log.d(Data.getLOG_TAG(), "input tag = " + viewTag);
        Log.d(Data.getLOG_TAG(), "row index = " + tag/battleFieldSize);
        return tag/battleFieldSize;
    }

    public void getView(int size){
        if(size == Data.getXsmallSize()){
            view = getLayoutInflater().inflate(R.layout.layout_xsmall, null);
        }else if (size == Data.getSmallSize()){
            view = getLayoutInflater().inflate(R.layout.layout_small, null);
        }else if (size == Data.getMediumSize()){
            view = getLayoutInflater().inflate(R.layout.layout_medium, null);
        }else if (size == Data.getLargeSize()){
            view = getLayoutInflater().inflate(R.layout.layout_large, null);
        }else if (size == Data.getXlargeSize()){
            view = getLayoutInflater().inflate(R.layout.layout_xlarge, null);
        }else if (size == Data.getXxlargeSize()){
            view = getLayoutInflater().inflate(R.layout.layout_xxlarge, null);
        }
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

    //region Private fields
    private ArrayList<ImageButton> imageButtonArrayList;
    private ArrayList<FlipView> flipViews;
    private ArrayList<String> userChoice;
    private ArrayList<String> viewTag;

    private BattleField battleField;

    private ContentValues contentValues;

    private Handler handler;
    private Handler handlerTime;
    private HashMap<Integer, Boolean> clickable;

    private LinearLayout basicLayout;

    private MediaPlayer mp;

    private SharedPreferences record;

    private SQLiteDatabase sqLiteDatabase;

    private Button restart;
    private TextView result;
    private TextView test1;
    private TextView test2;
    private TextView time;
    private TextView scoreTV;
    private Timer timer;
    private View view;

    private String BF;
    private int battleFieldSize = 6;
    private int battleFieldIndex = 0;
    private int minutes = 0;
    private int stepCounter = 0;
    private int userRecord = 0;
    private int topRecord = 0;
    private int seconds = 0;
    private int score =  Data.getXxlargeSize() * 2;
    private long time1 = 0;
    private long time2 = 0;
    private boolean flipFlag = true;
    //endregion

}
