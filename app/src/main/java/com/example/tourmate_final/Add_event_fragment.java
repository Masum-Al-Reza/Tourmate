package com.example.tourmate_final;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.viewmodel.Eventviewmodel;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_event_fragment extends Fragment {
    private TextInputEditText EventnameEt,destinationET,depurtureET,budgetET,DateBTn;
    private Button addeventBTn,updateBTN;
    private Eventviewmodel eventviewmodel;
    private String departureDate="";
    public static String eventID="";
    private String updateEventID ;


    public Add_event_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventviewmodel= ViewModelProviders.of(this).get(Eventviewmodel.class);
     //   Bundle bundle = getArguments();
        if (eventID.isEmpty()) {
        }else {
          //  eventID = bundle.getString("id");

            eventviewmodel.geteventdetails(eventID);
            Toast.makeText(getActivity(), "" + eventID, Toast.LENGTH_SHORT).show();

            eventviewmodel.eventdetailsLD.observe(this, new Observer<TourmateEvent>() {
                @Override
                public void onChanged(TourmateEvent eventPojo) {
try {

    EventnameEt.setText(eventPojo.getEventName());
    depurtureET.setText(eventPojo.getDeparturePlace());
    destinationET.setText(eventPojo.getDestination());
    DateBTn.setText(eventPojo.getDepartureDate());

    budgetET.setText(String.valueOf(eventPojo.getBudget()));
    addeventBTn.setVisibility(View.GONE);
    updateBTN.setVisibility(View.VISIBLE);

    departureDate = eventPojo.getDepartureDate();

    updateEventID = eventID;
    eventID = "";
}
catch (Exception e){

}
                }
            });
        }

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
        updateBTN=view.findViewById(R.id.upateEventBtn);
        DateBTn=view.findViewById(R.id.add_dateBTN);
        addeventBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventname= EventnameEt.getText().toString();
                String destination=destinationET.getText().toString();
                String depurture=depurtureET.getText().toString();
                String budget=budgetET.getText().toString();
                if (eventname.isEmpty()) {
                    EventnameEt.setError("Provide event name!");
                } else if (depurture.isEmpty()) {
                    depurtureET.setError("Provide start location!");
                } else if (destination.isEmpty()) {
                    destinationET.setError("Provide destination location!");
                } else if (budget.isEmpty()) {
                    budgetET.setError("Provide budget amount!");
                }
               else {
                    TourmateEvent event=new TourmateEvent(null,eventname,depurture,destination,Integer.parseInt(budget),departureDate, Eventutils.getDateWithTime());
                    eventviewmodel.save(event);
                    Toast.makeText(getActivity(), "save"+event, Toast.LENGTH_SHORT).show();

                }


            }
        });

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = EventnameEt.getText().toString();
                String startLocation = depurtureET.getText().toString();
                String destination = destinationET.getText().toString();
                String budget = budgetET.getText().toString();
                TourmateEvent tourMateEventPojo = new TourmateEvent(updateEventID,eventName,startLocation,destination,Integer.parseInt(budget),departureDate, Eventutils.getDateWithTime());
                eventviewmodel.update(tourMateEventPojo);
                Navigation.findNavController(v).navigate(R.id.eventlistfragment);

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
            departureDate=new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
            DateBTn.setText(departureDate);
        }
    };


}
