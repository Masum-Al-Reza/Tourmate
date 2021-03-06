package com.example.tourmate_final.repository;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.pojos.Moments;
import com.example.tourmate_final.pojos.TourmateEvent;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Eventrepostitory {
    private DatabaseReference rootref;
    private DatabaseReference userref;
    private  DatabaseReference eventref;
    private FirebaseUser firebaseUser;
    public MutableLiveData<List<TourmateEvent>> eventlistDB;
    public MutableLiveData<TourmateEvent> eventdetailsLD=new MutableLiveData<>();
    public   Eventrepostitory(){
        eventlistDB = new MutableLiveData<>();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        userref=rootref.child(firebaseUser.getUid());
        eventref=userref.child("event");
        eventref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TourmateEvent> events=new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    events.add(d.getValue(TourmateEvent.class));

                }
                eventlistDB.postValue(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public  MutableLiveData<TourmateEvent>  geteventdetailsbyeventid(final String eventid){
        eventref.child(eventid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TourmateEvent event=dataSnapshot.getValue(TourmateEvent.class);
                eventdetailsLD.postValue(event);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });return eventdetailsLD;
    }
public  void  addevent_to_db(TourmateEvent event ){
        String EventID=eventref.push().getKey();
        event.setEventID(EventID);
        eventref.child(EventID).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
}
public  void updatefromdb(TourmateEvent tourmateEvent){
          String eventid=tourmateEvent.getEventID();
          tourmateEvent.setEventID(eventid);
        eventref.child(eventid).setValue(tourmateEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
}
    public void addMorebudget(String eventID, int amount) {
        eventref.child(eventID).child("initialBudget").setValue(amount).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }
public  void  deleteeventfromdb(TourmateEvent tourmateEvent){
        final String eventid=tourmateEvent.getEventID();
        eventref.child(eventid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                final DatabaseReference expenseref=userref.child("EXpense");
                expenseref.child(eventid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DatabaseReference momentref=expenseref.child("moment");
                        momentref.child(eventid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                });

            }
        });


}

}
