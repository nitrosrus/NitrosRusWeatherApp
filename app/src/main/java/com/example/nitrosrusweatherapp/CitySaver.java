package com.example.nitrosrusweatherapp;

import android.app.Activity;
import android.content.SharedPreferences;

public class CitySaver {
    private static final String KEY_CITY = "city";
    String CITY = "Moscow";
    private final SharedPreferences userPreferences;
    private static CitySaver instance = null;

    public static CitySaver getInstance(Activity activity) {
        return instance = instance == null ? new CitySaver(activity) : instance;
    }

    public CitySaver(Activity activity) {
        userPreferences = activity.getPreferences(Activity.MODE_PRIVATE);

    }

    public String getCity() {
        return userPreferences.getString(KEY_CITY, CITY);
    }

    public void setCity(String city) {
        userPreferences.edit().putString(KEY_CITY, city).apply();
    }
}