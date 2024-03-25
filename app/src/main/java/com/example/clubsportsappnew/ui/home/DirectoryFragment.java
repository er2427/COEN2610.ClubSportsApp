package com.example.clubsportsappnew.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.Versions;
import com.example.clubsportsappnew.VersionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DirectoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Versions> versionsList;
    private VersionsAdapter versionsAdapter;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_directory, container, false); //get the layout from the xml
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

        // Set up RecyclerView adapter
        versionsAdapter = new VersionsAdapter(versionsList, requireContext()); // Use the class-level variable
        recyclerView.setAdapter(versionsAdapter);

        // Initialize data
        initData();
        SetRecyclerView();
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
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList, requireContext());
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);
    }
    private void initData() {
        versionsList = SportXmlParser.parseSports(requireContext());

        // Log the size of the versionsList
        Log.d("DirectoryFragment", "Versions list size: " + versionsList.size());

        // Load favorite states from SharedPreferences and update versionsList accordingly
        for (Versions version : versionsList) {
            boolean favorite = versionsAdapter.loadFavoriteState(version.getclubName());
            version.setFavorite(favorite);
            Log.d("DirectoryFragment", "Club: " + version.getclubName() + ", Favorite: " + favorite);
        }
    }

}

