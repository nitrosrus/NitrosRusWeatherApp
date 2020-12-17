package com.example.nitrosrusweatherapp;

import androidx.lifecycle.MutableLiveData;

import com.example.nitrosrusweatherapp.model.WeatherModel;

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

    private static final String KEY = "";
    private static OpenWeather openWeather;
    private static WeatherDownload instance = null;
    private Timer timer = null;
    private CitySaver citySaver;
    public MutableLiveData<WeatherModel> liveWeather = new MutableLiveData<>();
    public MutableLiveData<String> liveMessage = new MutableLiveData<>();
    private String cachedCity;


    public static WeatherDownload getInstance(CitySaver citySaver) {
        instance = instance == null ? new WeatherDownload(citySaver) : instance;
        return instance;
    }

    public WeatherDownload(CitySaver citySaver) {
        this.citySaver = citySaver;
        openWeather = getOpenWeather();
        cachedCity = citySaver.getCity();
        updateData(cachedCity);
    }

    private OpenWeather getOpenWeather() {
        return new Retrofit.Builder().baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build().create(OpenWeather.class);
    }

    private WeatherModel responseRetrofit(String city) throws Exception {

        Call<WeatherModel> call = openWeather.getWeather(city, "metric", KEY);
        Response<WeatherModel> response = call.execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody().string(), null);
        }
    }

    private void saveCity(String city) {
        citySaver.setCity(city);
    }

    public void updateData(String city) {
        if (timer != null) {
            timer.cancel();
            saveCity(city);
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    final WeatherModel model = responseRetrofit(city);
                    if (model == null) return;
                    liveWeather.postValue(model);
                } catch (Exception e) {
                    if (e.getLocalizedMessage().contains("city not found")) {
                        liveMessage.postValue("city not found");
                        updateData(cachedCity);
                    }
                    e.printStackTrace();
                }
            }
        }, 2000, 600000);

    }


}

interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherModel> getWeather(@Query("q") String q, @Query("units") String metric, @Query("appid") String keyApi);
}