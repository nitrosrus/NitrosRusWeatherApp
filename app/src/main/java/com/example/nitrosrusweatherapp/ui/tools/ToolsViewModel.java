package com.example.nitrosrusweatherapp.ui.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nitrosrusweatherapp.model.WeatherModel;

import java.util.Timer;
import java.util.TimerTask;


public class ToolsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Timer timer;

    public ToolsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("если датчики работают то вы увидите рельную температуру и влажность");
    }


    public LiveData<String> getText() {
        return mText;
    }



}