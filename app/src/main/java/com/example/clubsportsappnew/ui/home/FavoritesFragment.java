package com.example.clubsportsappnew.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Versions> versionsList;
    private VersionsAdapter versionsAdapter;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false); //get the layout from the xml
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);

        // Set up the search view to filter the recycler view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        searchView.clearFocus();
        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize data
        initData();
        SetRecyclerView();

        // Set up RecyclerView adapter
        versionsAdapter = new VersionsAdapter(versionsList); // Use the class-level variable
        recyclerView.setAdapter(versionsAdapter);
    }


    private void filterList(String text) {
        List<Versions> filteredList = new ArrayList<>();
        for(Versions version : versionsList) {
            if(version.getclubName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(version);
            }
        }
        //if there's no results for the search, alert the user
        if(filteredList.isEmpty()){
            Toast.makeText(requireContext(), "No Match Found", Toast.LENGTH_SHORT).show();
        }
        else{
            versionsAdapter.setFilteredList(filteredList);
        }
    }

    private void SetRecyclerView() {
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);
    }
    private void initData() {
        versionsList = SportXmlParser.parseSports(requireContext());
//        versionsList = new ArrayList<>();
//        versionsList.add(new Versions("Baseball - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Bowling", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Basketball - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Basketball - Women", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Boxing", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Curling", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Equestrian", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Women's Strength Club", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("E-Sports", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Fencing", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Field Hockey", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Figure Skating", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Fishing", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Golf - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Golf - Women", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Gymnastics", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Ice Hockey", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Ju-Jitsu", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Lacrosse - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Lacrosse - Women", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Log Rolling", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Power Lifting", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Quidditch", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Rock Climbing", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Rowing", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Rugby - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Rugby - Women", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Running", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Sailing", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Ski and Snowboard", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Soccer - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Soccer - Women", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Softball", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Spikeball", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Swimming & Diving", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Tae Kwon Do", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Tennis", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Triathlon", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Water Polo", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Ultimate Frisbee - Women", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Ultimate Frisbee - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Volleyball - Men", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Volleyball - Women", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Pickleball", "President", "Spring 2024", "Description"));
//        versionsList.add(new Versions("Waterski & Wakeboard", "President", "Spring 2024", "Description"));
//        // Add more data if needed
    }
}

