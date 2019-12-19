package com.example.tourmate_final;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.adapter.MomentsAdapter;
import com.example.tourmate_final.pojos.EventExpense;
import com.example.tourmate_final.pojos.Moments;
import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.viewmodel.Eventviewmodel;
import com.example.tourmate_final.viewmodel.Expense_viewmodel;
import com.example.tourmate_final.viewmodel.MomentViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Event_details extends Fragment {
    private static final String TAG = Event_details.class.getSimpleName();
    private static final int REQUEST_CAMERA_CODE = 111;
    private static final int REQUEST_STORAGE_CODE = 222;
    private TextView EventnameTV, totalexmenseTV, remainingbudgetTV, totalbudet;
    private Button AddexpenseBTN, TakingphotoBTN, ViewgallleryBTN, viewexpenseBTN, addmorebudgetTV;
    private String eventid = null;
    private String currentPhotoPath;
    int totalbudget = 0;
    private List<Moments> momentsList = new ArrayList<>();
    private Eventviewmodel eventviewmodel;
    private Expense_viewmodel expense_viewmodel;
    private MomentViewModel momentViewModel;


    public Event_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventviewmodel = ViewModelProviders.of(this).get(Eventviewmodel.class);
        expense_viewmodel = ViewModelProviders.of(this).get(Expense_viewmodel.class);
        momentViewModel = ViewModelProviders.of(this).get(MomentViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            eventid = bundle.getString("id");
            eventviewmodel.geteventdetails(eventid);
            expense_viewmodel.getallexpenses(eventid);
            momentViewModel.getMoments(eventid);


        }

        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventnameTV = view.findViewById(R.id.detailsEventName);
        totalexmenseTV = view.findViewById(R.id.detailsTotalExpense);
        remainingbudgetTV = view.findViewById(R.id.detailsRemainingBudget);
        totalbudet = view.findViewById(R.id.detailsInitialBudget);
        AddexpenseBTN = view.findViewById(R.id.addExpenseBtn);
        viewexpenseBTN = view.findViewById(R.id.viewexpenseBtn);
        ViewgallleryBTN = view.findViewById(R.id.viewMomentBtn);
        TakingphotoBTN = view.findViewById(R.id.addMomentBtn);


        addmorebudgetTV = view.findViewById(R.id.add_more_budgetTV);

        ViewgallleryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putString("id", eventid);
                Navigation.findNavController(v).navigate(R.id.action_event_details_to_moment_fragment, bundle);

            }
        });
        momentViewModel.momentsLD.observe(this, new Observer<List<Moments>>() {
            @Override
            public void onChanged(List<Moments> moments) {
                momentsList = moments;
                Toast.makeText(getActivity(), "this" + moments.size(), Toast.LENGTH_SHORT).show();

            }
        });


        expense_viewmodel.expenselistLD.observe(this, new Observer<List<EventExpense>>() {
            @Override
            public void onChanged(List<EventExpense> eventExpenses) {
                int totalex = 0;
                for (EventExpense ex : eventExpenses) {
                    totalex += ex.getAmount();

                }

                int remaingingbudget = totalbudget - totalex;
                remainingbudgetTV.setText("remmaining :" + remaingingbudget);
                totalexmenseTV.setText("total expense:" + totalex);

                viewexpenseBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Bundle bundle = new Bundle();
                        bundle.putString("id", eventid);

                        Navigation.findNavController(v).navigate(R.id.action_event_details_to_expense_details, bundle);
                    }
                });

            }
        });

        eventviewmodel.eventdetailsLD.observe(this, new Observer<TourmateEvent>() {
            @Override
            public void onChanged(TourmateEvent tourmateEvent) {

                EventnameTV.setText(tourmateEvent.getEventName());
                totalbudet.setText(String.valueOf(tourmateEvent.getBudget()));

                totalbudget = tourmateEvent.getBudget();


            }

        });
        AddexpenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showaddexpense();
            }
        });
        addmorebudgetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmoreexpense();
            }
        });
    }

    private void showmoreexpense() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add More Budget");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_budget_dialog_box, null);
        builder.setView(view);

        final EditText ExpenseET = view.findViewById(R.id.dialogAddbudgetAmountInput);
        builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int addmorebudget = 0;
                String amount = ExpenseET.getText().toString();
                if (amount.isEmpty()) {
                    Toast.makeText(getActivity(), "provide info", Toast.LENGTH_SHORT).show();

                } else {
                    TourmateEvent tourmateEvent = new TourmateEvent(null, null, null, null, Integer.parseInt(amount), null);
                    eventviewmodel.save(tourmateEvent);

                }
            }
        });
        builder.setNegativeButton("cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showaddexpense() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add expenses");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_expense_dialog_box, null);
        builder.setView(view);
        final EditText ExpennsenameET = view.findViewById(R.id.dialogAddExpenseNameInput);
        final EditText ExpenseET = view.findViewById(R.id.dialogAddExpenseAmountInput);
        builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ExpennsenameET.getText().toString();
                String amount = ExpenseET.getText().toString();
                if (amount.isEmpty() & name.isEmpty()) {

                } else {

                    EventExpense eventExpense = new EventExpense(null, eventid, name,
                            Integer.parseInt(amount), Eventutils.getcurrentdate());
                    expense_viewmodel.save(eventExpense);
                }
            }
        });
        builder.setNegativeButton("cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

}


