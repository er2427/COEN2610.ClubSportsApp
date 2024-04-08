package com.example.clubsportsappnew.ui.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
        return inflater.inflate(R.layout.fragment_directory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setVisibility(View.GONE); // Initially hide RecyclerView

        searchView = view.findViewById(R.id.searchView);

        initData(); // Move initData() call before setupSearchView()

        setupSearchView();
    }

    @SuppressLint("StaticFieldLeak")
    private void initData() {
        versionsList = new ArrayList<>();
        versionsAdapter = new VersionsAdapter(versionsList, requireContext());
        recyclerView.setAdapter(versionsAdapter);

        // Fetch data asynchronously
        new AsyncTask<Void, Void, List<Versions>>() {
            @Override
            protected List<Versions> doInBackground(Void... voids) {
                return SportXmlParser.parseSports(requireContext());
            }

            @Override
            protected void onPostExecute(List<Versions> result) {
                versionsList.clear();
                versionsList.addAll(result);
                versionsAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView after data is loaded
            }
        }.execute();
    }

    private void setupSearchView() {
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
    }

    private void filterList(String text) {
        List<Versions> filteredList = new ArrayList<>();
        for (Versions version : versionsList) {
            if (version.getclubName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(version);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No Match Found", Toast.LENGTH_SHORT).show();
        } else {
            versionsAdapter.setFilteredList(filteredList);
        }
    }
}


