package com.example.nitrosrusweatherapp.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.nitrosrusweatherapp.R;


public class ActivityWeather extends AppCompatActivity {

    private FragmentWeatherActivity fragmentWeatherActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragmentWeatherActivity = new FragmentWeatherActivity();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, fragmentWeatherActivity);
        fragmentTransaction.commit();

    }

}
