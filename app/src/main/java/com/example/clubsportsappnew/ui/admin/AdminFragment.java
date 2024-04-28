package com.example.clubsportsappnew.ui.admin;
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
import com.example.clubsportsappnew.ui.calendar.*;

import com.example.clubsportsappnew.ui.home.DatabaseHelper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminFragment extends Fragment {

    private List<Event> eventList = new ArrayList<>();

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

                        // Create a new event with the input details
                        Event newEvent = new Event(eventName, eventType, eventLocation, "Type", eventDate, eventDate);

                        // Add the new event to the event list
                        eventList.add(newEvent);

                        appendEventToXMLFile(newEvent);

                        //Add the new event to the CalendarFragment
                        //CalendarFragment calendarFragment = (CalendarFragment) getFragmentManager().findFragmentById(R.id.nav_calendar);
                        //if (calendarFragment != null) {
                          // calendarFragment.addEvent(newEvent);
                        //}
                        //else {
                          //  Toast.makeText(getContext(), "Calendar Fragment not found", Toast.LENGTH_SHORT).show();
                        //}

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
    public void appendEventToXMLFile(Event event) {
        try {
            // Open the file
            File file = new File(getContext().getFilesDir(), "practice_times.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            // Get the root element
            Element root = document.getDocumentElement();

            // Create a new practice element
            Element practice = document.createElement("practice");

            // Add child elements to the practice element
            Element sportName = document.createElement("sportName");
            sportName.appendChild(document.createTextNode(event.getSport()));
            practice.appendChild(sportName);

            Element eventType = document.createElement("eventType");
            eventType.appendChild(document.createTextNode(event.getType()));
            practice.appendChild(eventType);

            Element eventLocation = document.createElement("eventLocation");
            eventLocation.appendChild(document.createTextNode(event.getLocation()));
            practice.appendChild(eventLocation);

            Element eventDate = document.createElement("eventDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            eventDate.appendChild(document.createTextNode(sdf.format(event.getTime())));
            practice.appendChild(eventDate);

            root.appendChild(practice);

            // Write the updated document to the file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//Filtering according to what? Figure this out
//What does the database get email? Figure this out


