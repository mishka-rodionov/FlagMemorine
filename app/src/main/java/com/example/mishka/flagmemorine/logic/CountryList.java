package com.example.mishka.flagmemorine.logic;

import android.media.Image;
import android.widget.ImageView;

import com.example.mishka.flagmemorine.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Lab1 on 18.01.2018.
 */

public class CountryList {
    public CountryList(){

    }

    public static void loadCountryMap(){
        countryMap = new HashMap<>();
//        countryMap.put("Albania", R.drawable.albania);
//        countryMap.put("Andorra", R.drawable.andorra);
//        countryMap.put("Austria", R.drawable.austria);
//        countryMap.put("Belarus", R.drawable.belarus);
//        countryMap.put("Belgium", R.drawable.belgium);
//        countryMap.put("Bosnia and Herzegovina", R.drawable.bosnia_and_herzegovina);
//        countryMap.put("Bulgaria", R.drawable.bulgaria);
//        countryMap.put("Croatia", R.drawable.croatia);
//        countryMap.put("Czech Republic", R.drawable.czech_republic);
//        countryMap.put("Denmark", R.drawable.denmark);
//        countryMap.put("Estonia", R.drawable.estonia);
//        countryMap.put("Finland", R.drawable.finland);
//        countryMap.put("France", R.drawable.france);
//        countryMap.put("Germany", R.drawable.germany);
//        countryMap.put("Greece", R.drawable.greece);
//        countryMap.put("Hungary", R.drawable.hungary);
//        countryMap.put("Iceland", R.drawable.iceland);
//        countryMap.put("Ireland", R.drawable.ireland);
//        countryMap.put("Italy", R.drawable.italy);
//        countryMap.put("Kosovo", R.drawable.kosovo);
//        countryMap.put("Latvia", R.drawable.latvia);
//        countryMap.put("Liechtenstein", R.drawable.liechtenstein);
//        countryMap.put("Lithuania", R.drawable.lithuania);
//        countryMap.put("Luxembourg", R.drawable.luxembourg);
//        countryMap.put("Macedonia", R.drawable.macedonia);
//        countryMap.put("Malta", R.drawable.malta);
//        countryMap.put("Moldova", R.drawable.moldova);
//        countryMap.put("Monaco", R.drawable.monaco);
//        countryMap.put("Montenegro", R.drawable.montenegro);
//        countryMap.put("Netherlands", R.drawable.netherlands);
//        countryMap.put("Norway", R.drawable.norway);
//        countryMap.put("Poland", R.drawable.poland);
//        countryMap.put("Portugal", R.drawable.portugal);
//        countryMap.put("Romania", R.drawable.romania);
//        countryMap.put("Russia", R.drawable.russia);
//        countryMap.put("San Marino", R.drawable.san_marino);
//        countryMap.put("Serbia", R.drawable.serbia);
//        countryMap.put("Slovakia", R.drawable.slovakia);
//        countryMap.put("Slovenia", R.drawable.slovenia);
//        countryMap.put("Spain", R.drawable.spain);
//        countryMap.put("Sweden", R.drawable.sweden);
//        countryMap.put("Switzerland", R.drawable.switzerland);
//        countryMap.put("Turkey", R.drawable.turkey);
//        countryMap.put("Ukraine", R.drawable.ukraine);
//        countryMap.put("United Kingdom", R.drawable.united_kingdom);
//        countryMap.put("Vatican", R.drawable.vatican);
//        countryMap.put("Armenia", R.drawable.armenia);
//        countryMap.put("Azerbaijan", R.drawable.azerbaijan);
//        countryMap.put("Cyprus", R.drawable.cyprus);
//        countryMap.put("Georgia", R.drawable.georgia);
//        countryMap.put("Kazakhstan", R.drawable.kazakhstan);
                countryMap.put("Abkhazia",R.drawable.abkhazia);
                countryMap.put("Afghanistan",R.drawable.afghanistan);
                countryMap.put("Aland",R.drawable.aland);
                countryMap.put("Albania",R.drawable.albania);
                countryMap.put("Algeria",R.drawable.algeria);
                countryMap.put("American Samoa",R.drawable.american_samoa);
                countryMap.put("Andorra",R.drawable.andorra);
                countryMap.put("Angola",R.drawable.angola);
                countryMap.put("Anguilla",R.drawable.anguilla);
                countryMap.put("Antarctica",R.drawable.antarctica);
                countryMap.put("Antigua and Barbuda",R.drawable.antigua_and_barbuda);
                countryMap.put("Argentina",R.drawable.argentina);
                countryMap.put("Armenia",R.drawable.armenia);
                countryMap.put("Aruba",R.drawable.aruba);
                countryMap.put("Australia",R.drawable.australia);
                countryMap.put("Austria",R.drawable.austria);
                countryMap.put("Azerbaijan",R.drawable.azerbaijan);
                countryMap.put("Bahamas",R.drawable.bahamas);
                countryMap.put("Bahrain",R.drawable.bahrain);
                countryMap.put("Bangladesh",R.drawable.bangladesh);
                countryMap.put("Barbados",R.drawable.barbados);
                countryMap.put("Basque Country",R.drawable.basque_country);
                countryMap.put("Belarus",R.drawable.belarus);
                countryMap.put("Belgium",R.drawable.belgium);
                countryMap.put("Belize",R.drawable.belize);
                countryMap.put("Benin",R.drawable.benin);
                countryMap.put("Bermuda",R.drawable.bermuda);
                countryMap.put("Bhutan",R.drawable.bhutan);
                countryMap.put("Bolivia",R.drawable.bolivia);
                countryMap.put("Bosnia and Herzegovina",R.drawable.bosnia_and_herzegovina);
                countryMap.put("Botswana",R.drawable.botswana);
                countryMap.put("Brazil",R.drawable.brazil);
                countryMap.put("British Antarctic Territory",R.drawable.british_antarctic_territory);
                countryMap.put("British Virgin Islands",R.drawable.british_virgin_islands);
                countryMap.put("Brunei",R.drawable.brunei);
                countryMap.put("Bulgaria",R.drawable.bulgaria);
                countryMap.put("Burkina Faso",R.drawable.burkina_faso);
                countryMap.put("Burundi",R.drawable.burundi);
                countryMap.put("Cambodia",R.drawable.cambodia);
                countryMap.put("Cameroon",R.drawable.cameroon);
                countryMap.put("Canada",R.drawable.canada);
                countryMap.put("Canary Islands",R.drawable.canary_islands);
                countryMap.put("Cape Verde",R.drawable.cape_verde);
                countryMap.put("Cayman Islands",R.drawable.cayman_islands);
                countryMap.put("Central African Republic",R.drawable.central_african_republic);
                countryMap.put("Chad",R.drawable.chad);
                countryMap.put("Chile",R.drawable.chile);
                countryMap.put("China",R.drawable.china);
                countryMap.put("Christmas Island",R.drawable.christmas_island);
                countryMap.put("Cocos Keeling Islands",R.drawable.cocos_keeling_islands);
                countryMap.put("Colombia",R.drawable.colombia);
                countryMap.put("Commonwealth",R.drawable.commonwealth);
                countryMap.put("Comoros",R.drawable.comoros);
                countryMap.put("Cook Islands",R.drawable.cook_islands);
                countryMap.put("Costa Rica",R.drawable.costa_rica);
                countryMap.put("Cote dIvoire",R.drawable.cote_divoire);
                countryMap.put("Croatia",R.drawable.croatia);
                countryMap.put("Cuba",R.drawable.cuba);
                countryMap.put("Curacao",R.drawable.curacao);
                countryMap.put("Cyprus",R.drawable.cyprus);
                countryMap.put("Czech Republic",R.drawable.czech_republic);
                countryMap.put("Democratic Republic of the Congo",R.drawable.democratic_republic_of_the_congo);
                countryMap.put("Denmark",R.drawable.denmark);
                countryMap.put("Djibouti",R.drawable.djibouti);
                countryMap.put("Dominica",R.drawable.dominica);
                countryMap.put("Dominican Republic",R.drawable.dominican_republic);
                countryMap.put("East Timor",R.drawable.east_timor);
                countryMap.put("Ecuador",R.drawable.ecuador);
                countryMap.put("Egypt",R.drawable.egypt);
                countryMap.put("El Salvador",R.drawable.el_salvador);
                countryMap.put("England",R.drawable.england);
                countryMap.put("Equatorial Guinea",R.drawable.equatorial_guinea);
                countryMap.put("Eritrea",R.drawable.eritrea);
                countryMap.put("Estonia",R.drawable.estonia);
                countryMap.put("Ethiopia",R.drawable.ethiopia);
                countryMap.put("European Union",R.drawable.european_union);
                countryMap.put("Falkland Islands",R.drawable.falkland_islands);
                countryMap.put("Faroes",R.drawable.faroes);
                countryMap.put("Fiji",R.drawable.fiji);
                countryMap.put("Finland",R.drawable.finland);
                countryMap.put("France",R.drawable.france);
                countryMap.put("French Polynesia",R.drawable.french_polynesia);
                countryMap.put("French Southern Territories",R.drawable.french_southern_territories);
                countryMap.put("Gabon",R.drawable.gabon);
                countryMap.put("Gambia",R.drawable.gambia);
                countryMap.put("Georgia",R.drawable.georgia);
                countryMap.put("Germany",R.drawable.germany);
                countryMap.put("Ghana",R.drawable.ghana);
                countryMap.put("Gibraltar",R.drawable.gibraltar);
                countryMap.put("GoSquared",R.drawable.gosquared);
                countryMap.put("Greece",R.drawable.greece);
                countryMap.put("Greenland",R.drawable.greenland);
                countryMap.put("Grenada",R.drawable.grenada);
                countryMap.put("Guam",R.drawable.guam);
                countryMap.put("Guatemala",R.drawable.guatemala);
                countryMap.put("Guernsey",R.drawable.guernsey);
                countryMap.put("Guinea Bissau",R.drawable.guinea_bissau);
                countryMap.put("Guinea",R.drawable.guinea);
                countryMap.put("Guyana",R.drawable.guyana);
                countryMap.put("Haiti",R.drawable.haiti);
                countryMap.put("Honduras",R.drawable.honduras);
                countryMap.put("Hong Kong",R.drawable.hong_kong);
                countryMap.put("Hungary",R.drawable.hungary);
                countryMap.put("Iceland",R.drawable.iceland);
                countryMap.put("India",R.drawable.india);
                countryMap.put("Indonesia",R.drawable.indonesia);
                countryMap.put("Iran",R.drawable.iran);
                countryMap.put("Iraq",R.drawable.iraq);
                countryMap.put("Ireland",R.drawable.ireland);
                countryMap.put("Isle of Man",R.drawable.isle_of_man);
                countryMap.put("Israel",R.drawable.israel);
                countryMap.put("Italy",R.drawable.italy);
                countryMap.put("Jamaica",R.drawable.jamaica);
                countryMap.put("Japan",R.drawable.japan);
                countryMap.put("Jersey",R.drawable.jersey);
                countryMap.put("Jordan",R.drawable.jordan);
                countryMap.put("Kazakhstan",R.drawable.kazakhstan);
                countryMap.put("Kenya",R.drawable.kenya);
                countryMap.put("Kiribati",R.drawable.kiribati);
                countryMap.put("Kosovo",R.drawable.kosovo);
                countryMap.put("Kuwait",R.drawable.kuwait);
                countryMap.put("Kyrgyzstan",R.drawable.kyrgyzstan);
                countryMap.put("Laos",R.drawable.laos);
                countryMap.put("Latvia",R.drawable.latvia);
                countryMap.put("Lebanon",R.drawable.lebanon);
                countryMap.put("Lesotho",R.drawable.lesotho);
                countryMap.put("Liberia",R.drawable.liberia);
                countryMap.put("Libya",R.drawable.libya);
                countryMap.put("Liechtenstein",R.drawable.liechtenstein);
                countryMap.put("Lithuania",R.drawable.lithuania);
                countryMap.put("Luxembourg",R.drawable.luxembourg);
                countryMap.put("Macau",R.drawable.macau);
                countryMap.put("Macedonia",R.drawable.macedonia);
                countryMap.put("Madagascar",R.drawable.madagascar);
                countryMap.put("Malawi",R.drawable.malawi);
                countryMap.put("Malaysia",R.drawable.malaysia);
                countryMap.put("Maldives",R.drawable.maldives);
                countryMap.put("Mali",R.drawable.mali);
                countryMap.put("Malta",R.drawable.malta);
                countryMap.put("Mars",R.drawable.mars);
                countryMap.put("Marshall Islands",R.drawable.marshall_islands);
                countryMap.put("Martinique",R.drawable.martinique);
                countryMap.put("Mauritania",R.drawable.mauritania);
                countryMap.put("Mauritius",R.drawable.mauritius);
                countryMap.put("Mayotte",R.drawable.mayotte);
                countryMap.put("Mexico",R.drawable.mexico);
                countryMap.put("Micronesia",R.drawable.micronesia);
                countryMap.put("Moldova",R.drawable.moldova);
                countryMap.put("Monaco",R.drawable.monaco);
                countryMap.put("Mongolia",R.drawable.mongolia);
                countryMap.put("Montenegro",R.drawable.montenegro);
                countryMap.put("Montserrat",R.drawable.montserrat);
                countryMap.put("Morocco",R.drawable.morocco);
                countryMap.put("Mozambique",R.drawable.mozambique);
                countryMap.put("Myanmar",R.drawable.myanmar);
                countryMap.put("Nagorno Karabakh",R.drawable.nagorno_karabakh);
                countryMap.put("Namibia",R.drawable.namibia);
                countryMap.put("NATO",R.drawable.nato);
                countryMap.put("Nauru",R.drawable.nauru);
                countryMap.put("Nepal",R.drawable.nepal);
                countryMap.put("Netherlands Antilles",R.drawable.netherlands_antilles);
                countryMap.put("Netherlands",R.drawable.netherlands);
                countryMap.put("New Caledonia",R.drawable.new_caledonia);
                countryMap.put("New Zealand",R.drawable.new_zealand);
                countryMap.put("Nicaragua",R.drawable.nicaragua);
                countryMap.put("Niger",R.drawable.niger);
                countryMap.put("Nigeria",R.drawable.nigeria);
                countryMap.put("Niue",R.drawable.niue);
                countryMap.put("Norfolk Island",R.drawable.norfolk_island);
                countryMap.put("North Korea",R.drawable.north_korea);
                countryMap.put("Northern Cyprus",R.drawable.northern_cyprus);
                countryMap.put("Northern Mariana Islands",R.drawable.northern_mariana_islands);
                countryMap.put("Norway",R.drawable.norway);
                countryMap.put("Olympics",R.drawable.olympics);
                countryMap.put("Oman",R.drawable.oman);
                countryMap.put("Pakistan",R.drawable.pakistan);
                countryMap.put("Palau",R.drawable.palau);
                countryMap.put("Palestine",R.drawable.palestine);
                countryMap.put("Panama",R.drawable.panama);
                countryMap.put("Papua New Guinea",R.drawable.papua_new_guinea);
                countryMap.put("Paraguay",R.drawable.paraguay);
                countryMap.put("Peru",R.drawable.peru);
                countryMap.put("Philippines",R.drawable.philippines);
                countryMap.put("Pitcairn Islands",R.drawable.pitcairn_islands);
                countryMap.put("Poland",R.drawable.poland);
                countryMap.put("Portugal",R.drawable.portugal);
                countryMap.put("Puerto Rico",R.drawable.puerto_rico);
                countryMap.put("Qatar",R.drawable.qatar);
                countryMap.put("Red Cross",R.drawable.red_cross);
                countryMap.put("Republic of the Congo",R.drawable.republic_of_the_congo);
                countryMap.put("Romania",R.drawable.romania);
                countryMap.put("Russia",R.drawable.russia);
                countryMap.put("Rwanda",R.drawable.rwanda);
                countryMap.put("Saint Barthelemy",R.drawable.saint_barthelemy);
                countryMap.put("Saint Helena",R.drawable.saint_helena);
                countryMap.put("Saint Kitts and Nevis",R.drawable.saint_kitts_and_nevis);
                countryMap.put("Saint Lucia",R.drawable.saint_lucia);
                countryMap.put("Saint Martin",R.drawable.saint_martin);
                countryMap.put("Saint Vincent and the Grenadines",R.drawable.saint_vincent_and_the_grenadines);
                countryMap.put("Samoa",R.drawable.samoa);
                countryMap.put("San Marino",R.drawable.san_marino);
                countryMap.put("Sao Tome and Principe",R.drawable.sao_tome_and_principe);
                countryMap.put("Saudi Arabia",R.drawable.saudi_arabia);
                countryMap.put("Scotland",R.drawable.scotland);
                countryMap.put("Senegal",R.drawable.senegal);
                countryMap.put("Serbia",R.drawable.serbia);
                countryMap.put("Seychelles",R.drawable.seychelles);
                countryMap.put("Sierra Leone",R.drawable.sierra_leone);
                countryMap.put("Singapore",R.drawable.singapore);
                countryMap.put("Slovakia",R.drawable.slovakia);
                countryMap.put("Slovenia",R.drawable.slovenia);
                countryMap.put("Solomon Islands",R.drawable.solomon_islands);
                countryMap.put("Somalia",R.drawable.somalia);
                countryMap.put("Somaliland",R.drawable.somaliland);
                countryMap.put("South Africa",R.drawable.south_africa);
                countryMap.put("South Georgia and the South Sandwich Islands",R.drawable.south_georgia_and_the_south_sandwich_islands);
                countryMap.put("South Korea",R.drawable.south_korea);
                countryMap.put("South Ossetia",R.drawable.south_ossetia);
                countryMap.put("South Sudan",R.drawable.south_sudan);
                countryMap.put("Spain",R.drawable.spain);
                countryMap.put("Sri Lanka",R.drawable.sri_lanka);
                countryMap.put("Sudan",R.drawable.sudan);
                countryMap.put("Suriname",R.drawable.suriname);
                countryMap.put("Swaziland",R.drawable.swaziland);
                countryMap.put("Sweden",R.drawable.sweden);
                countryMap.put("Switzerland",R.drawable.switzerland);
                countryMap.put("Syria",R.drawable.syria);
                countryMap.put("Taiwan",R.drawable.taiwan);
                countryMap.put("Tajikistan",R.drawable.tajikistan);
                countryMap.put("Tanzania",R.drawable.tanzania);
                countryMap.put("Thailand",R.drawable.thailand);
                countryMap.put("Togo",R.drawable.togo);
                countryMap.put("Tokelau",R.drawable.tokelau);
                countryMap.put("Tonga",R.drawable.tonga);
                countryMap.put("Trinidad and Tobago",R.drawable.trinidad_and_tobago);
                countryMap.put("Tunisia",R.drawable.tunisia);
                countryMap.put("Turkey",R.drawable.turkey);
                countryMap.put("Turkmenistan",R.drawable.turkmenistan);
                countryMap.put("Turks and Caicos Islands",R.drawable.turks_and_caicos_islands);
                countryMap.put("Tuvalu",R.drawable.tuvalu);
                countryMap.put("Uganda",R.drawable.uganda);
                countryMap.put("Ukraine",R.drawable.ukraine);
                countryMap.put("United Arab Emirates",R.drawable.united_arab_emirates);
                countryMap.put("United Kingdom",R.drawable.united_kingdom);
                countryMap.put("United Nations",R.drawable.united_nations);
                countryMap.put("United States",R.drawable.united_states);
                countryMap.put("Unknown",R.drawable.unknown);
                countryMap.put("Uruguay",R.drawable.uruguay);
                countryMap.put("US Virgin Islands",R.drawable.us_virgin_islands);
                countryMap.put("Uzbekistan",R.drawable.uzbekistan);
                countryMap.put("Vanuatu",R.drawable.vanuatu);
                countryMap.put("Vatican City",R.drawable.vatican_city);
                countryMap.put("Venezuela",R.drawable.venezuela);
                countryMap.put("Vietnam",R.drawable.vietnam);
                countryMap.put("Wales",R.drawable.wales);
                countryMap.put("Wallis And Futuna",R.drawable.wallis_and_futuna);
                countryMap.put("Western Sahara",R.drawable.western_sahara);
                countryMap.put("Yemen",R.drawable.yemen);
                countryMap.put("Zambia",R.drawable.zambia);
                countryMap.put("Zimbabwe",R.drawable.zimbabwe);

    }

