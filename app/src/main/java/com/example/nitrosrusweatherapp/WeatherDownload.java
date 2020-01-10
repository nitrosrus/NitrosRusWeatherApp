package com.example.nitrosrusweatherapp;

import android.util.Log;

import com.example.nitrosrusweatherapp.model.WeatherModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherDownload {
    private static final String KEY = "94bde3146fcb9c9591279a0cff298631";
    private Retrofit retrofit;
    public static OpenWeather openWeather;

    public static WeatherModel getJSONData(String city) {
        WeatherModel model = null;
        Gson gson = new Gson();
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        String tempString;
        String key = "94bde3146fcb9c9591279a0cff298631";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + ",ru&units=metric&appid=" + key;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);

            //connection.addRequestProperty(KEY, context.getString(R.string.open_weather_map_key));
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            model = gson.fromJson(reader, WeatherModel.class);
            reader.close();

        } catch (Exception e) {

        } finally {
            if (connection != null) {

                connection.disconnect();
            }

            return model;
        }


    }


    public WeatherDownload() {
        retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        openWeather = retrofit.create(OpenWeather.class);


    }

    public interface OpenWeather {
        @GET("data/2.5/weather")
        Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String keyApi);


    }

    public static WeatherModel responseRetrofit(String city) throws Exception {

        Call<WeatherModel> call = openWeather.getWeather(city + ",ru", "94bde3146fcb9c9591279a0cff298631");
        Response<WeatherModel> response = call.execute();
        if (response.isSuccessful())
            return response.body();
        else
            Log.d("ERROR","tut");
            throw new Exception(response.errorBody().string(), null);
    }


}
