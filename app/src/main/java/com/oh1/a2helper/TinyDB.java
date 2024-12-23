package com.oh1.a2helper;
/*
 * Copyright 2014 KC Ochibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *  The "‚‗‚" character is not a comma, it is the SINGLE LOW-9 QUOTATION MARK unicode 201A
 *  and unicode 2017 that are used for separating the items in a list.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;


public class TinyDB {

    private static TinyDB instance;
    private final SharedPreferences preferences;

    private TinyDB(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static TinyDB getInstance(Context context) {

        if (instance == null) {

            instance = new TinyDB(context);
        }

        return instance;
    }
    // Getters

    public int getInt(String key, int i) {
        return preferences.getInt(key, i);
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    public ArrayList<String> getListString(String key, ArrayList<String> def) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    public ArrayList<Integer> getListInt(String key, ArrayList<Integer> def) {

        String[] ints = TextUtils.split(preferences.getString(key, ""), "‚‗‚");

        ArrayList<Integer> integers = new ArrayList<Integer>();

        for (String s : ints) {

            integers.add(Integer.valueOf(s));
        }

        return integers;
    }

    public int[] getArrayInt(String key, int[] def) {

        String[] ints = TextUtils.split(preferences.getString(key, ""), "‚‗‚");

        int[] integers = new int[ints.length];

        if (ints.length>0) {
            int count = 0;

            for (String s : ints) {

                integers[count] = Integer.valueOf(s);
                count++;
            }
        }
        return integers;
    }

    public boolean getBoolean(String key, boolean bol) {
        return preferences.getBoolean(key, bol);
    }

    // Put methods

    public void putListInt(String key, ArrayList<Integer> intList) {

        String[] ints = new String[intList.size()];
        int count = 0;

        for (int i: intList) {

            ints[count] = Integer.toString(i);
            count++;
        }

        preferences.edit().putString(key, TextUtils.join("‚‗‚", ints)).apply();
    }

    public void putInt(String key, int value) {

        preferences.edit().putInt(key, value).apply();
    }

    public void putFloat(String key, float value) {

        preferences.edit().putFloat(key, value).apply();
    }

    public void putString(String key, String value) {

        preferences.edit().putString(key, value).apply();
    }

    public void putListString(String key, ArrayList<String> stringList) {

if (stringList != null) {
    String[] myStringList = stringList.toArray(new String[stringList.size()]);
    preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
}
    }

    public void putBoolean(String key, boolean value) {

        preferences.edit().putBoolean(key, value).apply();
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

}