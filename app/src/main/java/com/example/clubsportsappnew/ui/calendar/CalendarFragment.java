package com.example.clubsportsappnew.ui.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.Versions;
import com.example.clubsportsappnew.VersionsAdapter;
import com.example.clubsportsappnew.ui.home.SportXmlParser;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarFragment extends Fragment {

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    ChipGroup chipGroup;
    Set<String> selectedItems = new HashSet<>();
    // Initializing favorites as selected

    private VersionsAdapter versionsAdapter;
    private List<Versions> calendarVersionsList;

    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        ArrayList<String> sportsNames = SportXmlParser.parseSportNames(requireContext());
        sportsNames.add(0, "Favorites");
        String[] item = sportsNames.toArray(new String[0]);

        calendarVersionsList = SportXmlParser.parseSports(requireContext());
        versionsAdapter = new VersionsAdapter(calendarVersionsList, requireContext());

        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<>(requireContext(), R.layout.filter_list_item, item);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Check if the item is already selected
                if (!selectedItems.contains(selectedItem)) {
                    // Add chip to ChipGroup
                    addChip(selectedItem);
                    // Add the selected item to the set
                    selectedItems.add(selectedItem);
                } else {
                    // Notify the user or handle the case where the item is already selected
                    Toast.makeText(requireContext(), "Item already selected", Toast.LENGTH_SHORT).show();
                }

                // Clear the text in AutoCompleteTextView after selection
                autoCompleteTextView.setText("");
            }
        });

        chipGroup = view.findViewById(R.id.chipGroup);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        CalendarView calendarView = view.findViewById(R.id.calendarView);

        recyclerView = view.findViewById(R.id.CalendarRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        // Load favorites
        boolean hasFavorites = false;
        for (Versions version : calendarVersionsList) {
            try {
                if (versionsAdapter.loadFavoriteState(version.getclubName())) {
                    selectedItems.add(version.getclubName());
                    hasFavorites = true;
                }
            } catch (Exception e) {
                Log.e("CalendarFragment", "Error loading favorite state", e);
            }
        }

        if (hasFavorites && !selectedItems.contains("Favorites")) {
            addChip("Favorites");
            selectedItems.add("Favorites");
        }

//        SetRecyclerView();
//
//        // Set up RecyclerView adapter
//        eventAdapter = new EventAdapter(eventList, requireContext()); // Use the class-level variable
//        recyclerView.setAdapter(eventAdapter);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                filterEvents(year, month, dayOfMonth);
//                // This is where you'll display events based on date
//                // Create a Calendar instance for the selected date
//                Calendar selectedDate = Calendar.getInstance();
//                selectedDate.set(year, month, dayOfMonth);
//                selectedDate.set(Calendar.HOUR_OF_DAY, 0);
//                selectedDate.set(Calendar.MINUTE, 0);
//                selectedDate.set(Calendar.SECOND, 0);
//                selectedDate.set(Calendar.MILLISECOND, 0);
//
//                // Filter the eventList to only include events that occur on the selected date
//                List<Event> filteredEvents = new ArrayList<>();
//                for (Event event : eventList) {
//                    Calendar eventDate = event.getDateCalendar();
//                    eventDate.set(Calendar.HOUR_OF_DAY, 0);
//                    eventDate.set(Calendar.MINUTE, 0);
//                    eventDate.set(Calendar.SECOND, 0);
//                    eventDate.set(Calendar.MILLISECOND, 0);
//                    if (eventDate.equals(selectedDate) && selectedItems.contains(event.getSport())) {
//                        filteredEvents.add(event);
//                    }
//                }
//
//                // Update the RecyclerView adapter with the filtered list of events
//                eventAdapter = new EventAdapter(filteredEvents, requireContext());
//                recyclerView.setAdapter(eventAdapter);

                // Display the date in a Toast
                //Toast.makeText(requireContext(), date.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addChip(String text) {
        Chip chip = new Chip(requireContext());
        chip.setText(text);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
                selectedItems.remove(text);

                // If the "Favorites" chip is removed, remove all favorite items from selectedItems
                if (text.equals("Favorites")) {
                    for (Versions version : calendarVersionsList) {
                        if (versionsAdapter.loadFavoriteState(version.getclubName())) {
                            selectedItems.remove(version.getclubName());
                        }
                    }
                }
            }
        });

        chipGroup.addView(chip);

        // Filter the events after adding a chip
        Calendar calendar = Calendar.getInstance();
        filterEvents(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void SetRecyclerView() {
        EventAdapter eventAdapter = new EventAdapter(eventList, requireContext());
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(true);
    }
    private void initData() {
          eventList = PracticeParser.parsePractices(requireContext());
    }


    private void filterEvents(int year, int month, int dayOfMonth) {
        // Create a Calendar instance for the selected date
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);
        selectedDate.set(Calendar.HOUR_OF_DAY, 0);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);

        // Filter the eventList to only include events that occur on the selected date and are selected in the chip group
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : eventList) {
            Calendar eventDate = event.getDateCalendar();
            eventDate.set(Calendar.HOUR_OF_DAY, 0);
            eventDate.set(Calendar.MINUTE, 0);
            eventDate.set(Calendar.SECOND, 0);
            eventDate.set(Calendar.MILLISECOND, 0);

            // Load favorites
            // Create an instance of VersionsAdapter to access the loadFavoriteState method
//            List<Versions> calendarVersionsList = VersionsAdapter.getVersionsList();
//            VersionsAdapter versionsAdapter = new VersionsAdapter(calendarVersionsList, requireContext());

            if (eventDate.equals(selectedDate) &&
                    (selectedItems.contains(event.getSport()) ||
                            (selectedItems.contains("Favorites") && versionsAdapter.loadFavoriteState(event.getSport())))) {
                filteredEvents.add(event);
            }
        }

        // Update the RecyclerView adapter with the filtered list of events
        eventAdapter = new EventAdapter(filteredEvents, requireContext());
        recyclerView.setAdapter(eventAdapter);
    }


}
