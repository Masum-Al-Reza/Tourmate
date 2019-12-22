package com.example.tourmate_final.viewmodel;

import android.net.Uri;
import android.os.storage.StorageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate_final.pojos.Moments;
import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.repository.Eventrepostitory;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.util.List;

import retrofit2.http.Url;

public class Eventviewmodel extends ViewModel {
private Eventrepostitory eventrepostitory;
    public MutableLiveData<List<TourmateEvent>> eventlistDB=new MutableLiveData<>();
    public MutableLiveData<TourmateEvent> eventdetailsLD=new MutableLiveData<>();

public Eventviewmodel(){
    eventrepostitory=new Eventrepostitory();
    eventlistDB=eventrepostitory.eventlistDB;

}
public  void  save(TourmateEvent event){

    eventrepostitory.addevent_to_db(event);
}
public  void geteventdetails(String eventid){
eventdetailsLD=eventrepostitory.geteventdetailsbyeventid(eventid);
}
public  void  delete(TourmateEvent tourmateEvent){
    eventrepostitory.deleteeventfromdb(tourmateEvent);
}
    public  void  update(TourmateEvent tourmateEvent){
        eventrepostitory.updatefromdb(tourmateEvent);
    }


}
