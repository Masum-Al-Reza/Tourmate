package com.example.tourmate_final.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate_final.R;
import com.example.tourmate_final.pojos.EventExpense;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExpenselistAdapter extends RecyclerView.Adapter<ExpenselistAdapter.Expenseviewholder> {
    private Context context;
    private List<EventExpense>expenseList;

    public ExpenselistAdapter(Context context, List<EventExpense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public Expenseviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.expense_list_rows,parent,false);
        return new ExpenselistAdapter.Expenseviewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull Expenseviewholder holder, int position) {
        holder.nameTV.setText(expenseList.get(position).getExpenseName());
        holder.expenseTV.setText(String.valueOf(expenseList.get(position).getAmount()));
        holder.dateTV.setText(expenseList.get(position).getExpenseDateTime());

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class Expenseviewholder extends RecyclerView.ViewHolder {
        private TextView nameTV,expenseTV,dateTV;

        public Expenseviewholder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.row_expenseName);
            expenseTV=itemView.findViewById(R.id.row_expense);
            dateTV=itemView.findViewById(R.id.row_expense_Date);

        }
    }
}
