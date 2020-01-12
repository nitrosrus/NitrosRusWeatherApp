package com.example.nitrosrusweatherapp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.nitrosrusweatherapp.model.WeatherModel;


import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class WeatherDownload {

    private Set<WeatherDownloadListener> listeners;
    private static final String KEY = "94bde3146fcb9c9591279a0cff298631";
    private static OpenWeather openWeather;
    private Retrofit retrofit;
    private static WeatherDownload instance = null;
    private Timer timer = null;
    public Handler handler = new Handler();


    public static WeatherDownload getInstance() {
        instance = instance == null ? new WeatherDownload() : instance;
        return instance;
    }

    public WeatherDownload() {
        listeners = new HashSet<>();
        retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
               .addConverterFactory(ScalarsConverterFactory.create()).build();openWeather = retrofit.create(OpenWeather.class);
        updateData();

    }
    public interface OpenWeather {
        @GET("data/2.5/weather")
        Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String keyApi);
    }

    private static WeatherModel responseRetrofit(String city) throws Exception {

        Call<WeatherModel> call = openWeather.getWeather(city + ",ru","94bde3146fcb9c9591279a0cff298631");
        Response<WeatherModel> response = call.execute();

        if (response.isSuccessful())
            return response.body();

        else
            throw new Exception(response.errorBody().string(), null);

    }



    public void updateData() {


        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    final WeatherModel model = responseRetrofit("murino");
                    if (model == null) return;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (WeatherDownloadListener listener : listeners) {
                                listener.updateWeather(model);

                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000, 10000);

    }

    public void stop() {
        if (timer != null) timer.cancel();
        listeners.clear();
    }


    public void addListener(WeatherDownloadListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(WeatherDownloadListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

}
