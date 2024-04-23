package com.example.clubsportsappnew.ui.admin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.ui.calendar.*;

import com.example.clubsportsappnew.ui.home.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminFragment extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase getReadableDatabase;
    private List<Event> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);

        Button addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Create a new event
                Calendar eventDate = Calendar.getInstance();
                Event newEvent = new Event("New Event", "Sport", "Location", "Type", eventDate, eventDate);

                 eventList.add(newEvent);

                // Filter events to update the calendar
                FragmentManager fragmentManager = getSupportFragmentManager();
                CalendarFragment calendarFragment = (CalendarFragment) fragmentManager.findFragmentById(R.id.nav_calendar);
                if(calendarFragment != null) {
                    calendarFragment.filterEvents(eventDate.get(Calendar.YEAR), eventDate.get(Calendar.MONTH), eventDate.get(Calendar.DAY_OF_MONTH));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Calendar Fragment not found", Toast.LENGTH_SHORT).show();
                }

                // Correctly reference filterEvents method
            }
        });
    }
}

