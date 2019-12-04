package com.example.tourmate_final.viewmodel;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

public class LocationViewmodel extends AndroidViewModel {
    private Context context;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public MutableLiveData<Location>locationLd=new MutableLiveData<>();

    public LocationViewmodel(@NonNull Application application) {
        super(application);
        this.context=application;
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(context);
    }
    public  void  getcurrentlocation(){
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location==null){
                    return;

                }
              locationLd.postValue(location);
            //    Toast.makeText(context, "found", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
