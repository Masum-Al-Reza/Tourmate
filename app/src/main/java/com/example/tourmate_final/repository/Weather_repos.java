package com.example.tourmate_final.repository;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.Helper.Retrofitclient;
import com.example.tourmate_final.Weatherservice.Service_api;
import com.example.tourmate_final.current_weather.CurrentWeatherResponse;

import java.text.Format;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Weather_repos {
    public MutableLiveData<CurrentWeatherResponse>responsLD=new MutableLiveData<>();
    public  MutableLiveData<CurrentWeatherResponse> getcurrentweahter(Location location, String unit, String apikey) {
         Service_api Weatherapi = Retrofitclient.getclient().create(Service_api.class);
         String endurl = String.format("data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
                 location.getLatitude(),location.getLongitude(),unit,apikey);
         Weatherapi.getcurrentweahter(endurl).enqueue(new Callback<CurrentWeatherResponse>() {
             @Override
             public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                 if (response.isSuccessful()){
                     CurrentWeatherResponse currentWeatherResponse=response.body();
                     responsLD.postValue(currentWeatherResponse);
                 }
             }

             @Override
             public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

             }
         });
         return responsLD;
    }
}
