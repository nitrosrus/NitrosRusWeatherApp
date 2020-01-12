package com.example.nitrosrusweatherapp;

import android.app.Activity;
import android.content.SharedPreferences;

public class CitySaver {
    private static final String KEY = "city";
    private static final String CITY = "Moscow";
    private SharedPreferences userPreferences;

    CitySaver(Activity activity) {
        userPreferences = activity.getPreferences(Activity.MODE_PRIVATE);

    }

    protected String getCity() {
        return userPreferences.getString(KEY, CITY);
    }

    void setCity(String city) {
        userPreferences.edit().putString(KEY, city).apply();
    }
}