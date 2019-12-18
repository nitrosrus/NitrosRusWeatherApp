package com.example.nitrosrusweatherapp;

import android.content.Context;
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

import com.example.nitrosrusweatherapp.model.Main;
import com.example.nitrosrusweatherapp.model.WheatherModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;


public class FragmentWeatherActivity extends Fragment {
    private static final String LOG_TAG = "FragmentWeatherActivity";
    private static final String FONT_FILE = "font/weather.ttf";
    private final Handler handler = new Handler();

    private Typeface weatherFont;

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

    public void updateWeatherData(final String city) {

        new Thread() {
            @Override
            public void run() {
                final WheatherModel model = WeatherDownload.getJSONData(city);
                if (model == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(getView(), city + " " + getString(R.string.not_found), Snackbar.LENGTH_LONG).show();
                            //Toast.makeText(getActivity(), city + " " + getActivity().getString(R.string.not_found), Toast.LENGTH_LONG).show();

                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renderWeather(model);
                        }
                    });
                }
            }
        }.start();

    }

    private void renderWeather(WheatherModel model) {
        tvCitiName.setText(model.getName());
        tvWindSpeed.setText(model.getWind().getSpeed().toString() + " M/C");
        tvTemperature.setText(model.getMain().getTemp().toString() + " C");
        tvPressure.setText(model.getMain().getPressure().toString() + "мм.рт.с ");
        tvHumidity.setText(model.getMain().getHumidity().toString() + " %");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void changeCity(String city) {
        updateWeatherData(city);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
