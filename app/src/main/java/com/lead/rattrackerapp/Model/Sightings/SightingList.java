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
    private DatabaseReference mDatabase;


    private SightingList() {
        data = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
    public List getData() {
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
        if (data.size() < amount) {
            amount = data.size();
        }
        List smallData = new ArrayList(amount);
        for (int i = data.size() - 1; i >= (data.size() - amount); i--) {
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
        List dateRange = new ArrayList();
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
            List subData = new ArrayList(data.size());
            for (int i = 0; i < data.size(); i++) {
                subData.add(data.get(i));
            }
            return subData;
        } else {
            List subData = new ArrayList(end - start);
            for (int i = start; i < end; i++) {
                subData.add(data.get(i));
            }
            return subData;
        }
    }
}
