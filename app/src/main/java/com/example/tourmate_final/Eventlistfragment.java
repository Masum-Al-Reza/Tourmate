package com.example.tourmate_final;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourmate_final.adapter.EventAdapter;
import com.example.tourmate_final.adapter.Forecast_adapter;
import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.viewmodel.Eventviewmodel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Eventlistfragment extends Fragment {
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private Eventviewmodel eventviewmodel;


    public Eventlistfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        eventviewmodel= ViewModelProviders.of(this).get(Eventviewmodel.class);
        return inflater.inflate(R.layout.fragment_eventlistfragment, container, false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.EventRV);
        eventviewmodel.eventlistDB.observe(this, new Observer<List<TourmateEvent>>() {
            @Override
            public void onChanged(List<TourmateEvent> tourmateEvents) {

                eventAdapter=new EventAdapter(getActivity(),tourmateEvents);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(eventAdapter);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.addeventnav:
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_eventlistfragment_to_add_event_fragment);
                break;
            case R.id.locationitem:
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_eventlistfragment_to_location);
                break;
            case R.id.weatheritem:
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_eventlistfragment_to_weather);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    }


