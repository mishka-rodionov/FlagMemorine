package com.example.mishka.flagmemorine.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.BattleField;
import com.example.mishka.flagmemorine.logic.CountryList;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import eu.davidea.flipview.FlipView;

public class BattleFieldActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //******************************************************************************************
        httpClient = new HttpClient();
        record = getPreferences(MODE_PRIVATE);
        timer = new Timer();
        topRecord = Integer.parseInt(record.getString("REC", "10000"));
        userChoice = new ArrayList<>(2);
        viewTag = new ArrayList<>(2);
        CountryList.loadCountryMap();
        battleFieldSize = Integer.parseInt(getIntent().getStringExtra("size"));
        battleField = new BattleField(battleFieldSize);
        view = getLayoutInflater().inflate(R.layout.layout_xxlarge, null);
        initFlipView(view, battleFieldSize*battleFieldSize);
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
                if (flipViews.get(msg.arg1).isFlipped()) {
                    flipViews.get(msg.arg1).flip(false);
                    Log.d(Data.getLOG_TAG(), "flip = false " + flipViews.get(msg.arg1).isClickable());
                } else {
//                    flipViews.get(msg.arg1).flip(true);
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
//                Log.d(Data.getLOG_TAG(), "timer");
                Message msg = handlerTime.obtainMessage(1, minutes, seconds);
                handlerTime.sendMessage(msg);
            }
        }, 0, 1000);

//        relativeLayout.removeAllViewsInLayout();
        stepCounter = 0;

        //******************************************************************************************

        for (int i = 0; i < battleFieldSize * battleFieldSize; i++) {
            clickable.put(i, true);
        }

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
                final int rowIndex = rowIndexCalc(view.getTag().toString());                    // Вычисление индекса строки.
                final int columnIndex = columnIndexCalc(view.getTag().toString());              // Вычисление индекса столбца.
                    String country  = battleField.getElement(rowIndex, columnIndex);                                        // Получение ответа от AsynkTask
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

        };

        for (int i = 0; i < flipViews.size(); i++) {
            flipViews.get(i).setOnFlippingListener(onFlippingListener);
            Log.d(Data.getLOG_TAG(), "add onFlipListener " + i + " " + flipViews.get(i).getId() + " xxlarge1 = " + R.id.xxlarge1);
        }
        //******************************************************************************************

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initFlipView(View view, int battleFieldSize){
        flipViews = new ArrayList<>(battleFieldSize);
//        if (battleFieldSize == Data.getXsmallSize()){
//            for (int i = 0; i < Data.getXsmallSize(); i++) {
//                flipViews.add((FlipView) view.findViewById(Data.getIdxsmall(i)));
//            }
//        }else if (battleFieldSize == Data.getXxlargeSize()){
//            for (int i = 0; i < Data.getXxlargeSize(); i++) {
//                flipViews.add((FlipView) view.findViewById(Data.getIdxxlarge(i)));
//            }
//        }
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge1));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge2));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge3));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge4));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge5));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge6));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge7));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge8));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge9));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge10));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge11));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge12));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge13));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge14));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge15));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge16));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge17));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge18));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge19));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge20));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge21));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge22));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge23));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge24));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge25));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge26));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge27));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge28));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge29));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge30));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge31));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge32));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge33));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge34));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge35));
        flipViews.add((FlipView) view.findViewById(R.id.xxlarge36));

        Log.d(Data.getLOG_TAG(), "flipview size = " + flipViews.size());
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

    private BattleField battleField;

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
