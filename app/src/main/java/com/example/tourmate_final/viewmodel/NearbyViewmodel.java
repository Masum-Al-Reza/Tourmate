package com.example.tourmate_final.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate_final.Nearbyplace.NearbyplacesBody;
import com.example.tourmate_final.repository.NearbyRepos;

public class NearbyViewmodel extends ViewModel {
    private NearbyRepos nearbyRepos;
    public  NearbyViewmodel(){
        nearbyRepos=new NearbyRepos();
    }
public MutableLiveData<NearbyplacesBody>getnearbyfromREpos(String endurl){
        return nearbyRepos.nearbyresponse(endurl);

}
}
