package com.example.clubsportsappnew.ui.main_pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.clubsportsappnew.databinding.FragmentHomeBinding;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.parsers_and_adapters.adapters.Versions;
import com.example.clubsportsappnew.parsers_and_adapters.adapters.VersionsAdapter;
import com.example.clubsportsappnew.parsers_and_adapters.adapters.Event;
import com.example.clubsportsappnew.parsers_and_adapters.adapters.EventAdapter;
import com.example.clubsportsappnew.parsers_and_adapters.parsers.PracticeParser;
import com.example.clubsportsappnew.parsers_and_adapters.parsers.SportXmlParser;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView homeRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private List<Versions> favorites; // For managing favorites
    private VersionsAdapter versionsAdapter; // To load favorite states
    private ChipGroup filterChipGroup;
    private Set<String> selectedSports = new HashSet<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        homeRecyclerView = binding.getRoot().findViewById(R.id.HomeRecyclerView);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize the VersionsAdapter
        favorites = SportXmlParser.parseSports(requireContext());
        versionsAdapter = new VersionsAdapter(favorites, requireContext());

        // Initialize Filter Chips (if you're using a chip group)
        filterChipGroup =  root.findViewById(R.id.filterChipGroup); // Add filterChipGroup in your XML layout
        initializeFilterChips();

        // Load data and display initially
        loadAndFilterData();

        return root;
    }

    private void initializeFilterChips() {
        // Load favorites
        loadFavorites();

        List<String> sports = PracticeParser.parseSportNamesFromPractices(requireContext());
        sports.add(0, "Favorites"); // Add 'Favorites' as a filter
        for (String sport: sports) {
            addChip(sport);
        }
    }

    private void loadFavorites() {
        for (Versions version : favorites) {
            try {
                if (versionsAdapter.loadFavoriteState(version.getclubName())) {
                    selectedSports.add(version.getclubName());
                }
            } catch (Exception e) {
                // Handle errors loading favorites
            }
        }
    }

    private void addChip(String sportName) {
        Chip chip = new Chip(requireContext());
        chip.setText(sportName);
        chip.setCheckable(true);
        chip.setChecked(selectedSports.contains(sportName));

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedSports.add(sportName);
            } else {
                selectedSports.remove(sportName);
            }
            loadAndFilterData();
        });
        filterChipGroup.addView(chip);
    }

    private void loadAndFilterData() {
        eventList = PracticeParser.parsePractices(requireContext());
        filterEvents();
    }

    private void filterEvents() {
        List<Event> filteredEvents = new ArrayList<>();
        Calendar currentTime = Calendar.getInstance();

        for (Event event : eventList) {
            Calendar eventDate = event.getDateCalendar();
            eventDate.set(Calendar.HOUR_OF_DAY,0);
            eventDate.set(Calendar.MINUTE,0);
            eventDate.set(Calendar.SECOND,0);
            eventDate.set(Calendar.MILLISECOND,0);

            if (!event.getTime().equals("12:00 AM")) {
                if (eventDate.after(currentTime) && selectedSports.contains(event.getSport()) ||
                        (selectedSports.contains("Favorites") && versionsAdapter.loadFavoriteState(event.getSport()))) {
                    filteredEvents.add(event);
                }
            }
        }

        eventAdapter = new EventAdapter(filteredEvents, requireContext());
        homeRecyclerView.setAdapter(eventAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}