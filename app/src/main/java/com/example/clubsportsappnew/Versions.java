package com.example.clubsportsappnew;

public class Versions {

    private String clubName, President, Semester, description;
    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public Versions(String clubName, String President, String Semester, String description) {
        this.clubName = clubName;
        this.President = President;
        this.Semester = Semester;
        this.description = description;
        this.expandable = false;
    }

    public String getclubName() {
        return clubName;
    }

    public void setclubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPresident() {
        return President;
    }

    public void setPresident(String President) {
        this.President = President;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String Semester) {
        this.Semester = Semester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Presidents{" +
                "clubName='" + clubName + '\'' +
                ", President='" + President + '\'' +
                ", Semester='" + Semester + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
