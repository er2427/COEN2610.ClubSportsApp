package com.marquette.clubsportsappnew.parsers_and_adapters.adapters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Event
{
    private String name;
    private String sport;
    private String location;
    private String type;
    private Calendar date;
    private Calendar time;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    // Main Constructor for Event
    public Event(String name, String sport, String location, String type, Calendar date, Calendar time)
    {
        this.name = name;
        this.sport = sport;
        this.location = location;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public Event() {
        this("", "", "", "", Calendar.getInstance(), Calendar.getInstance()); // Set default description to an empty string
    }

    // Creating Dummy event object
    public Event(Event other) {
        this.name = other.name;
        this.sport = other.sport;
        this.location = other.location;
        this.type = other.type;
        this.date = (Calendar) other.date.clone();
        this.time = (Calendar) other.time.clone();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSport()
    {
        return sport;
    }

    public void setSport(String sport)
    {
        this.sport = sport;
    }

    public String getLocation()
    {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDate()
    {
        return dateFormat.format(date.getTime());
    }

    public Calendar getDateTimeCalendar() {
        Calendar dateTime = (Calendar) date.clone();
        dateTime.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        dateTime.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        return dateTime;
    }

    public Calendar getDateCalendar() {
        return date;
    }

    public void setDate(Calendar date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return timeFormat.format(time.getTime());
    }

    public void setTime(Calendar time)
    {
        this.time = time;
    }

}