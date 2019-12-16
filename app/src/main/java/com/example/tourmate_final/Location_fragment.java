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
import android.widget.Button;
import android.widget.TextView;

import com.example.tourmate_final.Nearbyplace.NearbyplacesBody;
import com.example.tourmate_final.Nearbyplace.Result;
import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.example.tourmate_final.viewmodel.NearbyViewmodel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Location_fragment extends Fragment implements OnMapReadyCallback {

   private TextView locationTV;
   private LocationViewmodel locationViewmodel;
   private NearbyViewmodel nearbyViewmodel;
    private GoogleMap googleMap;
    private Button ATMBN,RESTURANTBTN;
    private  String Type="";
    private Location mylocation=null;

    public Location_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        locationViewmodel= ViewModelProviders.of(getActivity()).get(LocationViewmodel.class);
        nearbyViewmodel= ViewModelProviders.of(getActivity()).get(NearbyViewmodel.class);


        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.Nearbymap);
        mapFragment.getMapAsync(this);
        ATMBN=view.findViewById(R.id.atmBTN);
        RESTURANTBTN=view.findViewById(R.id.resturentBTN);
        ATMBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type="ATM";
                getnearbyresponseData();
            }
        });
        RESTURANTBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type="Resturant";
                getnearbyresponseData();
            }
        });

     locationViewmodel.locationLd.observe(this, new Observer<Location>() {
         @Override
         public void onChanged(Location location) {
             if (location==null){
                 return;
             }
             mylocation=location;
             LatLng currentPos = new LatLng(location.getLatitude(), location.getLongitude());
             if (googleMap != null){
                 googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 14f));
             }
         }
     });
    }

    private void getnearbyresponseData() {
       String apikey=getString(R.string.Location_api_key);
        String Endurl= String.format("place/nearbysearch/json?location=%f,%f&radius=1500&type=%s&key=%s"
                ,mylocation.getLatitude(),mylocation.getLongitude(),Type,apikey);
        nearbyViewmodel.getnearbyfromREpos(Endurl).observe(this, new Observer<NearbyplacesBody>() {
            @Override
            public void onChanged(NearbyplacesBody nearbyplacesBody) {
               List<Result>results= nearbyplacesBody.getResults();
               for (Result r:results){
               }
            }
        });


    }

    private void latlongtoaddress(android.location.Location location) throws IOException {
        Geocoder geocoder=new Geocoder(getActivity());
        List<Address>addresslist=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        String address=addresslist.get(0).getAddressLine(0);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        locationViewmodel.getcurrentlocation();
    }
}
