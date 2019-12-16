package com.example.tourmate_final.Helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitclient {
    public  static final String BASE_URL="http://api.openweathermap.org/";
    public  static final String Nearbyplace_BASE_URL="https://maps.googleapis.com/maps/api/";


   public  static Retrofit weather_getcliet(){
        return new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }
    public  static Retrofit Nearbygetclient(){
        return new Retrofit.Builder().baseUrl(Nearbyplace_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }
}