    public static Integer getCountry(String name){
        return countryMap.get(name);
    }

    public static String getCountry(int index){return country.get(index);}

    public static void loading(int size){
        country = new ArrayList<String>(size);
        Random rnd = new Random();
//        int index = rnd.nextInt(countryList.length);
//        for (int i = 0; i < size; i++) {
//            if(i % 2 != 0){
//                country.add(i, countryList[index]);
//                index++;
//                if(index == countryList.length)
//                    index = 0;
//            }
//            else
//                country.add(i, countryList[index]);
//        }
            int index;
            for (int i = 0; i < size; i+=2) {
                index = rnd.nextInt(countryList.length);
                if(index == countryList.length)
                    index = 0;
                country.add(i, countryList[index]);
                country.add(i+1, countryList[index]);
            }
    }

    public static void shuffle(){
        Collections.shuffle(country);
    }

    public static ArrayList getCountryList(){return country;}

    private static ArrayList<String> country;
    private static HashMap<String, Integer> countryMap;
//    private static String[] countryList = {
//            "Albania",
//            "Andorra",
//            "Armenia",
//            "Austria",
//            "Azerbaijan",
//            "Belarus",
//            "Belgium",
//            "Bosnia and Herzegovina",
//            "Bulgaria",
//            "Croatia",
//            "Cyprus",
//            "Czech Republic",
//            "Denmark",
//            "Estonia",
//            "Finland",
//            "France",
//            "Georgia",
//            "Germany",
//            "Greece",
//            "Hungary",
//            "Iceland",
//            "Ireland",
//            "Italy",
//            "Kazakhstan",
//            "Kosovo",
//            "Latvia",
//            "Liechtenstein",
//            "Lithuania",
//            "Luxembourg",
//            "Macedonia",
//            "Malta",
//            "Moldova",
//            "Monaco",
//            "Montenegro",
//            "Netherlands",
//            "Norway",
//            "Poland",
//            "Portugal",
//            "Romania",
//            "Russia",
//            "San Marino",
//            "Serbia",
//            "Slovakia",
//            "Slovenia",
//            "Spain",
//            "Sweden",
//            "Switzerland",
//            "Turkey",
//            "Ukraine",
//            "United Kingdom",
//            "Vatican"};

