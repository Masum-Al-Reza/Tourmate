package com.example.tourmate_final;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tourmate_final.viewmodel.LocationViewmodel;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Weather extends Fragment {
    private TextView currentlocationTV,locationanametv;
    private LocationViewmodel locationViewmodel;



    public Weather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        locationViewmodel= ViewModelProviders.of(getActivity()).get(LocationViewmodel.class);
        locationViewmodel.getcurrentlocation();
        return inflater.inflate(R.layout.fragment_weather, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentlocationTV=view.findViewById(R.id.locationTV);
        locationanametv=view.findViewById(R.id.locationnameTV);
        locationViewmodel.locationLd.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                currentlocationTV.setText(location.getLatitude()+","+location.getLongitude());
                try {
                    latlongtoaddressname(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void latlongtoaddressname(Location location) throws IOException {
        Geocoder geocoder=new Geocoder(getActivity());
        List<Address>adresslist=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        String address=adresslist.get(0).getAddressLine(0);
        locationanametv.setText(address);


    }

}
