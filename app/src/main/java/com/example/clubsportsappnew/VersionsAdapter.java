package com.example.clubsportsappnew;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VersionsAdapter extends RecyclerView.Adapter<VersionsAdapter.VersionVH> {

    private static final String PREF_NAME = "FavoritePrefs";
    private SharedPreferences sharedPreferences;

    List<Versions> versionsList;

    public VersionsAdapter(List<Versions> versionsList, Context context) {
        this.versionsList = versionsList;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setFilteredList(List<Versions> versionsList) {
        this.versionsList = versionsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {
        Versions versions = versionsList.get(position);
        holder.clubNameTxt.setText(versions.getclubName());
        holder.PresidentTxt.setText(versions.getPresident());
        holder.EmailTxt.setText(versions.getEmail());
        holder.SemesterTxt.setText(versions.getSemester());
        holder.descriptionTxt.setText(versions.getDescription());

        boolean isExpandable = versionsList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        // Clicking button in directory fragment
        Button favorite_button = holder.itemView.findViewById(R.id.favorite_button); // Change to your actual ID
        favorite_button.setOnClickListener(view -> {
            Versions currentVersion = versionsList.get(position);
            currentVersion.setFavorite(!currentVersion.getFavorite());
            updateStarIcon(currentVersion, favorite_button); // Update the star visually
            saveFavoriteState(currentVersion.getclubName(), currentVersion.getFavorite());
        });
        updateStarIcon(versionsList.get(position), favorite_button);
    }

    private void saveFavoriteState(String clubName, boolean favorite) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(clubName, favorite);
        editor.apply();
    }

    public boolean loadFavoriteState(String clubName) {
        return sharedPreferences.getBoolean(clubName, false);
    }

    private void updateStarIcon(Versions version, Button favorite_button) {
        if (version.getFavorite()) {
            favorite_button.setBackgroundResource(R.drawable.menu_favorites_fill); // Use your filled star drawable
        } else {
            favorite_button.setBackgroundResource(R.drawable.menu_favorites); // Use your hollow star drawable
        }
    }

    @Override
    public int getItemCount() {
        return versionsList.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {

        TextView clubNameTxt, PresidentTxt, EmailTxt, SemesterTxt, descriptionTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            clubNameTxt = itemView.findViewById(R.id.code_name);
            PresidentTxt = itemView.findViewById(R.id.version);
            EmailTxt = itemView.findViewById(R.id.contact);
            SemesterTxt = itemView.findViewById(R.id.api_level);
            descriptionTxt = itemView.findViewById(R.id.description);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener((View v) -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Versions versions = versionsList.get(getAbsoluteAdapterPosition());
                    versions.setExpandable(!versions.isExpandable());
                    notifyItemChanged(getAbsoluteAdapterPosition());
                    if (versions.isExpandable()) {
                        expandableLayout.setVisibility(View.VISIBLE);
                    } else {
                        expandableLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

}
