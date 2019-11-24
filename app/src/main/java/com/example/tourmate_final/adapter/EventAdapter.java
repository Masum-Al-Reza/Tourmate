package com.example.tourmate_final.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate_final.R;
import com.example.tourmate_final.pojos.TourmateEvent;


import java.util.List;
import java.util.zip.Inflater;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private Context context;
    private List<TourmateEvent> eventList;


    public EventAdapter(Context context, List<TourmateEvent> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.list_rows,parent,false);
        return new EventViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.nameTV.setText(eventList.get(position).getEventName());
        holder.destinationTV.setText(eventList.get(position).getDepartureDate());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTV,destinationTV,depurtureTV,budgetTV;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.row_eventName);
            destinationTV=itemView.findViewById(R.id.row_departureDate);



        }
    }
}
