package com.example.tourmate_final.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.pojos.EventExpense;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Expense_repository  {
   private DatabaseReference rootref;
   private DatabaseReference User_ref;
   private  DatabaseReference Expenseref;
   private FirebaseUser firebaseUser;
   public MutableLiveData<List<EventExpense>>expenselistLD=new MutableLiveData<>();

   public  Expense_repository(){
       firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
       rootref=FirebaseDatabase.getInstance().getReference();
       User_ref=rootref.child(firebaseUser.getUid());
       Expenseref=User_ref.child("expenses");
   }
   public  void addexpensetoDB(EventExpense expense){
       String expenseid=Expenseref.push().getKey();
       expense.setExpenseId(expenseid);
       Expenseref.child(expense.getEventId()).child(expenseid).setValue(expense);

   }
   public  MutableLiveData<List<EventExpense>>getexpenselists(String eventid){
      Expenseref.child(eventid).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              List<EventExpense> eventExpenses=new ArrayList<>();
              for (DataSnapshot d: dataSnapshot.getChildren()){
                  eventExpenses.add(d.getValue(EventExpense.class));
              }
              expenselistLD.postValue(eventExpenses);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
      return expenselistLD;
   }
}
