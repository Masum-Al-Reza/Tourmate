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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.current_weather.CurrentWeatherResponse;
import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.example.tourmate_final.viewmodel.Weatherviewmodel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Weather_fragment extends Fragment {
    private TextView currentlocationTV,locationanametv,imagetv,latlongtv;
    private LocationViewmodel locationViewmodel;
    private Weatherviewmodel weatherviewmodel;
    private String unit="matric";



    public Weather_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        locationViewmodel= ViewModelProviders.of(getActivity()).get(LocationViewmodel.class);

        weatherviewmodel= ViewModelProviders.of(this).get(Weatherviewmodel.class);
        locationViewmodel.getcurrentlocation();

        return inflater.inflate(R.layout.fragment_weather, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //currentlocationTV=view.findViewById(R.id.locationTV);
        latlongtv=view.findViewById(R.id.latlongtv);
//        imagetv=view.findViewById(R.id.weathericon);

        locationanametv=view.findViewById(R.id.locationnameTV);
        locationViewmodel.locationLd.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
               // currentlocationTV.setText(location.getLatitude()+","+location.getLongitude());
                initinitializestate(location);
              /*  try {
                    latlongtoaddressname(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });
       /* weatherviewmodel.responseLD.observe(this, new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse response) {
                double temp=response.getMain().getTemp();
                int date=response.getDt();
                String city=response.getName();
                String icon=response.getWeather().get(0).getIcon();

                Picasso.get().load(Eventutils.IMAGE_ICON_PREFIX +icon+".png");
                latlongtv.setText(temp+"\n"+date+"\n"+city);

            }
        });*/
    }

    private void initinitializestate(Location location) {
        String Apikey=getString(R.string.weather_api_key);
        weatherviewmodel.getcurrentweather(location,unit,Apikey).observe(this, new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse response) {
                double temp=response.getMain().getTemp();
                int date=response.getDt();
                String city=response.getName();
                String icon=response.getWeather().get(0).getIcon();
                Toast.makeText(getActivity(), "founds"+response, Toast.LENGTH_SHORT).show();

                Picasso.get().load(Eventutils.IMAGE_ICON_PREFIX +icon+".png");
                latlongtv.setText(temp+"\n"+date+"\n"+city);

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
