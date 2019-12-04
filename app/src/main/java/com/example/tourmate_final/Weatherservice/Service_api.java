package com.example.tourmate_final.Weatherservice;

import com.example.tourmate_final.current_weather.CurrentWeatherResponse;
import com.example.tourmate_final.repository.Weather_repos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Service_api {
  @GET
  Call<CurrentWeatherResponse>getcurrentweahter(@Url String endurl);

}
