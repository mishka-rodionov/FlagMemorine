package com.rodionov.mishka.flagmemorine.service;

import com.rodionov.mishka.flagmemorine.activity.MultiplayerBotActivity;

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

    public MultiplayerBot(int size, int lev){
        this.battlefieldSize = size;
        this.gameSteps = new ArrayList<>();
        this.battlefield = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            battlefield.add(i);
        }
        this.level = lev;
    }

    @Override
    public void deactivatePoint(int tag) {
        battlefield.remove(battlefield.indexOf(tag));
    }

    @Override
    public void flipEvent(int tag, String value) {
        couple = new HashMap<String, Integer>();
        couple.put(value, tag);
        gameSteps.add(couple);
    }

    @Override
    public int botFlip() {
        Random random = new Random();
        return battlefield.get(random.nextInt(battlefield.size()-1));
    }

    private List<Map> gameSteps;
    private Map<String, Integer> couple;
    private List<Integer> battlefield;
    private int battlefieldSize;
    private int level;
    private MultiplayerBotActivity mpba;
}
