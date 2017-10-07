package com.lead.rattrackerapp.Model.Sightings;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ejjac on 10/7/2017.
 *
 * Container for the rat sighting objects
 */

public class SightingList {
    private static final SightingList ourInstance = new SightingList();

    public static SightingList getInstance() {
        return ourInstance;
    }

    private List<Sighting> data;

    private SightingList() {
        data = new ArrayList<>();
    }

    public void loadSightings(InputStream sightings) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(sightings, StandardCharsets.UTF_8));
            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",", -1);
                int id = (elements[0].length() > 0) ? Integer.parseInt(elements[0]) : -1;
                String date = elements[1];
                String locationType = elements[7];
                String zip = elements[8];
                String address = elements[9];
                String city = elements[16];
                Borough borough;
                switch (elements[23]) {
                    case ("MANHATTAN"):
                        borough = Borough.MANHATTAN;
                        break;
                    case ("QUEENS"):
                        borough = Borough.QUEENS;
                        break;
                    case ("BROOKLYN"):
                        borough = Borough.BROOKLYN;
                        break;
                    case ("STATEN ISLAND"):
                        borough = Borough.STATEN_ISLAND;
                        break;
                    case ("BRONX"):
                        borough = Borough.BRONX;
                        break;
                    default:
                        borough = Borough.NONE;
                        break;
                }
                Double latitude = (elements[49].length() > 0)
                        ? Double.parseDouble(elements[49]) : -1;
                Double longitude = (elements[50].length() > 0)
                        ? Double.parseDouble(elements[50]) : -1;
                addSighting(new Sighting(id, date, locationType, zip, address, city, borough,
                        longitude, latitude));
            }
            br.close();
        } catch (IOException e) {
            Log.e("Sightings", "Error reading assets", e);
        }
    }

    public void addSighting(Sighting s) {
        data.add(s);
    }

    public List getData() {
        return data;
    }

    public List<Sighting> getSmallData(int amount) {
        List smallData = new ArrayList(amount);
        for (int i = 0; i < amount; i++) {
            smallData.add(data.get(i));
        }
        return smallData;
    }

    public List<Sighting> getSubsetData(int start, int end) {
        List subData = new ArrayList(end - start);
        for (int i = start; i < end; i++) {
            subData.add(data.get(i));
        }
        return subData;
    }
}
