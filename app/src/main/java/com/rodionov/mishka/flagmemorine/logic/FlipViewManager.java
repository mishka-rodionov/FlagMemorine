package com.rodionov.mishka.flagmemorine.logic;

import android.util.Log;
import android.view.View;

import com.rodionov.mishka.flagmemorine.R;

import java.util.ArrayList;
import java.util.HashMap;

import eu.davidea.flipview.FlipView;

/**
 * Created by Lab1 on 10.05.2018.
 */

public class FlipViewManager {

    public void initFlipView(View view, int battleFieldSize, BattleField battleField){
        flipViews = new ArrayList<>(battleFieldSize);
        if (battleFieldSize == Data.getXsmallSize()){
            for (int i = 0; i < Data.getXsmallSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxsmall(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
                flipViews.get(i).isFlipped();
            }
        }else if (battleFieldSize == Data.getSmallSize()){
            for (int i = 0; i < Data.getSmallSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdsmall(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getMediumSize()){
            for (int i = 0; i < Data.getMediumSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdmedium(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getLargeSize()){
            for (int i = 0; i < Data.getLargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdlarge(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getXlargeSize()){
            for (int i = 0; i < Data.getXlargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxlarge(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }else if (battleFieldSize == Data.getXxlargeSize()){
            for (int i = 0; i < Data.getXxlargeSize(); i++) {
                flipViews.add((FlipView) view.findViewById(Data.getIdxxlarge(i)));
                flipViews.get(i).setRearImage(CountryList.getCountry(battleField.getElement(i)));
            }
        }

        for (int i = 0; i < flipViews.size(); i++) {
            flipViews.get(i).setFrontImage(R.drawable.unknown);
            flipViews.get(i).setEnabled(true);
        }

        Log.d(Data.getLOG_TAG(), "flipview size = " + flipViews.size());
    }

    public ArrayList<FlipView> getFlipViews() {
        return flipViews;
    }

    private HashMap<Integer, Boolean> clickable;
    private ArrayList<FlipView> flipViews;
}
