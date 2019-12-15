package com.example.nitrosrusweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ActivityWeather extends AppCompatActivity {
    private static final String BUTTON_KEY_OK = "OK";
    private static String WEATHER_KEY_TAG = "123hgfyweubwe1ui2y3b1i2hbk12h4gf";
    private CitySaver citySaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        citySaver = new CitySaver(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_for_fragments, new FragmentWeatherActivity(), WEATHER_KEY_TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_city) {
            showInputDialog();
            return true;
        }
        return false;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.change_city_dialog));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton(BUTTON_KEY_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());

            }
        });
        builder.show();
    }

    private void changeCity(String city) {
        FragmentWeatherActivity fragmentWeatherActivity = (FragmentWeatherActivity) getSupportFragmentManager().findFragmentByTag(WEATHER_KEY_TAG);
        fragmentWeatherActivity.changeCity(city);
            citySaver.setCity(city);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
