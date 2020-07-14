package com.example.tourmate_final.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate_final.Add_event_fragment;
import com.example.tourmate_final.Helper.Eventutils;
import com.example.tourmate_final.MainActivity;
import com.example.tourmate_final.R;
import com.example.tourmate_final.pojos.TourmateEvent;
import com.example.tourmate_final.viewmodel.Eventviewmodel;


import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private Context context;
    private List<TourmateEvent> eventList;
    Bundle bundle;
    private Eventviewmodel eventviewmodel=new Eventviewmodel();


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
    public void onBindViewHolder(@NonNull final EventViewHolder holder, final int position) {
        holder.nameTV.setText(eventList.get(position).getEventName());
        holder.destinationdate.setText(eventList.get(position).getDepartureDate());
        holder.budgetTV.setText(String.valueOf(eventList.get(position).getBudget()));
        holder.destinatinlaceTV.setText(eventList.get(position).getDestination());
        holder.depurtureTV.setText(eventList.get(position).getDeparturePlace());
        holder.currenttime.setText(eventList.get(position).getCreateEventDate());
        String goingdate=(eventList.get(position).getDepartureDate());
        long differdate= Eventutils.getDefferentBetweenTwoDate(Eventutils.getcurrentdate(),goingdate);
        if (differdate==0)
        {
            holder.dateleft.setText("Have a safe Journey!");

        }
        else if (differdate<0)
        {
            holder.dateleft.setText("Tour Finished!");
        }

        else
        {
            holder.dateleft.setText(String.valueOf(Eventutils.getDefferentBetweenTwoDate(Eventutils.getcurrentdate(),goingdate))+" Days Left");

        }
        holder.rowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String eventid=eventList.get(position).getEventID();
                final TourmateEvent eventPojo = eventList.get(position);
                  bundle=new Bundle();
                bundle.putString("id",eventid);

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.event_row_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.detailse_item:
                                Navigation.findNavController(holder.itemView).navigate(R.id.action_eventlistfragment_to_event_details,bundle);
                                break;
                            case  R.id.edit_item:
                                Add_event_fragment.eventID = eventid;

                                Navigation.findNavController(holder.itemView).navigate(R.id.action_eventlistfragment_to_add_event_fragment);

                                break;
                            case  R.id.delete_item:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Delete this entire Event?");
                                builder.setMessage("Remember: Once Delete of the event it cannot be undone!");
                                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        eventviewmodel.delete(eventPojo);
                                        Navigation.findNavController(holder.itemView).navigate(R.id.eventlistfragment);
                                    }
                                });
                                builder.setNegativeButton("Cancel",null);

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();



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
        public TextView nameTV, destinationdate,depurtureTV,budgetTV,destinatinlaceTV,rowmenu,currenttime,dateleft;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.row_eventName);
            dateleft=itemView.findViewById(R.id.row_remaining);
            destinationdate =itemView.findViewById(R.id.row_departureDate);
            depurtureTV =itemView.findViewById(R.id.row_depurturename);
            currenttime =itemView.findViewById(R.id.currenttime);
            budgetTV =itemView.findViewById(R.id.row_budget);
            destinatinlaceTV =itemView.findViewById(R.id.row_destination);
            rowmenu =itemView.findViewById(R.id.row_menu);






        }
    }
}
