package com.rodionov.mishka.flagmemorine.logic;

import android.util.Log;
import android.widget.ImageView;

import com.rodionov.mishka.flagmemorine.R;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Lab1 on 23.05.2018.
 */

public class Facts {

    public static String randomRussiaFacts(){
        Random rnd = new Random();
        int resource = rnd.nextInt(russia.length);
        return russia[resource];
    }

    private static void loadCountryFactsMap(){
        countryFactsMap = new HashMap<>();
        countryFactsMap.put(R.drawable.russia, russia);
        countryFactsMap.put(R.drawable.brazil, brazil);
        countryFactsMap.put(R.drawable.italy, italy);
        countryFactsMap.put(R.drawable.united_states, united_states);
        countryFactsMap.put(R.drawable.china, china);

    }

    public static String randomCountryFacts(ImageView imageView){
        loadCountryFactsMap();
        Random rnd = new Random();
        int resource = rnd.nextInt(countryResources.length);
        String[] country = countryFactsMap.get(countryResources[resource]);
        imageView.setImageResource(countryResources[resource]);
        Log.i(Data.getLOG_TAG(), "country resources length " + country.length);
        Log.i(Data.getLOG_TAG(), "resource  " + resource);
        Log.i(Data.getLOG_TAG(), "russia length " + russia.length);
        Log.i(Data.getLOG_TAG(), "brazil length " + brazil.length);
        Log.i(Data.getLOG_TAG(), "italy length " + italy.length);
        Log.i(Data.getLOG_TAG(), "united states length " + united_states.length);
        Log.i(Data.getLOG_TAG(), "china length " + china.length);
        Log.i(Data.getLOG_TAG(), "randomCountryFacts: " + country.length);
        int index = rnd.nextInt(country.length);
        return country[index];
    }

    private static HashMap<Integer, String[]> countryFactsMap;

    private static String[] russia = {
            "Russia - is a biggest country of the world.",
            "Moscow - is a capital of Russian Federation.",
            "Baikal - is a deepest lake of the world.",
            "The world’s first satellite, named Sputnik, was launched by the Soviet Union in 1957.",
            "The official residence of the Russian president is the Kremlin in Moscow. The name Kremlin means fortress.",
            "Mount Elbrus is the highest mountain in Russia (and Europe), it reaches a height of 5642 metres (18,510 feet).",
            "Russia’s Volga River is the longest in Europe, with a length of around 3690 kilometres (2293 miles).",
            "Russia produces a large amount of renewable energy thanks to its well developed hydropower stations.",
            "Russia is located across 9 time zones."
    };

    private static String[] afghanistan = {};
    private static String[] brazil = {
            "Brazil is the largest country in South America.",
            "The name Brazil comes from a tree named brazilwood.",
            "Portugal claimed the land of Brazil in the year 1500. Independence was declared in 1822.",
            "Brazil is the only country in South America that speaks Portuguese.",
            "Brazil has a large coastline on the eastern side of South America, stretching 7491 kilometres (4655 miles) in length.",
            "Brazil shares a border with all South American countries except for Chile and Ecuador."
    };
    private static String[] italy = {
            "Vatican City and San Marino are very small independent states located inside Italy.",
            "Rome is the capital city of Italy. Other major cities include Milan, Naples, Turin, and Palermo.",
            "Rome was the home to the Ancient Romans, a civilization that grew into a huge empire. Western civilization as we know today is based on many Ancient Roman principles.",
            "The volcanoes Etna and Vesuvius are a constant danger to humans due to their closeness to big cities.",
            "Ferrari, Lamborghini, Alfa Romeo and Maserati are well known Italian car manufacturers.",
            "The most popular sport in Italy is football (soccer). Italy has won four world cups, the last one being in 2006."
    };

    private static String[] united_states = {
            "The US is the 4th largest country in the world by land area and 3rd by population.",
            "The US developed the first nuclear weapons, using them on the Japanese cities of Hiroshima and Nagasaki near the end of World War 2.",
            "The Mississippi and Missouri Rivers combine to form the longest river system in the US and the fourth longest in the world.",
            "The tallest mountain the US is Mt McKinley, located in the state of Alaska it reaches 20,320 ft (6,194 m) above sea level.",
            "Alaska was purchased from Russia in 1867 and is the largest state in the US by land area.",
            "Hawaii is the most recent of the 50 states in the US (joining in 1959) and is the only one made up entirely of islands.",
            "Most of the world's tornadoes occur in the Midwest region of the US known as Tornado Alley."
    };
    private static String[] china = {
            "China has the largest population in the world, with over 1.3 billion people (1,343,239,923) as of July 2012.",
            "China is the 3rd largest country by area at 9,706,961 sq km (3,747,879 sq miles).",
            "China has the fourth longest river in the world, the Yangtze River, which reaches 5,797km (3,602 miles) in length. It also has the sixth longest, the Yellow River, stretching 4,667km (2,900 miles).",
            "The Great Wall of China is the largest man made structure in the world, stretching an incredible 8,850 kilometres (5,500 miles).",
            "In 2003, China became the third country to successfully send a person to space."
    };

