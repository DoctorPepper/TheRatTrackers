package com.lead.rattrackerapp.Model.Sightings;

/**
 * Created by ejjac on 10/7/2017.
 *
 * List of possible NYC boroughs
 */

enum Borough {
    MANHATTAN ("Manhatten"),
    STATEN_ISLAND ("Staten Island"),
    QUEENS ("Queens"),
    BROOKLYN ("Brooklyn"),
    BRONX ("Bronx"),
    NONE ("");

    private String name;

    Borough (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}