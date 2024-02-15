package com.example.clubsportsappnew.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.Versions;
import com.example.clubsportsappnew.VersionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DirectoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Versions> versionsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_directory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize data
        initData();
        SetRecyclerView();

        // Set up RecyclerView adapter
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
        recyclerView.setAdapter(versionsAdapter);
    }

    private void SetRecyclerView() {
        VersionsAdapter versionsAdapter = new VersionsAdapter(versionsList);
        recyclerView.setAdapter(versionsAdapter);
        recyclerView.setHasFixedSize(true);
    }
    private void initData() {
        versionsList = new ArrayList<>();
        versionsList.add(new Versions("Baseball", "President", "Spring 2024", "Description"));
        // Add more data if needed
    }
}

