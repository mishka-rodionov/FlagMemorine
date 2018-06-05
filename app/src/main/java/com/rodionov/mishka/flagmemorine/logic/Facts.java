package com.rodionov.mishka.flagmemorine.logic;

import java.util.Random;

/**
 * Created by Lab1 on 23.05.2018.
 */

public class Facts {

    public static String randomCountryFacts(){
        Random rnd = new Random();
        int index = rnd.nextInt(countryFacts.length);
        return countryFacts[index];
    }

    private static String[] countryFacts = {
            "Russia - is a biggest country of the world.",
            "Moscow - is a capital of Russian Federation.",
            "Baikal - is  a deepest lake of the world."
    };
}
