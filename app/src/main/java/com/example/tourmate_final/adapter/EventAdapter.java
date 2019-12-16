package com.example.tourmate_final.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate_final.R;
import com.example.tourmate_final.pojos.TourmateEvent;


import java.util.List;

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
    public void onBindViewHolder(@NonNull EventViewHolder holder, final int position) {
        holder.nameTV.setText(eventList.get(position).getEventName());
        holder.destinationdate.setText(eventList.get(position).getDepartureDate());
        holder.budgetTV.setText(String.valueOf(eventList.get(position).getBudget()));
        holder.destinatinlaceTV.setText(eventList.get(position).getDestination());
        holder.depurtureTV.setText(eventList.get(position).getDeparturePlace());
        holder.rowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.event_row_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String eventid=eventList.get(position).getEventID();
                        Bundle bundle=new Bundle();
                        bundle.putString("id",eventid);

                        switch (item.getItemId()){

                            case R.id.detailse_item:
                                Navigation.findNavController(v).navigate(R.id.action_eventlistfragment_to_event_details,bundle);
                                break;
                            case  R.id.edit_item:
                                Navigation.findNavController(v).navigate(R.id.action_eventlistfragment_to_add_event_fragment);

                                break;
                            case  R.id.delete_item:
                                break;
                        }
                        return false;
                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTV, destinationdate,depurtureTV,budgetTV,destinatinlaceTV,rowmenu;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.row_eventName);
            destinationdate =itemView.findViewById(R.id.row_departureDate);
            depurtureTV =itemView.findViewById(R.id.row_depurturename);
            budgetTV =itemView.findViewById(R.id.row_budget);
            destinatinlaceTV =itemView.findViewById(R.id.row_destination);
            rowmenu =itemView.findViewById(R.id.row_menu);






        }
    }
}
