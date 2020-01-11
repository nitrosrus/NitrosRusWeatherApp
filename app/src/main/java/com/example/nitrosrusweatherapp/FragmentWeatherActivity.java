package com.example.nitrosrusweatherapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitrosrusweatherapp.model.WeatherModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;


public class FragmentWeatherActivity extends Fragment {
    private static final String LOG_TAG = "FragmentWeatherActivity";
    private static final String FONT_FILE = "font/weather.ttf";
    private final Handler handler = new Handler();
    private static final String KEY = "94bde3146fcb9c9591279a0cff298631";
    private Fragment fragment;
    private Typeface weatherFont;

    private Button btnUpdate;

    private TextView tvCitiName;
    private TextView tvLastUpdate;
    private TextView tvCurrentTime;
    private TextView tvCurrentDate;
    private TextView tvWeatherIcon;
    private TextView tvCurentDetail;
    private TextView tvWindSpeed;
    private TextView tvHumidity;
    private TextView tvTemperature;
    private TextView tvPressure;
    CitySaver citySaver;
    private Timer timer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWeather activityWeather = (ActivityWeather) getActivity();
        // weatherFont = Typeface.createFromAsset(activityWeather.getAssets(), FONT_FILE);
        citySaver = new CitySaver(activityWeather);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather_activity, container, false);
        tvCitiName = (TextView) rootView.findViewById(R.id.tv_city_name);
        tvLastUpdate = (TextView) rootView.findViewById(R.id.tv_last_update);
        tvCurrentTime = (TextView) rootView.findViewById(R.id.tv_current_time);
        tvCurrentDate = (TextView) rootView.findViewById(R.id.tv_current_date);
        tvWeatherIcon = (TextView) rootView.findViewById(R.id.tv_icon_current_weather);
        tvCurentDetail = (TextView) rootView.findViewById(R.id.tv_weather_detailed);
        tvWindSpeed = (TextView) rootView.findViewById(R.id.tv_wind_speed);
        tvHumidity = (TextView) rootView.findViewById(R.id.tv_current_humidity);
        tvTemperature = (TextView) rootView.findViewById(R.id.tv_current_temp);
        tvPressure = (TextView) rootView.findViewById(R.id.tv_current_pressure);

        return rootView;
    }

    public void updateWeatherData() {

    }

    public void renderWeather(WeatherModel model) {
        tvCitiName.setText(model.getName().toString());
        tvLastUpdate.setText("fatal");
//        Snackbar.make(getView(), "Error this", Snackbar.LENGTH_LONG).show();
//        ((TextView) getActivity().findViewById(R.id.tv_city_name)).setText(model.getName());
//        tvWindSpeed.setText(model.getWind().getSpeed().toString() + " M/C");
//        tvTemperature.setText(model.getMain().getTemp().toString() + " C");
//        tvPressure.setText(model.getMain().getPressure().toString() + "мм.рт.с ");
//        tvHumidity.setText(model.getMain().getHumidity().toString() + " %");

    }


    @Override
    public void onResume() {
        super.onResume();
        runPotok();
    }

    void changeCity(String city) {
    }


    private void runPotok() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               // WeatherDownload weatherDownload = null;

                try {

                   // WeatherModel model = ;


                    renderWeather(WeatherDownload.responseRetrofit(citySaver.getCity()));
                    Toast.makeText(getContext(), "дошел", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).run();


    }
}
