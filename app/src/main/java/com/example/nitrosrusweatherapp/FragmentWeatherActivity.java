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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentWeatherActivity extends Fragment implements WeatherDownloadListener {
    @BindView(R.id.tv_last_update)
    TextView tvLastUpdate;
    @BindView(R.id.tv_city_name)
    TextView tvCitiName;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.tv_current_date)
    TextView tvCurrentDate;
    @BindView(R.id.tv_icon_current_weather)
    TextView tvWeatherIcon;
    @BindView(R.id.tv_weather_detailed)
    TextView tvCurentDetail;
    @BindView(R.id.tv_wind_speed)
    TextView tvWindSpeed;
    @BindView(R.id.tv_current_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_current_temp)
    TextView tvTemperature;
    @BindView(R.id.tv_current_pressure)
    TextView tvPressure;


    private static final String LOG_TAG = "FragmentWeatherActivity";
    private static final String FONT_FILE = "font/weather.ttf";
    private final Handler handler = new Handler();
    private static final String KEY = "94bde3146fcb9c9591279a0cff298631";
    private Fragment fragment;
    private Typeface weatherFont;

    private Button btnUpdate;

    private Timer timer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // weatherFont = Typeface.createFromAsset(activityWeather.getAssets(), FONT_FILE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather_activity, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onResume() {
        WeatherDownload.getInstance().addListener(this);
        super.onResume();

    }

    void changeCity(String city) {
        WeatherDownload.getInstance().changeCity(city);
    }


    @Override
    public void onPause() {
        WeatherDownload.getInstance().removeListener(this);
        super.onPause();
    }

    @Override
    public void updateWeather(WeatherModel model) {

        tvCitiName.setText(model.getName().toString());
        tvWindSpeed.setText(model.getWind().getSpeed().toString() + " M/C");
        tvTemperature.setText(Math.round(model.getMain().getTemp()) + " C");
        tvPressure.setText(model.getMain().getPressure().toString() + "мм.рт.с ");
        tvHumidity.setText(model.getMain().getHumidity().toString() + " %");
        tvCurrentDate.setText(getDate());
        Snackbar.make(getView(), "Update this", Snackbar.LENGTH_LONG).show();
    }

    private String getDate() {
        String date;
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        date = dateFormat.format(currentDate);
        return date;
    }

}
