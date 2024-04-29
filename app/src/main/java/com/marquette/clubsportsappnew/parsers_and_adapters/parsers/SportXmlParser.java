package com.marquette.clubsportsappnew.parsers_and_adapters.parsers;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.marquette.clubsportsappnew.R;
import com.marquette.clubsportsappnew.parsers_and_adapters.adapters.Versions;
import com.marquette.clubsportsappnew.parsers_and_adapters.adapters.Practice;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SportXmlParser {

    public static ArrayList<Versions> parseSports(Context context) {
        ArrayList<Versions> versionsList = new ArrayList<Versions>();
        HashMap<String, ArrayList<Practice>> practiceMap = new HashMap<>();

        try {
            Resources resources = context.getResources();
            XmlResourceParser parser = resources.getXml(R.xml.sports);
            XmlResourceParser parser2 = resources.getXml(R.xml.practice_times);

            int eventType = parser.getEventType();
            int eventType2 = parser2.getEventType();
            Practice currentPractice = null;
            Versions currentSport = null;
            //make it so that the practice times load into the correct sport
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("sport".equals(parser.getName())) {
                            currentSport = new Versions();
                        } else if (currentSport != null) {
                            switch (parser.getName()) {
                                case "sportName":
                                    currentSport.setclubName(parser.nextText());
                                    break;
                                case "contactName":
                                    currentSport.setPresident("President: " + parser.nextText());
                                    break;
                                case "contactEmail":
                                    currentSport.setEmail("Email: " + parser.nextText());
                                    break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("sport".equals(parser.getName()) && currentSport != null) {
                            versionsList.add(currentSport);
                            currentSport = null;
                        }
                        break;
                }
                eventType = parser.next();
            }

            while(eventType2 != XmlPullParser.END_DOCUMENT) {
                switch (eventType2) {
                    case XmlPullParser.START_TAG:
                        if ("practice".equals(parser2.getName())) {
                            currentPractice = new Practice();
                        } else if (currentPractice != null) {
                            switch (parser2.getName()) {
                                case "sportName":
                                    currentPractice.setSportName(parser2.nextText());
                                    break;
                                case "description":
                                    currentPractice.setDescription(parser2.nextText());
                                    break;
                                case "location":
                                    currentPractice.setLocation(parser2.nextText());
                                    break;
                                case "day":
                                    currentPractice.setDay(parser2.nextText());
                                    break;
                                case "startTimeHour":
                                    currentPractice.setStartTimeHour(parser2.nextText() + ":");
                                    break;
                                case "startTimeMinute":
                                    currentPractice.setStartTimeMinute(parser2.nextText());
                                    break;
                                case "endTimeHour":
                                    String endTimeHour = parser2.nextText();
                                    if(endTimeHour.equals("None")){
                                        currentPractice.setEndTimeHour(null);
                                    }
                                    else{
                                        currentPractice.setEndTimeHour(" - " + endTimeHour + ":");
                                    }
                                    break;
                                case "endTimeMinute":
                                    currentPractice.setEndTimeMinute(parser2.nextText());
                                    break;
                                case "endTimePeriod":
                                    currentPractice.setEndTimePeriod(parser2.nextText());
                                    break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("practice".equals(parser2.getName()) && currentPractice != null) {
                            if (practiceMap.containsKey(currentPractice.getSportName())) {
                                practiceMap.get(currentPractice.getSportName()).add(currentPractice);
                            } else {
                                ArrayList<Practice> practices = new ArrayList<>();
                                practices.add(currentPractice);
                                practiceMap.put(currentPractice.getSportName(), practices);
                            }
                            currentPractice = null;
                        }
                        break;
                }
                eventType2 = parser2.next();
            }
            for(Versions version: versionsList) {
                ArrayList<Practice> practices = practiceMap.get(version.getclubName());
                if(practices==null){
                    practices = new ArrayList<>();
                }
                version.setPractices(practices);
            }
        }


        catch (XmlPullParserException | IOException e) {
            Log.e("XmlParser", "Error parsing XML", e);
        }

        return versionsList;
    }

    public static ArrayList<String> parseSportNames(Context context) {
        ArrayList<String> sportNames = new ArrayList<>();

        try {
            Resources resources = context.getResources();
            XmlResourceParser parser = resources.getXml(R.xml.sports);

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
