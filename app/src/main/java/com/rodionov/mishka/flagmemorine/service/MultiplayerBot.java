package com.rodionov.mishka.flagmemorine.service;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.rodionov.mishka.flagmemorine.activity.MultiplayerBotActivity;
import com.rodionov.mishka.flagmemorine.logic.BattleField;
import com.rodionov.mishka.flagmemorine.logic.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by mishka on 17/06/18.
 */

public class MultiplayerBot implements FlipListener{

    public MultiplayerBot(){}

    public MultiplayerBot(int size, int lev, BattleField bf){
        this.battlefieldSize = size;
        this.gameSteps = new ArrayList<>();
        this.battlefieldList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            battlefieldList.add(i);         // Массив тэгов табличек игрового поля. В каждой ячейке массива записан тэег таблички.
        }
        this.level = lev;
        this.prevValue = -1;
        this.battleField = bf;
        this.botChoice = new ArrayList<>();
    }

    @Override
    public void deactivatePoint(int tag) {
        Log.i(Data.getLOG_TAG(), "deactivatePoint: BATTLEFIELD SIZE = " + battlefieldList.size());
        String string = "";
        for (int i = 0; i < battlefieldList.size(); i++) {
            string += battlefieldList.get(i) + " ";
        }
        Log.i(Data.getLOG_TAG(), "deactivatePoint: BATTLEFIELD LIST = " + string);
        Log.i(Data.getLOG_TAG(), "deactivatePoint: TAG = " + tag);
        Log.i(Data.getLOG_TAG(), "deactivatePoint: BATTLEFIELD INDEX OF = " + battlefieldList.indexOf(tag));
        battlefieldList.remove(battlefieldList.indexOf(tag));
//        battlefieldList.remove(tag);
    }

    @Override
    public void flipEvent(int tag, String value) {
        couple = new HashMap<String, Integer>();
        couple.put(value, tag);
        gameSteps.add(couple);
    }

    @Override
    public ArrayList<Integer> botFlip() {
        botChoice.clear();
        Random random = new Random();
        int value = battlefieldList.get(random.nextInt(battlefieldList.size()-1));
        Log.i(Data.getLOG_TAG(), "FIRST CALCULATE RANDOM VALUE  = " + value);
        String country;
        if (gameSteps.size() >= level){
            Log.i(Data.getLOG_TAG(), "gameSteps.size() > LEVEL " + gameSteps.size());
            country = battleField.getElement(value);
            List<HashMap> gameStepsTemp = new ArrayList<>();;
            for (int i = gameSteps.size() - 1; i > gameSteps.size() - level; i--) {
//                gameStepsTemp.add()
            }
            for (int i = gameSteps.size() - 1; i > gameSteps.size() - level; i--) {
                if (gameSteps.get(i).containsKey(country) && (Integer) gameSteps.get(i).get(country) != value){
//                    int idsf = (Integer) gameSteps.get(i).get(country);
                    HashMap<String, Integer> tag = gameSteps.get(i);
                    int choice = tag.get(country);
                    botChoice.add(choice);
                    botChoice.add(value);
                    break;
                }
            }
            if (botChoice.size() < 2){
                Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. BOT CHOICE = " + botChoice.size());
                List<Integer> temp = new ArrayList<>(battlefieldList);
//                temp = battlefieldList;
                Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
                Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
                temp.remove((Integer) value);
                Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
                Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
                int value2 = temp.get(random.nextInt(temp.size()));
                Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. VALUE 2 = " + value2);
                botChoice.add(value);
                botChoice.add(value2);

            }
        }else{
            Log.i(Data.getLOG_TAG(), "GAME STEPS < LEVEL. BOT CHOICE = " + botChoice.size());
            value = battlefieldList.get(random.nextInt(battlefieldList.size()-1));
            List<Integer> temp = new ArrayList<>(battlefieldList);
//            temp = battlefieldList;
            Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
            Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
            temp.remove((Integer) value);
            Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
            Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
            int value2 = temp.get(random.nextInt(temp.size()));
            Log.i(Data.getLOG_TAG(), "NOT OCCURENCE IN GAME STEPS. VALUE 2 = " + value2);
            botChoice.add(value);
            botChoice.add(value2);
        }
        Log.i(Data.getLOG_TAG(), "FINISH VALUE BOT CHOICE 0 = " + botChoice.get(0));
        Log.i(Data.getLOG_TAG(), "FINISH VALUE BOT CHOICE 1 = " + botChoice.get(1));
//        Log.i(Data.getLOG_TAG(), "botFlip: RANDOM PREVVALUE = " + prevValue);
//        Log.i(Data.getLOG_TAG(), "botFlip: RANDOM VALUE = " + value);
//        if (gameSteps.size() > 3)
//        {
//            Log.i(Data.getLOG_TAG(), "botFlip: GAMESTEP VALUE 1 = " + gameSteps.get(gameSteps.size() - 1));
//            Log.i(Data.getLOG_TAG(), "botFlip: GAMESTEP VALUE 2 = " + gameSteps.get(gameSteps.size() - 2));
//            Log.i(Data.getLOG_TAG(), "botFlip: GAMESTEP VALUE 3 = " + gameSteps.get(gameSteps.size() - 3));
//        }
//
//        if (battlefieldList.size() == 2){
//            value = battlefieldList.get(0);
//        }
//
//        if (value == prevValue){
//            if (battlefieldList.size() == 2){
//                value = battlefieldList.get(1);
//            }else{
//                value = battlefieldList.get(random.nextInt(battlefieldList.size()-1));
//            }
//
//        }else{
//            prevValue = value;
//        }
        return botChoice;
    }

    private List<HashMap> gameSteps;
    private HashMap<String, Integer> couple;
    private List<Integer> battlefieldList;
    private int battlefieldSize;
    private int level;
    private int prevValue;
    private MultiplayerBotActivity mpba;
    private BattleField battleField;
    private ArrayList<Integer> botChoice;
}
