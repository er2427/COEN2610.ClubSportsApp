package com.example.clubsportsappnew;

import com.example.clubsportsappnew.ui.Practice;

import java.util.List;

public class Versions {

    private String clubName, President, Email, description;
    private boolean expandable;
    private boolean favorite;
    private List<Practice> practices;

    public boolean isExpandable() {
        return expandable;
    }
    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public Versions(String clubName, String President, String Email, String description, boolean favorite) {
        this.clubName = clubName;
        this.President = President;
        this.Email = Email;
        this.description = description;
        this.expandable = false;
    }

    // Add a constructor with default description value
    public Versions() {
        this("", "", "", "Practice Information", false); // Set default description to an empty string
    }

    public String getclubName() { return clubName; }

    public void setclubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPresident() {
        return President;
    }

    public void setPresident(String President) { this.President = President; }

    public String getEmail() { return Email; }

    public void setEmail(String Email) { this.Email = Email; }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getFavorite() {
        return favorite;
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<Practice> getPractices() {
        return practices;
    }
    public void setPractices(List<Practice> practices) {
        this.practices = practices;
    }

    @Override
    public String toString() {
        return "Presidents{" +
                "clubName='" + clubName + '\'' +
                ", President='" + President + '\'' +
                ", Email = '" + Email;
    }
}
