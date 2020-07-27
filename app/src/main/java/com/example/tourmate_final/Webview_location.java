package com.example.tourmate_final;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tourmate_final.viewmodel.LocationViewmodel;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Webview_location extends Fragment {
    private WebView webView;
     String findlocation;
     private LocationViewmodel locationViewmodel;
     String ATM="ATMs",Resturant="Resturants",Hotel="Hotels",Bus="Buses",Train="Trains",Tourist="Tourists";




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        isLocationPermissionGranted();
        locationViewmodel= ViewModelProviders.of(getActivity()).get(LocationViewmodel.class);
        if (ATM.equals(getArguments().getString("name"))){
            findlocation="ATMs";
        }else if(Resturant.equals(getArguments().getString("name"))){

            findlocation="Resturants";
        }else if(Hotel.equals(getArguments().getString("name"))){

            findlocation="Hotels";
        }else if(Bus.equals(getArguments().getString("name"))){

            findlocation="Buses";
        }else if(Train.equals(getArguments().getString("name"))){

            findlocation="Trains";
        }else if(Tourist.equals(getArguments().getString("name"))){

            findlocation="Tourists";
        } else {
            findlocation = getArguments().getString("name");
        }
        return inflater.inflate(R.layout.fragment_webview_location, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                locationViewmodel.getcurrentlocation();
            }
        });
        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {

                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        /*resolvable.startResolutionForResult(getActivity(),
                                123);*/
                        startIntentSenderForResult(resolvable.getResolution().getIntentSender(),
                                123, null, 0, 0,
                                0, null);

                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });


        webView = view.findViewById(R.id.webview);
        webView.loadUrl("https://www.google.com/maps/@23.8463694,90.2526174,15z");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setOnKeyListener(new View.OnKeyListener() {
                                     @Override
                                     public boolean onKey(View v, int keyCode, KeyEvent event) {
                                         //This is the filter
                                         if (event.getAction() != KeyEvent.ACTION_DOWN)
                                             return true;
                                         if (keyCode == KeyEvent.KEYCODE_BACK) {
                                             if (webView.canGoBack()) {
                                                 webView.goBack();
                                             } else {
                                                 (getActivity()).onBackPressed();
                                             }
                                             return true;
                                         }
                                         return false;
                                     }
                                 }
        );
        locationViewmodel.locationLd.observe(getActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                if (location == null) {

                } else {

                    String url = String.format("https://www.google.com.bd/maps/search/%s/@%f,%f,15z/data=!3m1!4b1?hl=en", findlocation, location.getLatitude(), location.getLongitude());
                    webView.loadUrl(url);
                    webView.setFocusable(true);
                    webView.setFocusableInTouchMode(true);
                }
            }
        });
    }
        private LocationRequest createLocationRequest() {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(3000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            return locationRequest;
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        private boolean isLocationPermissionGranted() {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
                return false;
            }

            return true;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode == 111 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {

                locationViewmodel.getcurrentlocation();
                webView.reload();

            } else {

                Toast.makeText(getActivity(), "Need Permission for use Map", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 123) {
                if (resultCode == Activity.RESULT_OK) {
                    locationViewmodel.getcurrentlocation();
                    webView.reload();
                }
            }
        }

    @Override
    public void onResume() {
        super.onResume();
        locationViewmodel.getcurrentlocation();
        webView.reload();
    }

    }
