package com.example.mishka.flagmemorine;

import android.media.Image;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by Lab1 on 18.01.2018.
 */

public class CountryList {
    public CountryList(){

    }

    public static void loadCountryMap(){
        countryMap = new HashMap<>();
        countryMap.put("Albania", R.drawable.albania);
        countryMap.put("Andorra", R.drawable.andorra);
        countryMap.put("Austria", R.drawable.austria);
        countryMap.put("Belarus", R.drawable.belarus);
        countryMap.put("Belgium", R.drawable.belgium);
        countryMap.put("Bosnia and Herzegovina", R.drawable.bosnia_and_herzegovina);
        countryMap.put("Bulgaria", R.drawable.bulgaria);
        countryMap.put("Croatia", R.drawable.croatia);
        countryMap.put("Czech Republic", R.drawable.czech_republic);
        countryMap.put("Denmark", R.drawable.denmark);
        countryMap.put("Estonia", R.drawable.estonia);
        countryMap.put("Finland", R.drawable.finland);
        countryMap.put("France", R.drawable.france);
        countryMap.put("Germany", R.drawable.germany);
        countryMap.put("Greece", R.drawable.greece);
        countryMap.put("Hungary", R.drawable.hungary);
        countryMap.put("Iceland", R.drawable.iceland);
        countryMap.put("Ireland", R.drawable.ireland);
        countryMap.put("Italy", R.drawable.italy);
        countryMap.put("Kosovo", R.drawable.kosovo);
        countryMap.put("Latvia", R.drawable.latvia);
        countryMap.put("Liechtenstein", R.drawable.liechtenstein);
        countryMap.put("Lithuania", R.drawable.lithuania);
        countryMap.put("Luxembourg", R.drawable.luxembourg);
        countryMap.put("Macedonia", R.drawable.macedonia);
        countryMap.put("Malta", R.drawable.malta);
        countryMap.put("Moldova", R.drawable.moldova);
        countryMap.put("Monaco", R.drawable.monaco);
        countryMap.put("Montenegro", R.drawable.montenegro);
        countryMap.put("Netherlands", R.drawable.netherlands);
        countryMap.put("Norway", R.drawable.norway);
        countryMap.put("Poland", R.drawable.poland);
        countryMap.put("Portugal", R.drawable.portugal);
        countryMap.put("Romania", R.drawable.romania);
        countryMap.put("Russia", R.drawable.russia);
        countryMap.put("San Marino", R.drawable.san_marino);
        countryMap.put("Serbia", R.drawable.serbia);
        countryMap.put("Slovakia", R.drawable.slovakia);
        countryMap.put("Slovenia", R.drawable.slovenia);
        countryMap.put("Spain", R.drawable.spain);
        countryMap.put("Sweden", R.drawable.sweden);
        countryMap.put("Switzerland", R.drawable.switzerland);
        countryMap.put("Turkey", R.drawable.turkey);
        countryMap.put("Ukraine", R.drawable.ukraine);
        countryMap.put("United Kingdom", R.drawable.united_kingdom);
        countryMap.put("Vatican", R.drawable.vatican);
        countryMap.put("Armenia", R.drawable.armenia);
        countryMap.put("Azerbaijan", R.drawable.azerbaijan);
        countryMap.put("Cyprus", R.drawable.cyprus);
        countryMap.put("Georgia", R.drawable.georgia);
        countryMap.put("Kazakhstan", R.drawable.kazakhstan);
//        countryMap.put("Armenia", R.drawable.armenia);

    }

    public static Integer getCountry(String name){
        return countryMap.get(name);
    }

    private static HashMap<String, Integer> countryMap;
    private static String[] countryList = {
            "Albania",
            "Andorra",
            "Armenia",
            "Austria",
            "Azerbaijan",
            "Belarus",
            "Belgium",
            "Bosnia and Herzegovina",
            "Bulgaria",
            "Croatia",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Estonia",
            "Finland",
            "France",
            "Georgia",
            "Germany",
            "Greece",
            "Hungary",
            "Iceland",
            "Ireland",
            "Italy",
            "Kazakhstan",
            "Kosovo",
            "Latvia",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macedonia",
            "Malta",
            "Moldova",
            "Monaco",
            "Montenegro",
            "Netherlands",
            "Norway",
            "Poland",
            "Portugal",
            "Romania",
            "Russia",
            "San Marino",
            "Serbia",
            "Slovakia",
            "Slovenia",
            "Spain",
            "Sweden",
            "Switzerland",
            "Turkey",
            "Ukraine",
            "United Kingdom",
            "Vatican"};
}