    private static int[] countryResources = {
//                R.drawable.afghanistan,
//                R.drawable.aland_islands,
//                R.drawable.albania,
//                R.drawable.algeria,
//                R.drawable.american_samoa,
//                R.drawable.andorra,
//                R.drawable.angola,
//                R.drawable.anguilla,
//                R.drawable.antarctica,
//                R.drawable.antigua_and_barbuda,
//                R.drawable.argentina,
//                R.drawable.armenia,
//                R.drawable.aruba,
//                R.drawable.australia,
//                R.drawable.austria,
//                R.drawable.azerbaijan,
//                R.drawable.bahamas,
//                R.drawable.bahrain,
//                R.drawable.bangladesh,
//                R.drawable.barbados,
//                R.drawable.belarus,
//                R.drawable.belgium,
//                R.drawable.belize,
//                R.drawable.benin,
//                R.drawable.bermuda,
//                R.drawable.bhutan,
//                R.drawable.biot,
//                R.drawable.bolivia,
//                R.drawable.bonaire,
//                R.drawable.bosnian,
//                R.drawable.botswana,
//                R.drawable.bouvet_island,
                R.drawable.brazil,
//                R.drawable.british_antarctic_territory,
//                R.drawable.british_virgin_islands,
//                R.drawable.brunei,
//                R.drawable.bulgaria,
//                R.drawable.burkina_faso,
//                R.drawable.burma,
//                R.drawable.burundi,
//                R.drawable.cambodia,
//                R.drawable.cameroon,
//                R.drawable.canada,
//                R.drawable.cape_verde,
//                R.drawable.cascadia,
//                R.drawable.cayman_islands,
//                R.drawable.central_african_republic,
//                R.drawable.chad,
//                R.drawable.chile,
                R.drawable.china,
//                R.drawable.christmas_island,
//                R.drawable.cocos_islands,
//                R.drawable.colombia,
//                R.drawable.comoros,
//                R.drawable.congo,
//                R.drawable.congo_kinshasa,
//                R.drawable.cook_islands,
//                R.drawable.costa_rica,
//                R.drawable.croatian,
//                R.drawable.cuba,
//                R.drawable.curacao,
//                R.drawable.cyprus,
//                R.drawable.czech_republic,
//                R.drawable.denmark,
//                R.drawable.djibouti,
//                R.drawable.dominica,
//                R.drawable.dominican_republic,
//                R.drawable.east_timor,
//                R.drawable.ecuador,
//                R.drawable.egypt,
//                R.drawable.el_salvador,
//                R.drawable.england,
//                R.drawable.equatorial_guinea,
//                R.drawable.eritrea,
//                R.drawable.estonia,
//                R.drawable.ethiopia,
//                R.drawable.european_union,
//                R.drawable.ex_yugoslavia,
//                R.drawable.falkland_islands,
//                R.drawable.faroe_islands,
//                R.drawable.fiji,
//                R.drawable.finland,
//                R.drawable.france,
//                R.drawable.french_polynesia,
//                R.drawable.french_southern_territories,
//                R.drawable.gabon
//                R.drawable.gambia
//                R.drawable.georgia,
//                R.drawable.germany,
//                R.drawable.ghana,
//                R.drawable.gibraltar,
//                R.drawable.greece,
//                R.drawable.greenland,
//                R.drawable.grenada,
//                R.drawable.guam,
//                R.drawable.guadeloupe,
//                R.drawable.guatemala,
//                R.drawable.guernsey,
//                R.drawable.guinea_bissau,
//                R.drawable.guinea,
//                R.drawable.guyana
//                R.drawable.haiti,
//                R.drawable.holy_see,
//                R.drawable.honduras,
//                R.drawable.hong_kong,
//                R.drawable.hungary,
//                R.drawable.iceland,
//                R.drawable.india,
//                R.drawable.indonesia,
//                R.drawable.iran,
//                R.drawable.iraq,
//                R.drawable.ireland,
//                R.drawable.isle_of_man,
//                R.drawable.israel,
                R.drawable.italy,
//                R.drawable.ivory_coast,
//                R.drawable.jamaica,
//                R.drawable.jan_mayen,
//                R.drawable.japan,
//                R.drawable.jarvis_island,
//                R.drawable.jersey,
//                R.drawable.jordan,
//                R.drawable.kazakhstan,
//                R.drawable.kenya,
//                R.drawable.kiribati,
//                R.drawable.kosovo,
//                R.drawable.kuwait,
//                R.drawable.kyrgyzstan,
//                R.drawable.laos,
//                R.drawable.latvia,
//                R.drawable.lebanon,
//                R.drawable.lesotho,
//                R.drawable.liberia,
//                R.drawable.libya,
//                R.drawable.liechtenstein,
//                R.drawable.lithuania,
//                R.drawable.luxembourg,
//                R.drawable.macau,
//                R.drawable.macedonia,
//                R.drawable.madagascar,
//                R.drawable.malawi,
//                R.drawable.malaysia,
//                R.drawable.maldives,
//                R.drawable.mali,
//                R.drawable.malta,
//                R.drawable.marshall_islands,
//                R.drawable.martinique,
//                R.drawable.mauritania,
//                R.drawable.mauritius,
//                R.drawable.mayotte,
//                R.drawable.mexico,
//                R.drawable.micronesia,
//                R.drawable.moldova,
//                R.drawable.monaco,
//                R.drawable.mongolia,
//                R.drawable.montenegro,
//                R.drawable.montserrat,
//                R.drawable.morocco,
//                R.drawable.mozambique,
//                R.drawable.myanmar,
//                R.drawable.namibia,
//                R.drawable.nauru,
//                R.drawable.nepal,
//                R.drawable.netherlands_antilles,
//                R.drawable.netherlands,
//                R.drawable.new_caledonia,
//                R.drawable.new_zealand,
//                R.drawable.nicaragua,
//                R.drawable.niger,
//                R.drawable.nigeria,
//                R.drawable.niue,
//                R.drawable.norfolk_island,
//                R.drawable.northern_ireland,
//                R.drawable.north_korea,
//                R.drawable.northern_mariana_islands,
//                R.drawable.norway,
//                R.drawable.olympics,
//                R.drawable.oman,
//                R.drawable.pakistan,
//                R.drawable.palau,
//                R.drawable.palestinian_territory,
//                R.drawable.panama,
//                R.drawable.papua_new_guinea,
//                R.drawable.paraguay,
//                R.drawable.peru,
//                R.drawable.philippines,
//                R.drawable.pitcairn,
//                R.drawable.poland,
//                R.drawable.portugal,
//                R.drawable.puerto_rico,
//                R.drawable.qatar,
//                R.drawable.reunion,
//                R.drawable.romania,
                R.drawable.russia,
//                R.drawable.rwanda,
//                R.drawable.saint_barthelemy,
//                R.drawable.saint_helena,
//                R.drawable.saint_helena_and_dependencies,
//                R.drawable.saint_kitts_and_nevis,
//                R.drawable.saint_lucia,
//                R.drawable.saint_martin,
//                R.drawable.saint_pierre_and_miquelon,
//                R.drawable.samoa,
//                R.drawable.san_marino,
//                R.drawable.sao_tome_and_principe,
//                R.drawable.saudi_arabia,
//                R.drawable.scotland,
//                R.drawable.senegal,
//                R.drawable.serbia,
//                R.drawable.seychelles,
//                R.drawable.sierra_leone,
//                R.drawable.singapore,
//                R.drawable.sint_eustatius,
//                R.drawable.sint_maarten,
//                R.drawable.slovakia,
//                R.drawable.slovenia,
//                R.drawable.smom,
//                R.drawable.solomon_islands,
//                R.drawable.somalia,
//                R.drawable.south_africa,
//                R.drawable.south_georgia,
//                R.drawable.korea,
//                R.drawable.south_sudan,
//                R.drawable.spain,
//                R.drawable.spm,
//                R.drawable.sri_lanka,
//                R.drawable.sudan,
//                R.drawable.suriname,
//                R.drawable.svalbard,
//                R.drawable.svg,
//                R.drawable.swaziland,
//                R.drawable.sweden,
//                R.drawable.switzerland,
//                R.drawable.syria,
//                R.drawable.taiwan,
//                R.drawable.tajikistan,
//                R.drawable.tanzania,
//                R.drawable.thailand,
//                R.drawable.timor_leste,
//                R.drawable.togo,
//                R.drawable.tokelau,
//                R.drawable.tonga,
//                R.drawable.trinidad_and_tobago,
//                R.drawable.tunisia,
//                R.drawable.turkey,
//                R.drawable.turkmenistan,
//                R.drawable.turks_and_caicos_islands,
//                R.drawable.tuvalu,
//                R.drawable.uganda,
//                R.drawable.ukraine,
//                R.drawable.united_arab_emirates,
//                R.drawable.united_kingdom,
                R.drawable.united_states
//                R.drawable.unknown,
//                R.drawable.uruguay,
//                R.drawable.ussr,
//                R.drawable.uzbekistan,
//                R.drawable.vanuatu,
//                R.drawable.vatican_city,
//                R.drawable.venezuela,
//                R.drawable.vietnam,
//                R.drawable.virgin_islands,
//                R.drawable.wales,
//                R.drawable.wallis_and_futuna,
//                R.drawable.western_sahara,
//                R.drawable.yemen,
//                R.drawable.yugoslavia,
//                R.drawable.zambia,
//                R.drawable.zimbabwe
    };
}
