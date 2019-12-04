package com.example.tourmate_final.repository;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.Helper.Retrofitclient;
import com.example.tourmate_final.Weatherservice.Service_api;
import com.example.tourmate_final.current_weather.CurrentWeatherResponse;
import com.example.tourmate_final.current_weather.Sys;
import com.example.tourmate_final.forecast_weather_pojos.ForecastWeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather_repos {
    private static final String TAG = Weather_repos.class.getSimpleName();

    public MutableLiveData<CurrentWeatherResponse> responsLD = new MutableLiveData<>();
    public MutableLiveData<ForecastWeatherResponse> forecastLD = new MutableLiveData<>();

    public MutableLiveData<CurrentWeatherResponse> getcurrentweahter(Location location, String unit, String apikey) {
        Service_api Weatherapi = Retrofitclient.getclient().create(Service_api.class);
        String endurl = String.format("data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
                location.getLatitude(), location.getLongitude(), unit, apikey);
        Weatherapi.getcurrentweahter(endurl).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful()) {
                    CurrentWeatherResponse currentWeatherResponse = response.body();
                    responsLD.postValue(currentWeatherResponse);
                 //   Log.e(TAG, "onResponse: " + currentWeatherResponse.getName());

                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
              //  Log.e(TAG, "onFailure: " + t.getLocalizedMessage());


            }
        });
        return responsLD;
    }

    public MutableLiveData<ForecastWeatherResponse> getforecastweather(Location location, String unit, String apikey) {
        Service_api service_api=Retrofitclient.getclient().create(Service_api.class);
        String endurl= String.format("data/2.5/forecast/daily?lat=%f&lon=%f&cnt=7&metrics=%s&appid=%s",location.getLatitude(),location.getLongitude(),unit,apikey);
        service_api.getforecastweather(endurl).enqueue(new Callback<ForecastWeatherResponse>() {
            @Override
            public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
                if (response.isSuccessful()){
                    ForecastWeatherResponse response1=response.body();
                    forecastLD.postValue(response1);
                }
            }

            @Override
            public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {

            }
        });
        return forecastLD;
    }
}
