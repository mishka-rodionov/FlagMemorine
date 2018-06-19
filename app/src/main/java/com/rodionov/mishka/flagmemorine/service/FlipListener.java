package com.rodionov.mishka.flagmemorine.service;

import java.util.ArrayList;

/**
 * Created by Lab1 on 18.06.2018.
 */

public interface FlipListener {
    void flipEvent(int tag, String value);
    void deactivatePoint(int tag);
    ArrayList<Integer> botFlip();
}
