package com.example.tourmate_final;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourmate_final.adapter.EventAdapter;
import com.example.tourmate_final.adapter.ExpenselistAdapter;
import com.example.tourmate_final.pojos.EventExpense;
import com.example.tourmate_final.viewmodel.Expense_viewmodel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Expense_details extends Fragment {
    private RecyclerView recyclerView;
    private ExpenselistAdapter expenselistAdapter;
    private String expenseid=null;
    private Expense_viewmodel expense_viewmodel;


    public Expense_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        expense_viewmodel= ViewModelProviders.of(this).get(Expense_viewmodel.class);
        Bundle bundle=getArguments();
        if (bundle!=null){
            expenseid=bundle.getString("id");
            expense_viewmodel.getallexpenses(expenseid);
            Toast.makeText(getActivity(), "ex"+expenseid, Toast.LENGTH_SHORT).show();
        }

        return inflater.inflate(R.layout.fragment_expense_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.expenseRV);
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


    }
}
