package com.example.clubsportsappnew.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false); //get the layout from the xml
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        initData();

        // Set up RecyclerView adapter
        versionsAdapter = new VersionsAdapter(versionsList, requireContext()); // Use the class-level variable
        recyclerView.setAdapter(versionsAdapter);

        // Initialize data
        SetRecyclerView();
    }

    private void SetRecyclerView() {
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList, requireContext());
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);
    }
    private void initData() {
        // Initialize versionsList
        versionsList = new ArrayList<>();

        // Load all versions
        List<Versions> allVersions = SportXmlParser.parseSports(requireContext());

        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("FavoritePrefs", Context.MODE_PRIVATE);

        // Load favorite states from SharedPreferences and update versionsList accordingly
        for (Versions version : allVersions) {
            boolean favorite = sharedPreferences.getBoolean(version.getclubName(), false);
            version.setFavorite(favorite);
            if (favorite) {
                versionsList.add(version);
            }
            Log.d("FavoritesFragment", "Club: " + version.getclubName() + ", Favorite: " + favorite);
        }
    }

}

