package com.rodionov.mishka.flagmemorine.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Lab on 12.07.2018.
 */

public class CountryInfo {

    public CountryInfo(){}

    public CountryInfo(String cn){
        this.countryName = cn;
        this.capital = "";
        this.population = "";
        this.square = "";
    }

    public CountryInfo(String cn, String cap, String pop, String sq){
        this.countryName = cn;
        this.capital = cap;
        this.population = pop;
        this.square = sq;
    }

    public static void sortCountryInfo(){
        CountryInfo countryInfo;
        for (int i = 0; i < countries.size() - 1; i++) {
            for (int j = i + 1; j < countries.size(); j++) {
                if (compareStrings(countries.get(i).getCountryName(), countries.get(j).getCountryName())/*countries.get(i).getCountryName() < countries.get(j).getCountryName()*/){
                    countryInfo = countries.get(i);
                    countries.set(i,countries.get(j));
                    countries.set(j,countryInfo);
                }
            }
        }
    }

    public static boolean compareStrings(String str1, String str2){
        int size;
        if (str1.length() < str2.length()){
            size = str1.length();
        }else {
            size = str2.length();
        }
        for (int i = 0; i < size; i++) {
            if (str1.charAt(i) != str2.charAt(i)){
                if (str1.charAt(i) > str2.charAt(i)){               // Must be <
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public static void initCountryInfo(){
        initCountryInfoMap();
        countries = new ArrayList<>();
        Iterator iterator = countryInfoMap.keySet().iterator();
        while (iterator.hasNext()){
            String cname = iterator.next().toString();
            String[] param = countryInfoMap.get(cname);
            countries.add(new CountryInfo(cname, param[0], param[1], param[2]));
        }
    }

    public static void initCountryInfoMap(){
        countryInfoMap = new HashMap<>();
        countryInfoMap.put("Afghanistan",new String[]{"Kabul", "34 656 032", "652 864 km2"});
        countryInfoMap.put("Albania", new String[]{"Tirana", "2 876 591", "22 072 km2"});
        countryInfoMap.put("Algeria", new String[]{"Algiers", "42 200 000", "2 381 741 km2"});
        countryInfoMap.put("American Samoa", new String[]{"Pago Pago", "51 504", "199 km2"});
        countryInfoMap.put("Andorra", new String[]{"Andorra la Vella", "77 281", "467.63 km2"});
        countryInfoMap.put("Angola", new String[]{"Luanda", "25 789 024", "1 246 700 km2"});
        countryInfoMap.put("Anguilla", new String[]{"The Valley", "14 764", "91 km2"});
//        countryInfoMap.put("Antarctica",
        countryInfoMap.put("Antigua and Barbuda", new String[]{"St. John's", "100 963", "440 km2"});
        countryInfoMap.put("Argentina", new String[]{"Buenos Aires", "43 847 430", "2 780 400 km2"});
        countryInfoMap.put("Armenia", new String[]{"Yerevan", "2 924 816", "29 743 km2"});
        countryInfoMap.put("Aruba", new String[]{"Oranjestad", "104 822", "178.91 km2"});
        countryInfoMap.put("Australia", new String[]{"Canberra", "24 993 800", "7 692 024 km2"});
        countryInfoMap.put("Austria", new String[]{"Vienna", "8 823 054", "83 879 km2"});
        countryInfoMap.put("Azerbaijan", new String[]{"Baku", "9 911 646", "86 600 km2"});
        countryInfoMap.put("Bahamas", new String[]{"Nassau", "391 232", "13 878 km2"});
        countryInfoMap.put("Bahrain", new String[]{"Manama", "1 343 000", "766 km2"});
//        countryInfoMap.put("Bangladesh",
//        countryInfoMap.put("Barbados",
//        countryInfoMap.put("Belarus",
//        countryInfoMap.put("Belgium",
//        countryInfoMap.put("Belize",
//        countryInfoMap.put("Benin",
//        countryInfoMap.put("Bermuda",
//        countryInfoMap.put("Bhutan",
//        countryInfoMap.put("Bolivia",
//        countryInfoMap.put("Bonaire",
//        countryInfoMap.put("Bosnian",
//        countryInfoMap.put("Botswana",
//        countryInfoMap.put("Brazil",
//        countryInfoMap.put("Brunei",
//        countryInfoMap.put("Bulgaria",
//        countryInfoMap.put("Burkina Faso",
//        countryInfoMap.put("Burundi",
//        countryInfoMap.put("Cambodia",
//        countryInfoMap.put("Cameroon",
//        countryInfoMap.put("Canada",
//        countryInfoMap.put("Cape Verde",
//        countryInfoMap.put("Cayman Islands",
//        countryInfoMap.put("Central African Republic",
//        countryInfoMap.put("Chad",
//        countryInfoMap.put("Chile",
        countryInfoMap.put("China", new String[]{"Beijing", "1 430 075 000", "9 598 962 km2"});
//        countryInfoMap.put("Christmas Island",
//        countryInfoMap.put("Cocos Islands",
//        countryInfoMap.put("Colombia",
//        countryInfoMap.put("Comoros",
//        countryInfoMap.put("Congo",
//        countryInfoMap.put("Democratic Republic of the Congo",
//        countryInfoMap.put("Cook Islands",
//        countryInfoMap.put("Costa Rica",
//        countryInfoMap.put("Croatian",
//        countryInfoMap.put("Cuba",
//        countryInfoMap.put("Curacao",
//        countryInfoMap.put("Cyprus",
//        countryInfoMap.put("Czech Republic",
//        countryInfoMap.put("Denmark",
//        countryInfoMap.put("Djibouti",
//        countryInfoMap.put("Dominica",
//        countryInfoMap.put("Dominican Republic",
//        countryInfoMap.put("East Timor",
//        countryInfoMap.put("Ecuador",
//        countryInfoMap.put("Egypt",
//        countryInfoMap.put("El Salvador",
//        countryInfoMap.put("England",
//        countryInfoMap.put("Equatorial Guinea",
//        countryInfoMap.put("Eritrea",
//        countryInfoMap.put("Estonia",
//        countryInfoMap.put("Ethiopia",
//        countryInfoMap.put("European Union",
//        countryInfoMap.put("Falkland Islands",
//        countryInfoMap.put("Faroe Islands",
//        countryInfoMap.put("Fiji",
//        countryInfoMap.put("Finland",
//        countryInfoMap.put("France",
//        countryInfoMap.put("French Polynesia",
//        countryInfoMap.put("Gabon",
//        countryInfoMap.put("Gambia",
//        countryInfoMap.put("Georgia",
//        countryInfoMap.put("Germany",
//        countryInfoMap.put("Ghana",
//        countryInfoMap.put("Gibraltar",
//        countryInfoMap.put("Greece",
//        countryInfoMap.put("Greenland",
//        countryInfoMap.put("Grenada",
//        countryInfoMap.put("Guam",
//        countryInfoMap.put("Guatemala",
//        countryInfoMap.put("Guinea",
//        countryInfoMap.put("Guinea Bissau",
//        countryInfoMap.put("Guyana",
//        countryInfoMap.put("Haiti",
//        countryInfoMap.put("Honduras",
//        countryInfoMap.put("Hong Kong",
//        countryInfoMap.put("Hungary",
//        countryInfoMap.put("Iceland",
//        countryInfoMap.put("India",
//        countryInfoMap.put("Indonesia",
//        countryInfoMap.put("Iran",
//        countryInfoMap.put("Iraq",
//        countryInfoMap.put("Ireland",
//        countryInfoMap.put("Isle of Man",
//        countryInfoMap.put("Israel",
//        countryInfoMap.put("Italy",
//        countryInfoMap.put("Ivory Coast",
//        countryInfoMap.put("Jamaica",
//        countryInfoMap.put("Japan",
//        countryInfoMap.put("Jordan",
//        countryInfoMap.put("Kazakhstan",
//        countryInfoMap.put("Kenya",
//        countryInfoMap.put("Kiribati",
//        countryInfoMap.put("Kosovo",
//        countryInfoMap.put("Kuwait",
//        countryInfoMap.put("Kyrgyzstan",
//        countryInfoMap.put("Laos",
//        countryInfoMap.put("Latvia",
//        countryInfoMap.put("Lebanon",
//        countryInfoMap.put("Lesotho",
//        countryInfoMap.put("Liberia",
//        countryInfoMap.put("Libya",
//        countryInfoMap.put("Liechtenstein",
//        countryInfoMap.put("Lithuania",
//        countryInfoMap.put("Luxembourg",
//        countryInfoMap.put("Macedonia",
//        countryInfoMap.put("Madagascar",
//        countryInfoMap.put("Malawi",
//        countryInfoMap.put("Malaysia",
//        countryInfoMap.put("Maldives",
//        countryInfoMap.put("Mali",
//        countryInfoMap.put("Malta",
//        countryInfoMap.put("Marshall Islands",
//        countryInfoMap.put("Mauritania",
//        countryInfoMap.put("Mauritius",
//        countryInfoMap.put("Mexico",
//        countryInfoMap.put("Micronesia",
//        countryInfoMap.put("Moldova",
//        countryInfoMap.put("Monaco",
//        countryInfoMap.put("Mongolia",
//        countryInfoMap.put("Montenegro",
//        countryInfoMap.put("Morocco",
//        countryInfoMap.put("Mozambique",
//        countryInfoMap.put("Myanmar",
//        countryInfoMap.put("Namibia",
//        countryInfoMap.put("Nauru",
//        countryInfoMap.put("Nepal",
//        countryInfoMap.put("Netherlands",
//        countryInfoMap.put("New Zealand",
//        countryInfoMap.put("Nicaragua",
//        countryInfoMap.put("Niger",
//        countryInfoMap.put("Nigeria",
//        countryInfoMap.put("Niue",
//        countryInfoMap.put("Norfolk Island",
//        countryInfoMap.put("Northern Ireland",
//        countryInfoMap.put("Northern Mariana Islands",
//        countryInfoMap.put("North Korea",
//        countryInfoMap.put("Norway",
//        countryInfoMap.put("Oman",
//        countryInfoMap.put("Pakistan",
//        countryInfoMap.put("Palau",
//        countryInfoMap.put("Palestinian Territory",
//        countryInfoMap.put("Panama",
//        countryInfoMap.put("Papua New Guinea",
//        countryInfoMap.put("Paraguay",
//        countryInfoMap.put("Peru",
//        countryInfoMap.put("Philippines",
//        countryInfoMap.put("Pitcairn",
//        countryInfoMap.put("Poland",
//        countryInfoMap.put("Portugal",
//        countryInfoMap.put("Puerto Rico",
//        countryInfoMap.put("Qatar",
//        countryInfoMap.put("Reunion",
//        countryInfoMap.put("Romania",
        countryInfoMap.put("Russia", new String[]{"Moscow", "146 880 432", "17 125 191 km2"});
//        countryInfoMap.put("Rwanda",
//        countryInfoMap.put("Saint Barthelemy",
//        countryInfoMap.put("Saint Helena",
//        countryInfoMap.put("Saint Kitts and Nevis",
//        countryInfoMap.put("Saint Lucia",
//        countryInfoMap.put("Saint Martin",
//        countryInfoMap.put("Saint Pierre and Miquelon",
//        countryInfoMap.put("Samoa",
//        countryInfoMap.put("San Marino",
//        countryInfoMap.put("Sao Tome and Principe",
//        countryInfoMap.put("Saudi Arabia",
//        countryInfoMap.put("Scotland",
//        countryInfoMap.put("Senegal",
//        countryInfoMap.put("Serbia",
//        countryInfoMap.put("Seychelles",
//        countryInfoMap.put("Sierra Leone",
//        countryInfoMap.put("Singapore",
//        countryInfoMap.put("Sint Eustatius",
//        countryInfoMap.put("Sint Maarten",
//        countryInfoMap.put("Slovakia",
//        countryInfoMap.put("Slovenia",
//        countryInfoMap.put("Solomon Islands",
//        countryInfoMap.put("Somalia",
//        countryInfoMap.put("South Africa",
//        countryInfoMap.put("South Georgia",
//        countryInfoMap.put("South Korea",
//        countryInfoMap.put("South Sudan",
//        countryInfoMap.put("Spain",
//        countryInfoMap.put("Sri Lanka",
//        countryInfoMap.put("Sudan",
//        countryInfoMap.put("Suriname",
        countryInfoMap.put("Saint Vincent and the Grenadines", new String[]{"Kingstown", "117 534", "389.3 km2"});
//        countryInfoMap.put("Swaziland",
//        countryInfoMap.put("Sweden",
//        countryInfoMap.put("Switzerland",
//        countryInfoMap.put("Syria",
//        countryInfoMap.put("Taiwan",
//        countryInfoMap.put("Tajikistan",
//        countryInfoMap.put("Tanzania",
//        countryInfoMap.put("Thailand",
//        countryInfoMap.put("Togo",
//        countryInfoMap.put("Tokelau",
//        countryInfoMap.put("Tonga",
//        countryInfoMap.put("Trinidad and Tobago",
//        countryInfoMap.put("Tunisia",
//        countryInfoMap.put("Turkey",
//        countryInfoMap.put("Turkmenistan",
//        countryInfoMap.put("Turks and Caicos Islands",
//        countryInfoMap.put("Tuvalu",
//        countryInfoMap.put("Uganda",
//        countryInfoMap.put("Ukraine",
//        countryInfoMap.put("United Arab Emirates",
//        countryInfoMap.put("United Kingdom",
//        countryInfoMap.put("United States",
//        countryInfoMap.put("Uruguay",
//        countryInfoMap.put("Uzbekistan",
//        countryInfoMap.put("Vanuatu",
//        countryInfoMap.put("Vatican City",
//        countryInfoMap.put("Venezuela",
//        countryInfoMap.put("Vietnam",
//        countryInfoMap.put("Wales",
//        countryInfoMap.put("Yemen",
//        countryInfoMap.put("Zambia",
//        countryInfoMap.put("Zimbabwe"
    }

    public static List<CountryInfo> getCountries(){ return countries; }

    // region Getters

    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getPopulation() {
        return population;
    }

    public String getSquare() {
        return square;
    }

    // endregion Getters

    // region Setters

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    // endregion Setters

    private String countryName;
    private String capital;
    private String population;
    private String square;

    private static List<CountryInfo> countries;
    private static Map<String, String[]> countryInfoMap;

}
