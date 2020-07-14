package com.example.tourmate_final.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate_final.pojos.EventExpense;
import com.example.tourmate_final.repository.Expense_repository;

import java.util.List;

public class Expense_viewmodel extends ViewModel {


    private Expense_repository expense_repository;
    public  MutableLiveData<List<EventExpense>> expenselistLD=new MutableLiveData<>();
   public  Expense_viewmodel(){
       expense_repository=new Expense_repository();
   }
   public  void save(EventExpense Expense){
       expense_repository.addexpensetoDB(Expense);
   }
   public  void getallexpenses(String eventid){
       expenselistLD=expense_repository.getexpenselists(eventid);
   }

    public void updateExpense(EventExpense expensePojo)
    {
        expense_repository.updateExpense(expensePojo);
    }

    public void DeleteExpense(EventExpense expensePojo)
    {
        expense_repository.deleteExpenseFromDB(expensePojo);
    }



}
