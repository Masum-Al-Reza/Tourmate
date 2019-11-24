package com.example.tourmate_final.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.repository.Eventrepostitory;


import java.util.List;

public class Eventviewmodel extends ViewModel {
private Eventrepostitory eventrepostitory;
    public MutableLiveData<List<TourmateEvent>> eventlistDB=new MutableLiveData<>();

public Eventviewmodel(){
    eventrepostitory=new Eventrepostitory();
    eventlistDB=eventrepostitory.eventlistDB;

}
public  void  save(TourmateEvent event){

    eventrepostitory.addevent_to_db(event);
}

}
