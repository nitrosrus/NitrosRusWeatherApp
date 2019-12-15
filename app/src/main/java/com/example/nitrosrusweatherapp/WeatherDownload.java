package com.example.nitrosrusweatherapp;

import com.example.nitrosrusweatherapp.model.WheatherModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDownload {
    // private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=moscow,ru&appid=";
    //  private static final String KEY = "x-api-key";
    // private static final String RESPONSE = "cod";
    //  private static final int GOOD_RESPONSE_COD = 200;

    private static final String NEW_LINE = "\n";


    public static WheatherModel getJSONData(String city) {
        WheatherModel model = null;
        Gson gson = new Gson();
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        String tempString;
        String key="94bde3146fcb9c9591279a0cff298631";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q="+city+",ru&units=metric&appid="+key ;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);

            //connection.addRequestProperty(KEY, context.getString(R.string.open_weather_map_key));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            while ((tempString = reader.readLine()) != null) {
//                stringBuilder.append(tempString).append(NEW_LINE);
//            }


//            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//            if (jsonObject.getInt(RESPONSE) != GOOD_RESPONSE_COD) {
//                return null;
//            }
            //return jsonObject;
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


}
