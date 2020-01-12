package com.example.nitrosrusweatherapp.ui.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class ToolsViewModel extends ViewModel   {

    private MutableLiveData<String> mText;


    public ToolsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("если датчики работают то вы увидите рельную температуру и влажность");
    }


    public LiveData<String> getText() {
        return mText;
    }




}