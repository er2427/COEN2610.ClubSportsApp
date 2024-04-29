package com.marquette.clubsportsappnew.parsers_and_adapters.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.marquette.clubsportsappnew.R;
import com.marquette.clubsportsappnew.parsers_and_adapters.parsers.SportXmlParser;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Filter extends Fragment {

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    ChipGroup chipGroup;
    Set<String> selectedItems = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        ArrayList<String> sportsNames = SportXmlParser.parseSportNames(requireContext());
        String[] item = sportsNames.toArray(new String[0]);

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

    private void addChip(String text) {
        Chip chip = new Chip(requireContext());
        chip.setText(text);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
                selectedItems.remove(text);
            }
        });

        chipGroup.addView(chip);
    }
}