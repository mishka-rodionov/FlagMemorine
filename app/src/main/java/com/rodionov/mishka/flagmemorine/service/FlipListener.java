package com.rodionov.mishka.flagmemorine.service;

/**
 * Created by Lab1 on 18.06.2018.
 */

public interface FlipListener {
    void flipEvent(int tag, String value);
    void deactivatePoint(int tag);
    int botFlip();
}
