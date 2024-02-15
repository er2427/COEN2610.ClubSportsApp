package com.example.clubsportsappnew;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubsportsappnew.Versions;

import java.text.BreakIterator;
import java.util.List;

public class VersionsAdapter extends RecyclerView.Adapter<VersionsAdapter.VersionVH> {

    List<Versions> versionsList;

    public VersionsAdapter(List<Versions> versionsList) {
        this.versionsList = versionsList;
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
        holder.SemesterTxt.setText(versions.getSemester());
        holder.descriptionTxt.setText(versions.getDescription());

        boolean isExpandable = versionsList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return versionsList.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {

        TextView clubNameTxt, PresidentTxt, SemesterTxt, descriptionTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        public VersionVH(@NonNull View itemView) {
            super(itemView);

            clubNameTxt = itemView.findViewById(R.id.code_name);
            PresidentTxt = itemView.findViewById(R.id.version);
            SemesterTxt = itemView.findViewById(R.id.api_level);
            descriptionTxt = itemView.findViewById(R.id.description);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener((View v) -> {
                int position = getAbsoluteAdapterPosition();
                if(position!= RecyclerView.NO_POSITION) {
                    Versions versions = versionsList.get(getAbsoluteAdapterPosition());
                    versions.setExpandable(!versions.isExpandable());
                    notifyItemChanged(getAbsoluteAdapterPosition());
                    if(versions.isExpandable()) {
                        expandableLayout.setVisibility(View.VISIBLE);
                    } else {
                        expandableLayout.setVisibility(View.GONE);
                    }
                    Log.d("VersionAdapter", "Item clicked at Position: "+ position);
                }
            });
        }
    }

}
