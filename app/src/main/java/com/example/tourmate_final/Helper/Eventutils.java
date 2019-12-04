package com.example.tourmate_final.Helper;

import android.provider.ContactsContract;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Eventutils {
    public  static  final  String IMAGE_ICON_PREFIX="https://openweathermap.org/img/wn";
    public  static  final String getformatteddate(long dt){
        Date date=new Date(1000*dt);
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);

    }
}
