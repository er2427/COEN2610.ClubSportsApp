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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false); //get the layout from the xml
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Here is where we will filter on fragments

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

    private void SetRecyclerView() {
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);
    }
    private void initData() {
        versionsList = SportXmlParser.parseSports(requireContext());
    }
}

