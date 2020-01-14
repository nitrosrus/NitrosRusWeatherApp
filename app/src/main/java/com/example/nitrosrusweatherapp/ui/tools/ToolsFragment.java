package com.example.nitrosrusweatherapp.ui.tools;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.nitrosrusweatherapp.ActivityWeather;
import com.example.nitrosrusweatherapp.R;

import java.util.List;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private ActivityWeather activityWeather;
    private Sensor sensorHumidity;
    private Sensor sensorTemperature;
    private TextView humidity;
    private TextView temp;
    private SensorEventListener listener;

    private Activity activity;
    private FragmentManager fragmentManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel = ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }

        });

        humidity = (TextView) root.findViewById(R.id.real_humidity);

        return root;
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);

    }

    @Override
    public void onResume() {

        super.onResume();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            Log.i("SENSORS", sensor.getName());
        }
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                humidity.setText(event.values.toString());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(listener, sensorHumidity, 100);

    }


}