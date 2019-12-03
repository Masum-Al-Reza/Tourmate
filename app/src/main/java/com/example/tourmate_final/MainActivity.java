package com.example.tourmate_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity   {
    private LocationViewmodel locationViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationViewmodel= ViewModelProviders.of(this).get(LocationViewmodel.class);
        isLocationPermissionGranted();
    }
    private boolean isLocationPermissionGranted(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
            locationViewmodel.getcurrentlocation();
        }
    }
}
