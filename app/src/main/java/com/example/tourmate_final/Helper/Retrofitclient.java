package com.example.tourmate_final.Helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitclient {
    public  static final String BASE_URL="http://api.openweathermap.org/";


   public  static Retrofit getclient(){
        return new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }
}
