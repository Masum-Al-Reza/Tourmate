package com.example.tourmate_final.service_Api;

import com.example.tourmate_final.Nearbyplace.NearbyplacesBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Location_service_api {
    @GET
    Call<NearbyplacesBody> getcurrentweahter(@Url String endurl);
}
