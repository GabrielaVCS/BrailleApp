package com.example.myapplication;

import java.util.HashMap;
import java.util.Map;

public class MapKeysBraille {
    private static String TAG = "MapKeysBraille";

    private static Map<String, String> mapKeys = new HashMap<String, String>();

    public static Map<String, String> getMapKeys() {
        mapKeys.put("A", "100000");
        mapKeys.put("B", "101000");
        mapKeys.put("C", "110000");
        mapKeys.put("D", "110100");
        mapKeys.put("E", "100100");
        mapKeys.put("F", "111000");
        mapKeys.put("G", "111100");
        mapKeys.put("H", "101100");
        mapKeys.put("I", "011000");
        mapKeys.put("J", "011100");
        mapKeys.put("K", "100010");
        //L
        mapKeys.put("M", "110010");
        mapKeys.put("N", "110110");
        mapKeys.put("O", "100110");
        //P
        //Q -> arrumar
        mapKeys.put("R", "101110");
        mapKeys.put("S", "011010");
        mapKeys.put("T", "011110");
        mapKeys.put("U", "100011");
        mapKeys.put("V", "101011");
        mapKeys.put("X", "110011");
        //W
        //Y
        mapKeys.put("Z", "100111");

        return mapKeys;
    }
}
