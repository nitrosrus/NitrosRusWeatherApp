package com.example.nitrosrusweatherapp;

import com.example.nitrosrusweatherapp.model.WheatherModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherDownload {
    private static final String KEY = "94bde3146fcb9c9591279a0cff298631";
    private static final String RESPONSE = "cod";
    private static final int GOOD_RESPONSE_COD = 200;


    public static WheatherModel getJSONData(String city) {
        WheatherModel model = null;
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

            model = gson.fromJson(reader, WheatherModel.class);
            reader.close();

        } catch (Exception e) {

        } finally {
            if (connection != null) {

                connection.disconnect();
            }

            return model;
        }


    }

    public static OpenWeather openWeather;

    public static void initFit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();
        openWeather = (OpenWeather) retrofit.create(WheatherModel.class);


    }


    public void requestFit(String city, String local, String keyApi) {
        local = "ru";

        openWeather.loadWeather(city, local, keyApi).enqueue(new Callback<WheatherModel>() {
            @Override
            public void onResponse(Call<WheatherModel> call, Response<WheatherModel> response) {
                if (response.body() != null) {
                    getModel(response.body());
                }
            }
            @Override
            public void onFailure(Call<WheatherModel> call, Throwable t) {

            }
        });


    }


    public interface OpenWeather {
        @GET("data/2.5/weather")
        Call<WheatherModel> loadWeather(@Query("q") String cityCountry, @Query("ru") String local, @Query("appid") String keyApi);

        Call<WheatherModel> loadWeatherCord(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String keyApi);

    }

    public static WheatherModel getModel(WheatherModel model) {
        return model;
    }

}
