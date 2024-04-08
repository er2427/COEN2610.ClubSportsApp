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

import com.example.clubsportsappnew.ui.Practice;

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
        holder.descriptionTxt.setText(versions.getDescription());
        holder.practice_times_container.removeAllViews();

        List<Practice> practices = versions.getPractices();
        boolean isExpandable = versionsList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.descriptionTxt.setVisibility(View.GONE);

        // Clicking button in directory fragment
        Button favorite_button = holder.itemView.findViewById(R.id.favorite_button); // Change to your actual ID
        favorite_button.setOnClickListener(view -> {
            Versions currentVersion = versionsList.get(position);
            currentVersion.setFavorite(!currentVersion.getFavorite());
            updateStarIcon(currentVersion, favorite_button); // Update the star visually
            saveFavoriteState(currentVersion.getclubName(), currentVersion.getFavorite());
        });
        updateStarIcon(versionsList.get(position), favorite_button);

        int previousPracticeId = -1;
        for(Practice practice: practices) {
            View practiceTimeView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.practice_time, null, false);

            int currentPracticeId = View.generateViewId();
            practiceTimeView.setId(currentPracticeId);

            if(previousPracticeId != -1){
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.BELOW, previousPracticeId);
                practiceTimeView.setLayoutParams(params);
            }
            TextView dayTextView = practiceTimeView.findViewById(R.id.day);
            TextView startHourTextView = practiceTimeView.findViewById(R.id.startHour);
            TextView startMinuteTextView = practiceTimeView.findViewById(R.id.startMinute);
            TextView endHourTextView = practiceTimeView.findViewById(R.id.endHour);
            TextView endMinuteTextView = practiceTimeView.findViewById(R.id.endMinute);
            TextView endPeriodTextView = practiceTimeView.findViewById(R.id.endPeriod);
            TextView locationTextView = practiceTimeView.findViewById(R.id.location);

            dayTextView.setText(practice.getDay());
            startHourTextView.setText(practice.getStartTimeHour());
            startMinuteTextView.setText(practice.getStartTimeMinute());
            endHourTextView.setText(practice.getEndTimeHour());
            endMinuteTextView.setText(practice.getEndTimeMinute());
            endPeriodTextView.setText(practice.getEndTimePeriod());
            locationTextView.setText(practice.getLocation());
            //if the sport doesn't have a practice, make it so nothing shows
            if(practice.getDay() == null || practice.getDay().isEmpty()){
                dayTextView.setVisibility(View.GONE);
            }
            else{
                holder.descriptionTxt.setVisibility(View.VISIBLE);
            }
            if(practice.getStartTimeHour() == null || practice.getStartTimeHour().isEmpty()){
                startHourTextView.setVisibility(View.GONE);
            }
            if(practice.getStartTimeMinute() == null || practice.getStartTimeMinute().isEmpty()){
                startMinuteTextView.setVisibility(View.GONE);
            }
            if(practice.getEndTimeHour() == null || practice.getEndTimeHour().isEmpty()){
                endHourTextView.setVisibility(View.GONE);
            }
            if(practice.getEndTimeMinute() == null || practice.getEndTimeMinute().isEmpty()){
                endMinuteTextView.setVisibility(View.GONE);
            }
            if(practice.getEndTimePeriod() == null || practice.getEndTimePeriod().isEmpty()){
                endPeriodTextView.setVisibility(View.GONE);
            }
            if(practice.getLocation() == null || practice.getLocation().isEmpty()){
                locationTextView.setVisibility(View.GONE);
            }
            holder.practice_times_container.addView(practiceTimeView);
            previousPracticeId = currentPracticeId;
        }
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

        TextView clubNameTxt, PresidentTxt, EmailTxt, descriptionTxt, dayTextView, startHourTextView, startMinuteTextView, endMinuteTextView, endHourTextView, endPeriodTextView, locationTextView, typeText;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        RelativeLayout practice_times_container;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            clubNameTxt = itemView.findViewById(R.id.clubName);
            PresidentTxt = itemView.findViewById(R.id.president);
            EmailTxt = itemView.findViewById(R.id.contact);
            descriptionTxt = itemView.findViewById(R.id.description);
            dayTextView = itemView.findViewById(R.id.day);
            startHourTextView = itemView.findViewById(R.id.startHour);
            startMinuteTextView = itemView.findViewById(R.id.startMinute);
            endMinuteTextView = itemView.findViewById(R.id.endMinute);
            endHourTextView = itemView.findViewById(R.id.endHour);
            endPeriodTextView = itemView.findViewById(R.id.endPeriod);
            locationTextView = itemView.findViewById(R.id.location);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            practice_times_container = itemView.findViewById(R.id.practice_times_container);

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
