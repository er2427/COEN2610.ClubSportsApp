package com.example.clubsportsappnew.parsers_and_adapters.adapters;

public class Practice {
    private String sportName;
    private String description;
    private String location;
    private String type;
    private String day;
    private String startTimeHour;
    private String startTimeMinute;
    private String startTimePeriod;
    private String endTimeHour;
    private String endTimeMinute;
    private String endTimePeriod;

    public Practice(String sportName, String description, String location, String day, String startTimeHour, String startTimeMinute, String startTimePeriod, String endTimeHour, String endTimeMinute, String endTimePeriod) {
        this.sportName = sportName;
        this.description = description;
        this.location = location;
        this.day = day;
        this.startTimeHour = startTimeHour;
        this.startTimeMinute = startTimeMinute;
        this.startTimePeriod = startTimePeriod;
        this.endTimeHour = endTimeHour;
        this.endTimeMinute = endTimeMinute;
        this.endTimePeriod = endTimePeriod;
    }

    public Practice() {
        this("", "", "", "", "", "", "", "", "", "");
    }
    // getters and setters for all fields
    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(String startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public String getStartTimeMinute() {
        return startTimeMinute;
    }

    public void setStartTimeMinute(String startTimeMinute) {
        this.startTimeMinute = startTimeMinute;
    }

    public String getStartTimePeriod() {
        return startTimePeriod;
    }

    public void setStartTimePeriod(String startTimePeriod) {
        this.startTimePeriod = startTimePeriod;
    }

    public String getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(String endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public String getEndTimeMinute() {
        return endTimeMinute;
    }

    public void setEndTimeMinute(String endTimeMinute) {
        this.endTimeMinute = endTimeMinute;
    }

    public String getEndTimePeriod() {
        return endTimePeriod;
    }

    public void setEndTimePeriod(String endTimePeriod) {
        this.endTimePeriod = endTimePeriod;
    }
}
