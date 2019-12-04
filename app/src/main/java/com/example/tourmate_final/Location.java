package com.example.tourmate_final;


import android.location.Address;
import android.location.Geocoder;
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
public class Location extends Fragment {

   private TextView locationTV;
   private LocationViewmodel locationViewmodel;
    public Location() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        locationViewmodel= ViewModelProviders.of(getActivity()).get(LocationViewmodel.class);
        locationViewmodel.getcurrentlocation();
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationTV=view.findViewById(R.id.locationTVorg);
      locationViewmodel.locationLd.observe(this, new Observer<android.location.Location>() {
          @Override
          public void onChanged(android.location.Location location) {
              try {
                  latlongtoaddress(location);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      });
    }

    private void latlongtoaddress(android.location.Location location) throws IOException {
        Geocoder geocoder=new Geocoder(getActivity());
        List<Address>addresslist=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        String address=addresslist.get(0).getAddressLine(0);
        locationTV.setText(address);

    }
}
