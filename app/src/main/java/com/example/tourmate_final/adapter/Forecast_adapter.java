package com.example.tourmate_final.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.R;
import com.example.tourmate_final.Weather_fragment;
import com.example.tourmate_final.forecast_weather_pojos.ForecastWeatherResponse;
import com.example.tourmate_final.forecast_weather_pojos.Forecast_List;

import java.util.ArrayList;
import java.util.List;

public class Forecast_adapter extends RecyclerView.Adapter<Forecast_adapter.Forecastviewholder> {
    private Context context;
    private List<Forecast_List>forecastList;
    private String tempunit ;

    public Forecast_adapter(Context context, List<Forecast_List> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public Forecastviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.forecastlistrows,parent,false);
        return new Forecast_adapter.Forecastviewholder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull Forecastviewholder holder, int position) {
        holder.tempnameTV.setText(String.valueOf(Math.round(forecastList.get(position).getTemp().getMin()))+Eventutils.DEGREE+tempunit);
        holder.maxtempTV.setText(String.valueOf(Math.round(forecastList.get(position).getTemp().getMax()))+Eventutils.DEGREE+tempunit);

        holder.row_forcast_sunrise.setText(Eventutils.getTime(forecastList.get(position).getSunrise()));
        holder.row_forcast_sunset.setText(Eventutils.getTime(forecastList.get(position).getSunset()));
        holder.row_forcast_date.setText((Eventutils.getformatteddate(forecastList.get(position).getDt())));
        holder.row_forcast_status.setText(forecastList.get(position).getWeather().get(0).getDescription());

    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    class Forecastviewholder extends RecyclerView.ViewHolder{

private TextView tempnameTV,maxtempTV,row_forcast_sunrise,row_forcast_sunset,row_forcast_status,row_forcast_date;
        public Forecastviewholder(@NonNull View itemView) {
            super(itemView);
            tempnameTV=itemView.findViewById(R.id.row_forcast_min);
            maxtempTV=itemView.findViewById(R.id.row_forcast_temp_max);
            row_forcast_sunrise=itemView.findViewById(R.id.row_forcast_sunrise);
            row_forcast_sunset=itemView.findViewById(R.id.row_forcast_sunset);
            row_forcast_status=itemView.findViewById(R.id.row_forcast_status);
            row_forcast_date=itemView.findViewById(R.id.row_forcast_date);
        }

}

}
