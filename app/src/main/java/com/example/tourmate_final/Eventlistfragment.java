package com.example.tourmate_final;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tourmate_final.adapter.EventAdapter;
import com.example.tourmate_final.adapter.Forecast_adapter;
import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.viewmodel.Eventviewmodel;
import com.example.tourmate_final.viewmodel.Loginviemodel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Eventlistfragment extends Fragment {
    private static final String TAG = Eventlistfragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private ProgressBar progressBarevent;
    private CardView nodataevent;
    private Eventviewmodel eventviewmodel;
    private Loginviemodel loginviemodel;


    public Eventlistfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        eventviewmodel= ViewModelProviders.of(this).get(Eventviewmodel.class);
        loginviemodel= ViewModelProviders.of(this).get(Loginviemodel.class);
        return inflater.inflate(R.layout.fragment_eventlistfragment, container, false);

    }



    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.EventRV);
        progressBarevent=view.findViewById(R.id.eventProgress);
        nodataevent=view.findViewById(R.id.nodataCV);
        nodataevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        eventviewmodel.eventlistDB.observe(this, new Observer<List<TourmateEvent>>() {
            @Override
            public void onChanged(List<TourmateEvent> tourmateEvents) {
                int size=tourmateEvents.size();

                if (size<=0){
                    nodataevent.setVisibility(View.VISIBLE);
                    progressBarevent.setVisibility(View.GONE);

                }else {

                    eventAdapter = new EventAdapter(getActivity(), tourmateEvents);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(eventAdapter);
                    progressBarevent.setVisibility(View.GONE);
                }

            }
        });
    }


    }


