package com.example.tourmate_final.service_Api;

import com.example.tourmate_final.current_weather.CurrentWeatherResponse;
import com.example.tourmate_final.forecast_weather_pojos.ForecastWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Service_api {
  @GET
  Call<CurrentWeatherResponse>getcurrentweahter(@Url String endurl);
  @GET
  Call<ForecastWeatherResponse>getforecastweather(@Url String endurl);

}
