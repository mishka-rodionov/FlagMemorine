package com.example.mishka.flagmemorine;

import android.util.Log;
import android.view.View;

/**
 * Created by Lab1 on 10.01.2018.
 */

public class ClickHandler implements View.OnClickListener {

    public ClickHandler(View view){
        this.view = view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageButton2:
                Log.d(LOG_TAG, "click imageButton2");
                break;
            case R.id.imageButton3:
                Log.d(LOG_TAG, "click imageButton3");
                break;
        }
    }

    private View view;
    private String LOG_TAG = "flagmemorine";
}
