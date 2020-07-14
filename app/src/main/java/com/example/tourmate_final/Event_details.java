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
import androidx.cardview.widget.CardView;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.adapter.ExpenselistAdapter;
import com.example.tourmate_final.adapter.MomentsAdapter;
import com.example.tourmate_final.pojos.EventExpense;
import com.example.tourmate_final.pojos.Moments;
import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.viewmodel.Eventviewmodel;
import com.example.tourmate_final.viewmodel.Expense_viewmodel;
import com.example.tourmate_final.viewmodel.MomentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

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
    private Button  TakingphotoBTN, ViewgallleryBTN, viewexpenseBTN, addmorebudgetTV;
    private String eventid = null;
    private String currentPhotoPath;
    private  int totalbudget = 0;
    private List<Moments> momentsList = new ArrayList<>();
    private Eventviewmodel eventviewmodel;
    private Expense_viewmodel expense_viewmodel;
    private MomentViewModel momentViewModel;
    private FloatingActionButton AddexpenseBTN;
    private RecyclerView recyclerView;
    private ExpenselistAdapter expenselistAdapter;
    private CircularProgressBar budgetProgressbar, balanceProgressbar, expenseProgressbar;
private CardView addExpenseCardView;
    String exCatagories;



    public Event_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // tabLayout.setVisibility(View.GONE);
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
        AddexpenseBTN = view.findViewById(R.id.addExpenseBtn);
        totalbudet = view.findViewById(R.id.detailsInitialBudget);
        recyclerView=view.findViewById(R.id.expesnseRV);
        budgetProgressbar=view.findViewById(R.id.BudgetProgressBar);
        expenseProgressbar=view.findViewById(R.id.expenseProgressBar);
        balanceProgressbar=view.findViewById(R.id.remainingProgressBar);
        addExpenseCardView=view.findViewById(R.id.addExpenseCardView);


        addExpenseCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showaddexpense();
            }
        });

      /*
        viewexpenseBTN = view.findViewById(R.id.viewexpenseBtn);
        ViewgallleryBTN = view.findViewById(R.id.viewMomentBtn);
        TakingphotoBTN = view.findViewById(R.id.addMomentBtn);


        addmorebudgetTV = view.findViewById(R.id.add_more_budgetTV);*/

      /*  ViewgallleryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putString("id", eventid);
                Navigation.findNavController(v).navigate(R.id.action_event_details_to_moment_fragment, bundle);

            }
        });*/
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
                expenselistAdapter=new ExpenselistAdapter(getActivity(),eventExpenses);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(expenselistAdapter);
                Toast.makeText(getActivity(), "is"+eventExpenses, Toast.LENGTH_SHORT).show();

            }
        });


        expense_viewmodel.expenselistLD.observe(this,new Observer<List<EventExpense>>() {
            @Override
            public void onChanged(List<EventExpense> eventExpenses) {
                int totalex = 0;
                int remaining=0;
                for (EventExpense ex : eventExpenses) {
                    totalex += ex.getAmount();

                }

                 remaining = totalbudget - totalex;
                remainingbudgetTV.setText(String.valueOf(remaining));
                totalexmenseTV.setText(String.valueOf(totalex));
                int size = eventExpenses.size();
                recyclerView.setVisibility(View.VISIBLE);

                double percentage = (((double) totalex / (double) totalbudget) * 100.0);
                final double remainingParcent = percentage - 100.0;
                expenseProgressbar.setProgressWithAnimation((float) percentage, (long) 1000);
                balanceProgressbar.setProgressWithAnimation((float) remainingParcent, (long) 1000);
                budgetProgressbar.setProgressWithAnimation(100, (long) 1000);


             /*   viewexpenseBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Bundle bundle = new Bundle();
                        bundle.putString("id", eventid);

                        Navigation.findNavController(v).navigate(R.id.action_event_details_to_expense_details, bundle);
                    }
                });*/
             if (size>0){
                 expenselistAdapter=new ExpenselistAdapter(getActivity(),eventExpenses);
                 LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                 recyclerView.setLayoutManager(llm);
                 recyclerView.setAdapter(expenselistAdapter);
                 Toast.makeText(getActivity(), "is"+eventExpenses, Toast.LENGTH_SHORT).show();
                 addExpenseCardView.setVisibility(View.GONE);
             }else {
                 addExpenseCardView.setVisibility(View.VISIBLE);
                 recyclerView.setVisibility(View.GONE);

             }

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
      /*  addmorebudgetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmoreexpense();
            }
        });*/
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
               // int addmorebudget = 0;
                String amount = ExpenseET.getText().toString();
                if (amount.isEmpty()) {
                    Toast.makeText(getActivity(), "provide info", Toast.LENGTH_SHORT).show();

                } else {
                    int currentBudget = totalbudget + Integer.parseInt(amount);
                    eventviewmodel.addMorebudget(eventid, currentBudget);
                  //  TourmateEvent tourmateEvent = new TourmateEvent(null, null, null, null, Integer.parseInt(amount), null);
                 //    eventviewmodel.save(tourmateEvent);
                   // TourmateEvent tourMateEventPojo = new TourmateEvent(eventid,null,null,null,currentBudget,null, Eventutils.getDateWithTime());
                 //  eventviewmodel.update(tourMateEventPojo);
                    Toast.makeText(getActivity(), "Added Budget!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
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
        final Spinner expenseCatagoriesSP = view.findViewById(R.id.expenseCatagories);
        final  Button add=view.findViewById(R.id.addbtn);
        final  Button cancel=view.findViewById(R.id.cancelBtn);

        String[] catagories = {"Select Catagories", "Food", "Transport", "Hotel", "Other"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, catagories);
        expenseCatagoriesSP.setAdapter(arrayAdapter);
        expenseCatagoriesSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exCatagories = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final  AlertDialog dialog = builder.create();
        dialog.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ExpennsenameET.getText().toString();
                String amount = ExpenseET.getText().toString();
                if (amount.isEmpty() & name.isEmpty()) {

                } else {

                    EventExpense eventExpense = new EventExpense(null, eventid, name,
                            Integer.parseInt(amount),exCatagories, Eventutils.getcurrentdate());
                    expense_viewmodel.save(eventExpense);
                    dialog.dismiss();
                }
            }
        });

       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });



    }

}


