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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.adapter.Forecast_adapter;
import com.example.tourmate_final.current_weather.CurrentWeatherResponse;
import com.example.tourmate_final.forecast_weather_pojos.ForecastWeatherResponse;
import com.example.tourmate_final.forecast_weather_pojos.Forecast_List;
import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.example.tourmate_final.viewmodel.Weatherviewmodel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Weather_fragment extends Fragment {
    private static final String TAG = Weather_fragment.class.getSimpleName();

    private TextView currentlocationTV,locationanametv,latlongtv,forecastlatlongTV;
    private ImageView imagetv;
    private LocationViewmodel locationViewmodel;
    private Weatherviewmodel weatherviewmodel;
    private RecyclerView recyclerView;
    private Forecast_adapter forecast_adapter;
    private String unit="metric";



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
        imagetv=view.findViewById(R.id.weathericon);
        recyclerView=view.findViewById(R.id.forecastRV);
        forecastlatlongTV=view.findViewById(R.id.forecastlatlongtv);
        locationanametv=view.findViewById(R.id.locationnameTV);
        locationViewmodel.locationLd.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
               // currentlocationTV.setText(location.getLatitude()+","+location.getLongitude());
                initinitializestate(location);
                initializeforecaststate(location);
               try {
                    latlongtoaddressname(location);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initializeforecaststate(Location location) {
        String Apikey=getString(R.string.forecast_weather_api_key);
        weatherviewmodel.getforecastweather(location,unit,Apikey).observe(this, new Observer<ForecastWeatherResponse>() {
            @Override
            public void onChanged(ForecastWeatherResponse response) {
                List<Forecast_List>forecast_lists=response.getList();
                Log.e(TAG, "onChanged: "+forecast_lists.size());

                forecast_adapter=new Forecast_adapter(getContext(),forecast_lists);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(forecast_adapter);


            }
        });
    }

    private void initinitializestate(Location location) {
        String Apikey=getString(R.string.weather_api_key);
        weatherviewmodel.getcurrentweather(location,unit,Apikey).observe(this, new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse response) {
                double temp=response.getMain().getTemp();
                String date=Eventutils.getformatteddate(response.getDt());
                String city=response.getName();
                String icon=response.getWeather().get(0).getIcon();
                double wind=response.getWind().getSpeed();
                String description=response.getWeather().get(0).getDescription();

               // Toast.makeText(getActivity(), "image"+icon, Toast.LENGTH_SHORT).show();

                Picasso.get().load(Eventutils.IMAGE_ICON_PREFIX +icon+".png").into(imagetv);
                latlongtv.setText(temp+"\n"+date+"\n"+description);

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
