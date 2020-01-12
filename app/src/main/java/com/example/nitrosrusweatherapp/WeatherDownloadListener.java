package com.example.nitrosrusweatherapp;

import com.example.nitrosrusweatherapp.model.WeatherModel;

public interface WeatherDownloadListener {
    void updateWeather(WeatherModel model);
}
