package com.marquette.clubsportsappnew.parsers_and_adapters.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.marquette.clubsportsappnew.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventVH>
{
    private static final String PREF_NAME = "FavoritePrefs";
    private SharedPreferences sharedPreferences;

    List<Event> eventList;

    public EventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setFilteredList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);
        return new EventAdapter.EventVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventVH holder, int position) {
        Event events = eventList.get(position);
        holder.eventTxt.setText(events.getName());
        holder.dateTxt.setText(events.getTime() + " " + events.getDate());
        holder.sportTxt.setText(events.getSport());
        holder.locationTxt.setText(events.getLocation());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public class EventVH extends RecyclerView.ViewHolder {

        TextView eventTxt, dateTxt, sportTxt, locationTxt;
        LinearLayout linearLayout;

        public EventVH(@NonNull View itemView) {
            super(itemView);

            eventTxt = itemView.findViewById(R.id.event_name);
            dateTxt = itemView.findViewById(R.id.date);
            sportTxt = itemView.findViewById(R.id.sport);
            locationTxt = itemView.findViewById(R.id.location);

            linearLayout = itemView.findViewById(R.id.event_layout);

        }
    }



}