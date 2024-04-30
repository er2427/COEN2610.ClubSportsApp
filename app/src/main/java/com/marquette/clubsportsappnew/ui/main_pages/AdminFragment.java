package com.marquette.clubsportsappnew.ui.main_pages;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.clubsportsappnew.R;
import com.marquette.clubsportsappnew.parsers_and_adapters.adapters.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminFragment extends Fragment {
    private EventAdapter eventAdapter;
    public static List<Event> eventList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addEventButton = view.findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Inflate the event input dialog layout
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View eventInputDialogView = inflater.inflate(R.layout.dialog_event_input, null);

                // Create the event input dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(eventInputDialogView);
                AlertDialog eventInputDialog = builder.create();

                // Set up the "Create Event" button in the dialog
                Button createEventButton = eventInputDialogView.findViewById(R.id.createEventButton);
                createEventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the input from the input fields
                        EditText eventNameInput = eventInputDialogView.findViewById(R.id.eventNameInput);
                        String eventName = eventNameInput.getText().toString();

                        EditText eventTypeInput = eventInputDialogView.findViewById(R.id.eventTypeInput);
                        String eventType = eventTypeInput.getText().toString();

                        EditText eventSportInput = eventInputDialogView.findViewById(R.id.eventSportInput);
                        String eventSport = eventSportInput.getText().toString();

                        EditText eventLocationInput = eventInputDialogView.findViewById(R.id.eventLocationInput);
                        String eventLocation = eventLocationInput.getText().toString();

                        EditText eventDateInput = eventInputDialogView.findViewById(R.id.eventDateInput);
                        String eventDateString = eventDateInput.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar eventDate = Calendar.getInstance();
                        try {
                            eventDate.setTime(sdf.parse(eventDateString));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Event newEvent = new Event(); // Create a new event object
                        eventList.add(newEvent);     // Add the new event to

                        // Update the ListView in the AdminFragment to reflect the newly added event
                        ListView eventListView = view.findViewById(R.id.eventListView);
                        ArrayAdapter<Event> eventListAdapter = new ArrayAdapter<Event>(getContext(), android.R.layout.simple_list_item_1, eventList);
                        eventListView.setAdapter(eventListAdapter);
                        eventListAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed

                        // Close the dialog
                        eventInputDialog.dismiss();
                    }
                });

                // Show the dialog
                eventInputDialog.show();
            }
        });
    }

}


