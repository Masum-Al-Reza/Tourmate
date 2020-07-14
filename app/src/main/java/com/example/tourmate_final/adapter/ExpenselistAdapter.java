package com.example.tourmate_final.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.R;
import com.example.tourmate_final.pojos.EventExpense;
import com.example.tourmate_final.viewmodel.Expense_viewmodel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExpenselistAdapter extends RecyclerView.Adapter<ExpenselistAdapter.Expenseviewholder> {
    private Context context;
    private List<EventExpense>expenseList;
    private Expense_viewmodel expense_viewmodel=new Expense_viewmodel();
    private String expenseID;
    private String eventID,Catagories;


    public ExpenselistAdapter(Context context, List<EventExpense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public Expenseviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.expense_list_rows,parent,false);
        return new Expenseviewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull Expenseviewholder holder, final int position) {
        expenseID = expenseList.get(position).getExpenseId();
        eventID = expenseList.get(position).getEventId();
        final EventExpense expensePojo = expenseList.get(position);

        holder.nameTV.setText(expenseList.get(position).getExpenseName());
        holder.expenseTV.setText(String.valueOf(expenseList.get(position).getAmount()));
        holder.dateTV.setText(expenseList.get(position).getExpenseDateTime());
        holder.catgries.setText(expenseList.get(position).getE_catagories());

holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        expense_viewmodel.DeleteExpense(expensePojo);

    }
});
holder.edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Catagories = expenseList.get(position).getE_catagories();
        showExpenseDilog(expenseID,eventID,Catagories,position);
    }
});
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class Expenseviewholder extends RecyclerView.ViewHolder {
        private TextView nameTV,expenseTV,dateTV,catgries;
        private ImageView delete,edit;


        public Expenseviewholder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.row_expenseName);
            expenseTV=itemView.findViewById(R.id.row_expense);
            dateTV=itemView.findViewById(R.id.row_expense_Date);
            delete=itemView.findViewById(R.id.row_expenseDelete);
            edit=itemView.findViewById(R.id.row_expenseEdit);
            catgries=itemView.findViewById(R.id.row_expense_catagories);

        }
    }
/*
    public void  showexpensedialogue(final  String ExpenseID,final  String EventIF,final  int Position,final  String excatagories){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add expenses");
        View view = LayoutInflater.from(context).inflate(R.layout.add_expense_dialog_box, null);
        builder.setView(view);
        final EditText ExpennsenameET = view.findViewById(R.id.dialogAddExpenseNameInput);
        final EditText ExpenseET = view.findViewById(R.id.dialogAddExpenseAmountInput);
        final Spinner expenseCatagoriesSP = view.findViewById(R.id.expenseCatagories);
        final  Button update=view.findViewById(R.id.addbtn);
        final  Button cancel=view.findViewById(R.id.cancelBtn);

        String catagories[] = { "Food", "Transport", "Hotel", "Other"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, catagories);
        expenseCatagoriesSP.setAdapter(arrayAdapter);
        int spinnerPosition = arrayAdapter.getPosition(excatagories);
        expenseCatagoriesSP.setSelection(spinnerPosition);


        update.setText("update");
      //  ExpennsenameET.setText(expenseList.get(spinnerPosition).getExpenseName());
        ExpennsenameET.setText(expenseList..getExpenseName());
    }
*/

    private void showExpenseDilog(final String expenseID,final String eventID,final String exCatagories,final int position) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Update Expense");
        View view1 = LayoutInflater.from(context).inflate(R.layout.add_expense_dialog_box,null);

        builder.setView(view1);
        final EditText expenseNameET = view1.findViewById(R.id.dialogAddExpenseNameInput);
        final EditText expenseAmoutET = view1.findViewById(R.id.dialogAddExpenseAmountInput);
        final Spinner expenseCatagoriesSp = view1.findViewById(R.id.expenseCatagories);


        Button updatebtn = view1.findViewById(R.id.addbtn);
        Button cancelbtn = view1.findViewById(R.id.cancelBtn);

        String catagories [] = {"Food","Transport","Hotel","Other"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,catagories);
        expenseCatagoriesSp.setAdapter(arrayAdapter);
        Toast.makeText(context, ""+exCatagories, Toast.LENGTH_SHORT).show();
        int spinnerPosition = arrayAdapter.getPosition(exCatagories);
        expenseCatagoriesSp.setSelection(spinnerPosition);

        updatebtn.setText("Update");
        expenseNameET.setText(expenseList.get(position).getExpenseName());
        expenseAmoutET.setText(String.valueOf(expenseList.get(position).getAmount()));

        expenseCatagoriesSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Catagories = parent.getItemAtPosition(position).toString();
                Toast.makeText(context, ""+Catagories, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final AlertDialog dialog = builder.create();

        dialog.show();

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ename = expenseNameET.getText().toString();
                String amount = expenseAmoutET.getText().toString();

                EventExpense expensePojo = new EventExpense(expenseID,eventID,ename,Integer.parseInt(amount),Catagories, Eventutils.getDateWithTime());

                expense_viewmodel.updateExpense(expensePojo);

                Toast.makeText(context,"Updated", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



    }


}
