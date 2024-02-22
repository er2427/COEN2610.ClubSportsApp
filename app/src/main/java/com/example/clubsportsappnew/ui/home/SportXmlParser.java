package com.example.clubsportsappnew.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.Versions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class SportXmlParser {

    public static ArrayList<Versions> parseSports(Context context) {
        ArrayList<Versions> versionsList = new ArrayList<Versions>();

        try {
            Resources resources = context.getResources();
            XmlResourceParser parser = resources.getXml(R.xml.sports); // Assuming R.xml.sports_data is your XML file

            int eventType = parser.getEventType();
            Versions currentSport = null;

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

        } catch (XmlPullParserException | IOException e) {
            Log.e("XmlParser", "Error parsing XML", e);
        }

        return versionsList;
    }

}
