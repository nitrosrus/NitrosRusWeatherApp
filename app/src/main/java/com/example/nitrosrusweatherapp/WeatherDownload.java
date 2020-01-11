package com.example.nitrosrusweatherapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.nitrosrusweatherapp.model.WeatherModel;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class WeatherDownload {

    private static final String KEY = "94bde3146fcb9c9591279a0cff298631";
    private static OpenWeather openWeather;
    private Retrofit retrofit;


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

        Call<WeatherModel> call = openWeather.getWeather(city + ",ru", KEY);
        Response<WeatherModel> response = call.execute();

        if (response.isSuccessful())
            return response.body();

        else
            return null;
           // throw new Exception(response.body().getName(), null);

    }


}
