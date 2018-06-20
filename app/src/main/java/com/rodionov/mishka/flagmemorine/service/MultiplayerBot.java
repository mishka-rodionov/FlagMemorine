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
        this.entryFlag = false;
    }

    @Override
    public void deactivatePoint(int tag) {
        Log.i(Data.getMultibotTag(), "deactivatePoint: BATTLEFIELD SIZE = " + battlefieldList.size());
        String string = "";
        for (int i = 0; i < battlefieldList.size(); i++) {
            string += battlefieldList.get(i) + " ";
        }
        Log.i(Data.getMultibotTag(), "deactivatePoint: BATTLEFIELD LIST = " + string);
        Log.i(Data.getMultibotTag(), "deactivatePoint: TAG = " + tag);
        Log.i(Data.getMultibotTag(), "deactivatePoint: BATTLEFIELD INDEX OF = " + battlefieldList.indexOf(tag));
        battlefieldList.remove(battlefieldList.indexOf(tag));
        int first = -1;
        int second = -1;
        for (int i = 0; i < gameSteps.size(); i++) {
            if (Integer.parseInt(gameSteps.get(i).get(1).toString()) == tag){
                if(first == -1){
                    first = i;
                }else{
                    second = i;
                }
            }
        }
        if (first != -1 && second != -1){
            gameSteps.remove(second);
            gameSteps.remove(first);
        }
//        battlefieldList.remove(tag);
    }

    @Override
    public void flipEvent(int tag, String value) {
        couple = new ArrayList<>();
        couple.add(value);
        couple.add(Integer.toString(tag));
        gameSteps.add(couple);
    }

    @Override
    public ArrayList<Integer> botFlip() {
        botChoice.clear();
        Random random = new Random();
        if (battlefieldList.size() > 0){
            int value = battlefieldList.get(random.nextInt(battlefieldList.size()));
            Log.i(Data.getMultibotTag(), "FIRST CALCULATE RANDOM VALUE  = " + value);
            String country;
            if (gameSteps.size() >= level){
                Log.i(Data.getMultibotTag(), "gameSteps.size() > LEVEL " + gameSteps.size());
                String gameS = "";
                for (int i = 0; i < gameSteps.size(); i++) {
                    gameS += i + " = { " + gameSteps.get(i).get(0) + " " + gameSteps.get(i).get(1) + "}, ";
                }
                Log.i(Data.getMultibotTag(), "botFlip: gameSteps = " + gameS);
                country = battleField.getElement(value);
                for (int i = gameSteps.size() - 1; i > gameSteps.size() - level; i--) {
                    Log.i(Data.getMultibotTag(), "i = " + i);
                    country = gameSteps.get(i).get(0).toString();
                    Log.i(Data.getMultibotTag(), "country = " + country);
                    for (int j = i - 1; j > gameSteps.size() - level; j--) {
                        Log.i(Data.getMultibotTag(), "j = " + j);
                        String country2 = gameSteps.get(j).get(0).toString();
                        Log.i(Data.getMultibotTag(), "country2 = " + country2);
                        if (country.equals(country2)){
                            botChoice.add(Integer.parseInt(gameSteps.get(i).get(1).toString()));
                            botChoice.add(Integer.parseInt(gameSteps.get(j).get(1).toString()));
                            gameSteps.remove(i);
                            gameSteps.remove(j);
                            entryFlag = true;
                            break;
                        }
                    }
                    if (entryFlag){
                        entryFlag = false;
                        break;
                    }
//                    Log.i(Data.getMultibotTag(), "botFlip: ADDED VALUES FROM gameSteps = " + botChoice.get(0) + " " + botChoice.get(1));
                }
//                if (entryFlag){
//                    gameSteps.re
//                }

                if (botChoice.size() < 2) {
                    for (int i = gameSteps.size() - 1; i > gameSteps.size() - level; i--) {
                        if (gameSteps.get(i).get(0).equals(country) && Integer.parseInt(gameSteps.get(i).get(1).toString()) != value) {
//                    int idsf = (Integer) gameSteps.get(i).get(country);
                            int choice = Integer.parseInt(gameSteps.get(i).get(1).toString());
                            botChoice.add(choice);
                            botChoice.add(value);
//                            break;
                        }
                    }
//                    Log.i(Data.getMultibotTag(), "botFlip: ADDED VALUES FROM gameSteps AND NOT gameSteps = " + botChoice.get(0) + " " + botChoice.get(1));
                }
                if (botChoice.size() < 2){
                    Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. BOT CHOICE = " + botChoice.size());
                    List<Integer> temp = new ArrayList<>(battlefieldList);
//                temp = battlefieldList;
                    Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
                    Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
                    temp.remove((Integer) value);
                    Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
                    Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
                    int value2 = temp.get(random.nextInt(temp.size()));
                    Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. VALUE 2 = " + value2);
                    botChoice.add(value);
                    botChoice.add(value2);

                }
            }else{
                Log.i(Data.getMultibotTag(), "GAME STEPS < LEVEL. BOT CHOICE = " + botChoice.size());
                value = battlefieldList.get(random.nextInt(battlefieldList.size()-1));
                List<Integer> temp = new ArrayList<>(battlefieldList);
//            temp = battlefieldList;
                Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
                Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
                temp.remove((Integer) value);
                Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. temp size = " + temp.size());
                Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. battlefieldList size = " + battlefieldList.size());
                int value2 = temp.get(random.nextInt(temp.size()));
                Log.i(Data.getMultibotTag(), "NOT OCCURENCE IN GAME STEPS. VALUE 2 = " + value2);
                botChoice.add(value);
                botChoice.add(value2);
            }
            Log.i(Data.getMultibotTag(), "FINISH VALUE BOT CHOICE 0 = " + botChoice.get(0));
            Log.i(Data.getMultibotTag(), "FINISH VALUE BOT CHOICE 1 = " + botChoice.get(1));
            return botChoice;
        }
        botChoice.add(0);
        botChoice.add(1);
        Log.i(Data.getMultibotTag(), "botFlip: ADDED DEFAULT VALUES = " + botChoice.get(0) + " " + botChoice.get(1));
        return botChoice;
    }

    private List<ArrayList> gameSteps;
    private ArrayList<String> couple;
    private List<Integer> battlefieldList;
    private int battlefieldSize;
    private int level;
    private int prevValue;
    private MultiplayerBotActivity mpba;
    private BattleField battleField;
    private ArrayList<Integer> botChoice;
    private Boolean entryFlag;
}
