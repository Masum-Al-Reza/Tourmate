package com.example.tourmate_final.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.viewmodel.Loginviemodel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Firebase_repository {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
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
    public  MutableLiveData<Loginviemodel.Authenticationstate>  registrationuser(String email,String password){

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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



}
