package com.example.tourmate_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Map_dashboard extends Fragment {
    private CardView AtmBTN,ResturabtBTN,BusBTn,TrainBTN,HotelBTN,TouristBTN;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AtmBTN=view.findViewById(R.id.atmCV);
        ResturabtBTN=view.findViewById(R.id.resturantCV);
        BusBTn=view.findViewById(R.id.busCV);
        TrainBTN=view.findViewById(R.id.trainCV);
        HotelBTN=view.findViewById(R.id.hotelCV);
        TouristBTN=view.findViewById(R.id.touristCV);
        final  Bundle bundle = new Bundle();
        AtmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","ATMs");
                Navigation.findNavController(view).navigate(R.id.webview_location,bundle);

            }
        });
        ResturabtBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           bundle.putString("name","Resturants");
                Navigation.findNavController(view).navigate(R.id.webview_location,bundle);
            }
        });
        BusBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","Buses");
                Navigation.findNavController(view).navigate(R.id.webview_location,bundle);

            }
        });
        TrainBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","Trains");
                Navigation.findNavController(view).navigate(R.id.webview_location,bundle);

            }
        });
        HotelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","Hotels");
                Navigation.findNavController(view).navigate(R.id.webview_location,bundle);

            }
        });
        TouristBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("name","Tourists");
                Navigation.findNavController(view).navigate(R.id.webview_location,bundle);

            }
        });
    }


}