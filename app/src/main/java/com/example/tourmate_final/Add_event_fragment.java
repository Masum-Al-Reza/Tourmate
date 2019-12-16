package com.example.tourmate_final;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.viewmodel.Eventviewmodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_event_fragment extends Fragment {
    private EditText EventnameEt,destinationET,depurtureET,budgetET;
    private Button addeventBTn,DateBTn;
    private Eventviewmodel eventviewmodel;

    private String departureDate;



    public Add_event_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventviewmodel= ViewModelProviders.of(this).get(Eventviewmodel.class);

        return inflater.inflate(R.layout.fragment_add_event_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventnameEt=view.findViewById(R.id.inputeventname);
        destinationET=view.findViewById(R.id.inputdestination);
        depurtureET=view.findViewById(R.id.departureinput);
        budgetET=view.findViewById(R.id.inputbudget);
        addeventBTn=view.findViewById(R.id.eventaddBTn);
        DateBTn=view.findViewById(R.id.add_dateBTN);
        addeventBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventname= EventnameEt.getText().toString();
                String destination=destinationET.getText().toString();
                String depurture=depurtureET.getText().toString();
                String budget=budgetET.getText().toString();
                if (eventname.isEmpty() && destination.isEmpty() &&depurture.isEmpty() && budget.isEmpty()){
                    Toast.makeText(getActivity(), "provide all info", Toast.LENGTH_SHORT).show();

                }else {
                    TourmateEvent event=new TourmateEvent(null,eventname,depurture,destination,Integer.parseInt(budget),departureDate);
                    eventviewmodel.save(event);
                    Toast.makeText(getActivity(), "save"+event, Toast.LENGTH_SHORT).show();

                }


            }
        });
        DateBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datedialogshow();
            }
        });

    }
    private void datedialogshow() {
        Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd=new DatePickerDialog(getActivity(),datelistener,year,month,day);
        dpd.show();
    }
    private  DatePickerDialog.OnDateSetListener datelistener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Calendar calendar=Calendar.getInstance();
            calendar.set(i,i1,i2);
            departureDate=new SimpleDateFormat("dd/mm/yyyy").format(calendar.getTime());
            DateBTn.setText(departureDate);
        }
    };


}
