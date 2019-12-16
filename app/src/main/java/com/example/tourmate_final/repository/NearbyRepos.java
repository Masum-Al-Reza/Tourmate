package com.example.tourmate_final.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.Helper.Retrofitclient;
import com.example.tourmate_final.Nearbyplace.NearbyplacesBody;
import com.example.tourmate_final.service_Api.Location_service_api;
import com.example.tourmate_final.service_Api.Service_api;

import java.text.Format;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyRepos {
    public MutableLiveData<NearbyplacesBody> responsLD = new MutableLiveData<>();
    public MutableLiveData<NearbyplacesBody> nearbyresponse(String endurl) {
        Location_service_api location_service_api= Retrofitclient.Nearbygetclient().create(Location_service_api.class);

        location_service_api.getcurrentweahter(endurl).enqueue(new Callback<NearbyplacesBody>() {
            @Override
            public void onResponse(Call<NearbyplacesBody> call, Response<NearbyplacesBody> response) {
             responsLD.postValue(response.body());
            }

            @Override
            public void onFailure(Call<NearbyplacesBody> call, Throwable t) {

            }
        });
        return responsLD;
    }


}
