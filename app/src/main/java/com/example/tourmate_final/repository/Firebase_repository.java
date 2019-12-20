package com.example.tourmate_final.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.pojos.UserInformationPojo;
import com.example.tourmate_final.viewmodel.Loginviemodel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase_repository {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference rootref;
    private DatabaseReference userref;
    private DatabaseReference userinfo;
    public  MutableLiveData<Loginviemodel.Authenticationstate>statelivedata=new MutableLiveData<>();
    public  MutableLiveData<String>errmsg=new MutableLiveData<>();
    public  Firebase_repository(MutableLiveData<Loginviemodel.Authenticationstate> statelivedata){

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        this.statelivedata=statelivedata;



    }
    public  FirebaseUser getFirebaseUser(){
        return firebaseUser;

    }

    public MutableLiveData<String> getErrmsg() {
        return errmsg;
    }

    public  MutableLiveData<Loginviemodel.Authenticationstate>  loginfirebaseuser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUser=firebaseAuth.getCurrentUser();

             statelivedata.postValue(Loginviemodel.Authenticationstate.AUTHENTICATIONSTATED);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errmsg.postValue(e.getLocalizedMessage());
           statelivedata.postValue(Loginviemodel.Authenticationstate.UNAUTHENTICATIONSTATED);
            }
        });
return statelivedata;

    }
    public  MutableLiveData<Loginviemodel.Authenticationstate>  registrationuser(final UserInformationPojo userInformationPojo){

        firebaseAuth.createUserWithEmailAndPassword(userInformationPojo.getUserEmail(), userInformationPojo.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUser=firebaseAuth.getCurrentUser();
                    statelivedata.postValue(Loginviemodel.Authenticationstate.AUTHENTICATIONSTATED);
                    rootref= FirebaseDatabase.getInstance().getReference();
                    userref=rootref.child(firebaseUser.getUid());
                    userinfo=userref.child("user info");

                    String userid=firebaseUser.getUid();
                    userInformationPojo.setUesrID(userid);
                    userinfo.setValue(userInformationPojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errmsg.postValue(e.getLocalizedMessage());
             statelivedata.postValue(Loginviemodel.Authenticationstate.UNAUTHENTICATIONSTATED);
            }
        });

return statelivedata;
    }



}
