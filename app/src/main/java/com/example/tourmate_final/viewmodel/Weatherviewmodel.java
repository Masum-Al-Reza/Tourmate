package com.example.tourmate_final.viewmodel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate_final.current_weather.CurrentWeatherResponse;
import com.example.tourmate_final.repository.Weather_repos;

public class Weatherviewmodel extends ViewModel {
    private Weather_repos weather_repos;

    public  Weatherviewmodel(){
        weather_repos=new Weather_repos();
    }
      public MutableLiveData<CurrentWeatherResponse> getcurrentweather(Location location, String unit,
                                                                       String apikey){
        return  weather_repos.getcurrentweahter(location,unit,apikey);
    }
}
