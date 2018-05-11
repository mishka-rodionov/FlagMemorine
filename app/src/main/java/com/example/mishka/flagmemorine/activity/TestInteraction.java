package com.example.mishka.flagmemorine.activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;
import com.example.mishka.flagmemorine.logic.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class TestInteraction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_interaction);
        receiveText = (TextView) findViewById(R.id.receiveText);
        sendText = (EditText) findViewById(R.id.sendText);
        receive = (Button) findViewById(R.id.receive);
        send = (Button) findViewById(R.id.send);

        client = new OkHttpClient();
        httpClient = new HttpClient();

        requestTimer = new Timer();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.send:
                        send(httpClient, sendText.getText().toString());
                        break;
                    case R.id.receive:
                        receive(httpClient);
                        break;
                }
            }
        };

        requestTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                receive(httpClient);
            }
        }, 5000, 1000);

        receive.setOnClickListener(onClickListener);
        send.setOnClickListener(onClickListener);
    }

    public void send(HttpClient httpClient, String text){
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        client.newCall(httpClient.send(text)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Data.getLOG_TAG(), "run: " + "Fail!!!!!!!!!!!!");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String answer = response.body().string();
                Log.i(Data.getLOG_TAG(), "onResponse run: " + answer);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Data.getLOG_TAG(), "run: " + answer);
                    }
                });
            }
        });
    }

    public void receive(HttpClient httpClient){
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        client.newCall(httpClient.receive()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                ;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(Data.getLOG_TAG(), "run: " + "Fail!!!!!!!!!!!!");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String answer = response.body().string();
                Log.i(Data.getLOG_TAG(), "onResponse run: " + answer);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        receiveText.setText(answer.toString());
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestTimer.cancel();
    }

    private EditText sendText;
    private TextView receiveText;
    private Button send;
    private Button receive;

    private OkHttpClient client;
    private HttpClient httpClient;

    private Timer requestTimer;
}
