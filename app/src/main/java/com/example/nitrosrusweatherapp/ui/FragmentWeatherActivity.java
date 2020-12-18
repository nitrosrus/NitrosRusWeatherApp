package com.example.nitrosrusweatherapp.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nitrosrusweatherapp.CitySaver;
import com.example.nitrosrusweatherapp.IconUtils;
import com.example.nitrosrusweatherapp.R;
import com.example.nitrosrusweatherapp.WeatherDownload;
import com.example.nitrosrusweatherapp.model.WeatherModel;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FragmentWeatherActivity extends Fragment {

    private static final String BUTTON_KEY_OK = "OK";

    private TextView tvCityName;
    private TextView tvCurrentDate;
    private WeatherIconView tvWeatherIcon;
    private TextView tvCurrentDetail;
    private TextView tvWindSpeed;
    private TextView tvHumidity;
    private TextView tvTemperature;
    private TextView tvPressure;
    CitySaver citySaver;
    private IconUtils iconUtils;
    WeatherDownload weatherDownload;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iconUtils = new IconUtils();
        ActivityWeather activityWeather = (ActivityWeather) getActivity();
        citySaver = CitySaver.getInstance(activityWeather);
        weatherDownload = WeatherDownload.getInstance(citySaver);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather_activity, container, false);
        tvCityName = rootView.findViewById(R.id.tv_city_name);
        tvCityName.setOnClickListener((v) -> {
            showInputDialog();
        });
        tvCurrentDate = rootView.findViewById(R.id.tv_current_date);
        tvWeatherIcon = rootView.findViewById(R.id.tv_icon_current_weather);
        tvCurrentDetail = rootView.findViewById(R.id.tv_weather_detailed);
        tvWindSpeed = rootView.findViewById(R.id.tv_wind_speed);
        tvHumidity = rootView.findViewById(R.id.tv_current_humidity);
        tvTemperature = rootView.findViewById(R.id.tv_current_temp);
        tvPressure = rootView.findViewById(R.id.tv_current_pressure);
        tvWeatherIcon.setIconSize(150);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        weatherDownload.liveWeather.observe(this, this::updateWeather);
        weatherDownload.liveMessage.observe(this, this::toastMaker);
    }


    private void updateWeather(WeatherModel model) {
        tvCityName.setText(model.getName());
        tvWindSpeed.setText(String.format("%s M/C", Math.round(model.getWind().getSpeed())));
        tvTemperature.setText(String.format("%s C°", Math.round(model.getMain().getTemp())));
        tvPressure.setText(String.format("%s mm Hg", model.getMain().getPressure().toString()));
        tvHumidity.setText(String.format("%s %%", model.getMain().getHumidity().toString()));
        tvCurrentDate.setText(getDate());
        tvCurrentDetail.setText(model.getWeather().get(0).getDescription());
        tvWeatherIcon.setIconResource(getString(iconUtils.getResByIdCode(model.getWeather().get(0).getId())));

    }

    private String getDate() {
        String date;
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        date = dateFormat.format(currentDate);
        return date;
    }


    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.action_change_city));
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton(BUTTON_KEY_OK, (dialog, which) -> changeCity(input.getText().toString()));
        builder.show();
    }


    private void changeCity(String city) {
        if (!city.trim().isEmpty()) {
            weatherDownload.updateData(city);
        } else {
            toastMaker("Empty value");
        }
    }

    private void toastMaker(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

}
