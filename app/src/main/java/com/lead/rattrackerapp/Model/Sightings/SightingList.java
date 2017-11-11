package com.lead.rattrackerapp.Model.Sightings;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ejjac on 10/7/2017.
 *
 * Container for the rat sighting objects
 */

public class SightingList {
    private static final SightingList ourInstance = new SightingList();

    /**
     * Get a new instance of the SightingList
     *
     * @return the new instance
     */
    public static SightingList getInstance() {
        return ourInstance;
    }

    private List<Sighting> data;
    private int nextKey = 0;

    private SightingList() {
        data = new ArrayList<>();
    }

    public void reset() {
        data = new ArrayList<>();
    }

    /**
     * Add a new Sighting
     *
     * @param s the new Sighting to be entered
     */
    public void addSighting(Sighting s) {
        data.add(s);
        nextKey = Math.max(nextKey, s.getId() + 1);
    }

    /**
     * Returns the number of data elements in the list
     *
     * @return the size of data
     */
    public int size() {
        return data.size();
    }

    /**
     * Get the next key we will use in the Database
     *
     * @return the next key
     */
    public int getNextKey() {
        return nextKey;
    }

    /**
     * Get the data of the List
     *
     * @return the data
     */
    public List<Sighting> getData() {
        return getSmallData(data.size());
    }

    /**
     * Get a small amount of data
     *
     * @throws IllegalArgumentException if amount is negative
     * @param amount the amount of data to get,
     *               which is less than the total size of data
     * @return the small amount of data
     */
    public List<Sighting> getSmallData(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        int num = amount;
        if (amount > data.size()) {
            num = data.size();
        }
        List<Sighting> smallData = new ArrayList<>(num);
        for (int i = data.size() - 1; i >= (data.size() - num); i--) {
            smallData.add(data.get(i));
        }
        return smallData;
    }

    /**
     * Gets a list of sightings within a date range
     *
     * @param start start date
     * @param end end date
     * @return the list of sightings within the range
     */
    public List<Sighting> getDateRangeDate(double start, double end) {
        List<Sighting> dateRange = new ArrayList<>();
        for (Sighting s : data) {
            if (s.getLongDate() >= start && s.getLongDate() <= end) {
                dateRange.add(s);
            }
        }
        return dateRange;
    }

    /**
     * Gets a subset of the data
     *
     * @param start marks the start of the subset
     * @param end marks the end of the subset
     * @return the subset of data
     */
    public List<Sighting> getSubsetData(int start, int end) {
        if ((end - start) < data.size()) {
            List<Sighting> subData = new ArrayList<>(data.size());
            for (int i = 0; i < data.size(); i++) {
                subData.add(data.get(i));
            }
            return subData;
        } else {
            List<Sighting> subData = new ArrayList<>(end - start);
            for (int i = start; i < end; i++) {
                subData.add(data.get(i));
            }
            return subData;
        }
    }

    /**
     * Creates a new instance of SightingList for use in testing
     *
     * @return a new instance of the SightingList class
     */
    public static SightingList create_Instance() {
        return new SightingList();
    }
}
