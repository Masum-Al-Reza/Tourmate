package com.example.tourmate_final;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.adapter.Forecast_adapter;
import com.example.tourmate_final.current_weather.CurrentWeatherResponse;
import com.example.tourmate_final.forecast_weather_pojos.ForecastWeatherResponse;
import com.example.tourmate_final.forecast_weather_pojos.Forecast_List;
import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.example.tourmate_final.viewmodel.Weatherviewmodel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Weather_fragment extends Fragment {
    private static final String TAG = Weather_fragment.class.getSimpleName();

    private TextView currentlocationTV,locationanametv,latlongtv,forecastlatlongTV,bootomtextTV;
    private ImageView imagetv;
    private LocationViewmodel locationViewmodel;
    private Weatherviewmodel weatherviewmodel;
    private RecyclerView recyclerView;
    private Forecast_adapter forecast_adapter;
    private String unit=Eventutils.CELCIOUS_UNIT;
    private BottomSheetBehavior bottoms;
    private LinearLayout linearLayout;
    private ToggleButton togglrbt;


    public String tempunit=Eventutils.CELCIOUS_SYMBOL;

    private Location currentlocation;



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
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_weather, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        latlongtv=view.findViewById(R.id.latlongtv);
        imagetv=view.findViewById(R.id.weathericon);
        linearLayout=view.findViewById(R.id.bottomsheet);
        bottoms=BottomSheetBehavior.from(linearLayout);
        recyclerView=view.findViewById(R.id.forecastRV);
        togglrbt=view.findViewById(R.id.toggleBTN);
        forecastlatlongTV=view.findViewById(R.id.forecastlatlongtv);
        locationanametv=view.findViewById(R.id.locationnameTV);
        togglrbt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bottoms.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottoms.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        bottoms.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i==BottomSheetBehavior.STATE_EXPANDED){
                    togglrbt.setChecked(true);
                }else {
                    togglrbt.setChecked(false);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        locationViewmodel.locationLd.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                currentlocation=location;
                initinitializestate(location);
                initializeforecaststate(location);

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecast_menu,menu);
        SearchView searchView=(SearchView)menu.findItem(R.id.searchlocaationitem).getActionView();
        searchView.setQueryHint("enter  city ");
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    Convertqueytolatlng(query);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void Convertqueytolatlng(String query) throws  IOException {
        Geocoder geocoder=new Geocoder(getActivity());
        List<Address>addresslist=geocoder.getFromLocationName(query,1);
        if (addresslist!=null & addresslist.size()>0){
            double lat=addresslist.get(0).getLatitude();
            double lon=addresslist.get(0).getLongitude();
            currentlocation.setLatitude(lat);
            currentlocation.setLongitude(lon);
            initinitializestate(currentlocation);
            initializeforecaststate(currentlocation);
        }

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem furrenhite=menu.findItem(R.id.furrenhaititem);
        MenuItem Celcious=menu.findItem(R.id.celciusitem);
        if (unit.equals(Eventutils.CELCIOUS_UNIT)) {
            furrenhite.setVisible(true);
            Celcious.setVisible(false);
        }else {
            furrenhite.setVisible(false);
            Celcious.setVisible(true);

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.furrenhaititem:
                unit=Eventutils.FURRENHITE_UNIT;
                tempunit=Eventutils.FURRENHITE_SYMBOL;
                initinitializestate(currentlocation);
                initializeforecaststate(currentlocation);

                break;
            case R.id.celciusitem:

                unit=Eventutils.CELCIOUS_UNIT;
                tempunit=Eventutils.CELCIOUS_SYMBOL;
                initinitializestate(currentlocation);
                initializeforecaststate(currentlocation);
                break;
            case  R.id.searchlocaationitem:

                break;

        }
        return super.onOptionsItemSelected(item);

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
                latlongtv.setText(Math.round(temp)+Eventutils.DEGREE+tempunit+"\n"+date+"\n"+description);

            }
        });

    }



}
