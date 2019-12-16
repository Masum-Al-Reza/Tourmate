package com.example.tourmate_final.repository;

import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.example.tourmate_final.pojos.Moments;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MomentRepository {
    private FirebaseUser firebaseUser;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference momentsRef;
    public MutableLiveData<List<Moments>> momentsLD = new MutableLiveData<>();

    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath();

    public MomentRepository() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(firebaseUser.getUid());
        momentsRef = userRef.child("Moments");

    }

    public void addNewMoment(Moments moments){
        String momentId = momentsRef.push().getKey();
        moments.setMomentId(momentId);
        momentsRef.child(moments.getEventId())
                .child(momentId)
                .setValue(moments);
    }


    public MutableLiveData<List<Moments>> getAllMoments(String eventId){
        momentsRef.child(eventId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Moments> moments = new ArrayList<>();
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            moments.add(d.getValue(Moments.class));
                        }
                        momentsLD.postValue(moments);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return momentsLD;
    }

    public void deleteImagefromDB(Moments momentPojo) {
        String momentID = momentPojo.getMomentId();
        momentsRef.child(momentPojo.getEventId())
                .child(momentID)
                .removeValue();

        //Delete also firebaseStrogae

        FirebaseStorage  mFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(momentPojo.getDownloadUrl());



        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

               /// Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

}
