package com.example.tourmate_final.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate_final.pojos.UserInformationPojo;
import com.example.tourmate_final.repository.Firebase_repository;
import com.google.firebase.auth.FirebaseAuth;


public class Loginviemodel extends ViewModel {
    public enum Authenticationstate{
        AUTHENTICATIONSTATED,
        UNAUTHENTICATIONSTATED

    }
    private Firebase_repository repository;
    public MutableLiveData<Authenticationstate>statelivedata;
    public  MutableLiveData<String>errmsg=new MutableLiveData<>();


public Loginviemodel(){
        statelivedata=new MutableLiveData<>();
        repository=new Firebase_repository(statelivedata);
        errmsg=repository.getErrmsg();
        if (repository.getFirebaseUser()!=null){
            statelivedata.postValue(Authenticationstate.AUTHENTICATIONSTATED);

        }else {
            statelivedata.postValue(Authenticationstate.UNAUTHENTICATIONSTATED);

        }


}
    public  void  login(String email,String password){

        statelivedata=repository.loginfirebaseuser(email, password);
    }
    public  void  register(UserInformationPojo userInformationPojo){

        statelivedata=repository.registrationuser(userInformationPojo);
    }
    public void  logout(){
        FirebaseAuth.getInstance().signOut();
        statelivedata.postValue(Authenticationstate.UNAUTHENTICATIONSTATED);

    }


}