    private static String[] countryList = {
            "Abkhazia",
            "Afghanistan",
            "Aland",
            "Albania",
            "Algeria",
            "American Samoa",
            "Andorra",
            "Angola",
            "Anguilla",
            "Antarctica",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Basque Country",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bermuda",
            "Bhutan",
            "Bolivia",
            "Bosnia and Herzegovina",
            "Botswana",
            "Brazil",
            "British Antarctic Territory",
            "British Virgin Islands",
            "Brunei",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Canary Islands",
            "Cape Verde",
            "Cayman Islands",
            "Central African Republic",
            "Chad",
            "Chile",
            "China",
            "Christmas Island",
            "Cocos Keeling Islands",
            "Colombia",
            "Commonwealth",
            "Comoros",
            "Cook Islands",
            "Costa Rica",
            "Cote dIvoire",
            "Croatia",
            "Cuba",
            "Curacao",
            "Cyprus",
            "Czech Republic",
            "Democratic Republic of the Congo",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic",
            "East Timor",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "England",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Ethiopia",
            "European Union",
            "Falkland Islands",
            "Faroes",
            "Fiji",
            "Finland",
            "France",
            "French Polynesia",
            "French Southern Territories",
            "Gabon",
            "Gambia",
            "Georgia",
            "Germany",
            "Ghana",
            "Gibraltar",
            "GoSquared",
            "Greece",
            "Greenland",
            "Grenada",
            "Guam",
            "Guatemala",
            "Guernsey",
            "Guinea Bissau",
            "Guinea",
            "Guyana",
            "Haiti",
            "Honduras",
            "Hong Kong",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Ireland",
            "Isle of Man",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jersey",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Kosovo",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macau",
            "Macedonia",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Mars",
            "Marshall Islands",
            "Martinique",
            "Mauritania",
            "Mauritius",
            "Mayotte",
            "Mexico",
            "Micronesia",
            "Moldova",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Montserrat",
            "Morocco",
            "Mozambique",
            "Myanmar",
            "Nagorno Karabakh",
            "Namibia",
            "NATO",
            "Nauru",
            "Nepal",
            "Netherlands Antilles",
            "Netherlands",
            "New Caledonia",
            "New Zealand",
            "Nicaragua",
            "Niger",
            "Nigeria",
            "Niue",
            "Norfolk Island",
            "North Korea",
            "Northern Cyprus",
            "Northern Mariana Islands",
            "Norway",
            "Olympics",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestine",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines",
            "Pitcairn Islands",
            "Poland",
            "Portugal",
            "Puerto Rico",
            "Qatar",
            "Red Cross",
            "Republic of the Congo",
            "Romania",
            "Russia",
            "Rwanda",
            "Saint Barthelemy",
            "Saint Helena",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Martin",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia",
            "Scotland",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "Somaliland",
            "South Africa",
            "South Georgia and the South Sandwich Islands",
            "South Korea",
            "South Ossetia",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan",
            "Suriname",
            "Swaziland",
            "Sweden",
            "Switzerland",
            "Syria",
            "Taiwan",
            "Tajikistan",
            "Tanzania",
            "Thailand",
            "Togo",
            "Tokelau",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Turks and Caicos Islands",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates",
            "United Kingdom",
            "United Nations",
            "United States",
            "Unknown",
            "Uruguay",
            "US Virgin Islands",
            "Uzbekistan",
            "Vanuatu",
            "Vatican City",
            "Venezuela",
            "Vietnam",
            "Wales",
            "Wallis And Futuna",
            "Western Sahara",
            "Yemen",
            "Zambia",
            "Zimbabwe"};
}
