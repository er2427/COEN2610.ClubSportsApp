package com.example.clubsportsappnew.ui.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.example.clubsportsappnew.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class PracticeParser {

    public static ArrayList<Event> parsePractices(Context context) {
        ArrayList<Event> eventList = new ArrayList<>();

        try {
            Resources resources = context.getResources();
            XmlResourceParser parser = resources.getXml(R.xml.practice_times);

            Calendar endDate = Calendar.getInstance();
            endDate.set(2024, Calendar.MAY, 15);

            int eventType = parser.getEventType();
            Event currentEvent = null;
            Calendar startTime = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("practice".equals(parser.getName())) {
                            currentEvent = new Event();
                            startTime = Calendar.getInstance();
                        } else if (currentEvent != null) {
                            if ("sportName".equals(parser.getName())) {
                                currentEvent.setSport(parser.nextText());
                            } else if ("description".equals(parser.getName())) {
                                currentEvent.setName(parser.nextText());
                            } else if ("location".equals(parser.getName())) {
                                currentEvent.setLocation(parser.nextText());
                            } else if ("day".equals(parser.getName())) {
                                String dayOfWeek = parser.nextText();
                                startTime = getNextDayOfWeek(dayOfWeek);
                            } else if ("type".equals(parser.getName())) {
                                currentEvent.setType(parser.nextText());
                            } else if ("startTimeHour".equals(parser.getName())) {
                                int hour = Integer.parseInt(parser.nextText());
                                startTime.set(Calendar.HOUR, hour);
                            } else if ("startTimeMinute".equals(parser.getName())) {
                                int minute = Integer.parseInt(parser.nextText());
                                startTime.set(Calendar.MINUTE, minute);
                            } else if ("startTimePeriod".equals(parser.getName())) {
                                String period = parser.nextText();
                                int amPm = "AM".equalsIgnoreCase(period) ? Calendar.AM : Calendar.PM;
                                startTime.set(Calendar.AM_PM, amPm);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("practice".equals(parser.getName()) && currentEvent != null) {
                            currentEvent.setDate(startTime);
                            currentEvent.setTime(startTime);
                            while(startTime.before(endDate)) {
                                eventList.add(currentEvent);
                                currentEvent = new Event(currentEvent);
                                startTime.add(Calendar.WEEK_OF_YEAR, 1);
                                currentEvent.setDate((Calendar) startTime.clone());
                                currentEvent.setTime((Calendar) startTime.clone());
                            }
                            currentEvent = null;
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            Log.e("XmlParser", "Error parsing XML", e);
        }

        return eventList;
    }

    private static Calendar getNextDayOfWeek(String dayOfWeek) {
        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int dayIndex = Arrays.asList(days).indexOf(dayOfWeek);

        Calendar date = Calendar.getInstance();
        int diff = dayIndex - date.get(Calendar.DAY_OF_WEEK);
        if (diff <= 0) {
            diff += 7;
        }

        date.add(Calendar.DAY_OF_MONTH, diff);
        return date;
    }
    public static ArrayList<String> parseSportNamesFromPractices(Context context) {
        ArrayList<String> sportNames = new ArrayList<>();

        try {
            Resources resources = context.getResources();
            XmlResourceParser parser = resources.getXml(R.xml.practice_times);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("sportName".equals(parser.getName())) {
                            sportNames.add(parser.nextText());
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            Log.e("XmlParser", "Error parsing XML", e);
        }

        return sportNames;
    }

}