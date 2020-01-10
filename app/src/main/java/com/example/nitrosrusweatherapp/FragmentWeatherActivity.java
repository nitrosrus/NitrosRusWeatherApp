package com.example.nitrosrusweatherapp;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nitrosrusweatherapp.model.WeatherModel;
import com.google.android.material.snackbar.Snackbar;


public class FragmentWeatherActivity extends Fragment {
    private static final String LOG_TAG = "FragmentWeatherActivity";
    private static final String FONT_FILE = "font/weather.ttf";
    private final Handler handler = new Handler();
    private static final String KEY = "94bde3146fcb9c9591279a0cff298631";

    private Fragment fragment;
    private Typeface weatherFont;

    public TextView tvCityName;
    private TextView tvLastUpdate;
    private TextView tvCurrentTime;
    private TextView tvCurrentDate;
    private TextView tvWeatherIcon;
    private TextView tvCurentDetail;
    private TextView tvWindSpeed;
    private TextView tvHumidity;
    private TextView tvTemperature;
    private TextView tvPressure;
    private boolean result;
    private Button btnUpdate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWeather activityWeather = (ActivityWeather) getActivity();
        // weatherFont = Typeface.createFromAsset(activityWeather.getAssets(), FONT_FILE);
        updateWeatherData(new CitySaver(activityWeather).getCity());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather_activity, container, false);
        return rootView;
    }

    public void updateWeatherData(final String city) {

        new Thread() {
            @Override
            public void run() {

                try {

                    WeatherModel model = WeatherDownload.responseRetrofit(city);

                    renderWeather(model);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void renderWeather(WeatherModel model) {

        Snackbar.make(getView(), "Error this", Snackbar.LENGTH_LONG).show();
        ((TextView) getActivity().findViewById(R.id.tv_city_name)).setText(model.getName());
//        tvWindSpeed.setText(model.getWind().getSpeed().toString() + " M/C");
//        tvTemperature.setText(model.getMain().getTemp().toString() + " C");
//        tvPressure.setText(model.getMain().getPressure().toString() + "мм.рт.с ");
//        tvHumidity.setText(model.getMain().getHumidity().toString() + " %");

    }

    public void changeCity(String city) {
        updateWeatherData(city);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
