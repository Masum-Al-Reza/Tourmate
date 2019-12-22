package com.example.tourmate_final.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate_final.MainActivity;
import com.example.tourmate_final.pojos.UserInformationPojo;
import com.example.tourmate_final.viewmodel.Loginviemodel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase_repository {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    DatabaseReference rootref;
    DatabaseReference userref;
    DatabaseReference userinfo;
    public  MutableLiveData<Loginviemodel.Authenticationstate>statelivedata=new MutableLiveData<>();
    public  MutableLiveData<UserInformationPojo>userinfold=new MutableLiveData<>();
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

public  MutableLiveData<UserInformationPojo> getuserinfofromDB(){
        rootref=FirebaseDatabase.getInstance().getReference();
        userref=rootref.child(firebaseUser.getUid());
        userinfo=userref.child("Loginfo");
        userinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformationPojo userInformationPojo=dataSnapshot.getValue(UserInformationPojo.class);
                userinfold.postValue(userInformationPojo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userinfold;
}


}
