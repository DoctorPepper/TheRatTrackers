package com.lead.rattrackerapp.Model.Sightings;

/**
 * Created by ejjac on 10/7/2017.
 *
 * Holds data for a single rat sighting
 */

public class Sighting {
    private int id;
    private String date;
    private String locType;
    private String zip;
    private String address;
    private String city;
    private Borough borough;
    private double longitude;
    private double latitude;
    private long longDate;

    /**
     * Full constructor
     * @param id the Sighting's ID number
     * @param date the Sighting's date
     * @param locationType the Sighting's location type
     * @param zip the Sighting's zip code
     * @param address the Sighting's address
     * @param city the Sighting's city
     * @param borough the Sighting's borough
     * @param longitude the Sighting's longitude
     * @param latitude the Sighting's latitude
     * @param longDate the Sighting's date
     */
    public Sighting(int id, String date, String locationType, String zip, String address, String city,
                    Borough borough, double longitude, double latitude, long longDate) {
        this.id = id;
        this.date = date;
        this.locType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.longitude = longitude;
        this.latitude = latitude;
        this.longDate = longDate;


    }

    /**
     * Empty constructor
     */
    public Sighting() {
    }

    /**
     * Gets the ID of the Sighting
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the Sighting
     *
     * @param id the new ID to be entered
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the date of the Sighting
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the Sighting
     *
     * @param date the new date to be entered
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the location type of the Sighting
     *
     * @return the location type
     */
    public String getLocationType() {
        return locType;
    }

    /**
     * Set the location type of the Sighting
     *
     * @param locationType the new location type to be entered
     */
    public void setLocationType(String locationType) {
        this.locType = locationType;
    }

    /**
     * Get the zip code of the Sighting
     *
     * @return the zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Set the zip code of the Sighting
     *
     * @param zip the new zip code to be entered
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Get the address of the Sighting
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of the Sighting
     *
     * @param address the new address to be entered
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the city of the Sighting
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city of the sighting
     *
     * @param city the new city to be entered
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the borough of the Sighting
     *
     * @return the borough
     */
    public Borough getBorough() {
        return borough;
    }

    /**
     * Set the borough of the Sighting
     *
     * @param borough the new borough to be entered
     */
    public void setBorough(Borough borough) {
        this.borough = borough;
    }

    /**
     * Get the longitude of the Sighting
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude of the Sighting
     *
     * @param longitude the new longitude to be entered
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the latitude of the Sighting
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude of the Sighting
     *
     * @param latitude the new latitude to be entered
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the date of the Sightings
     * @param longDate the new date to be entered
     */
    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }

    /**
     * gets the date of the Sighting
     * @return the date
     */
    public long getLongDate() {
        return longDate;
    }

    /**
     * Shorter label which will be used as the label in the recycle view
     *
     * @return the sort label
     */
    public String shortLabel() {
        return "Sighting #" + id + ": "   + '\n' +
                "Date: "    + date        + '\n' +
                "Address: " + address     + '\n' +
                "City: "    + city;
    }

    /**
     * Full informational label
     *
     * @return full label info
     */
    @Override
    public String toString() {
        return "Sighting #"         + id + ": "   + "\n" +
                "Date: "            + date        + "\n" +
                "Location Type: "   + locType     + "\n" +
                "Zip: "             + zip         + "\n" +
                "Address: "         + address     + "\n" +
                "City: "            + city        + "\n" +
                "Borough: "         + borough     + "\n" +
                "Longitude: "       + longitude   + "\n" +
                "Latitude: "        + latitude;
    }

    /**
     * Determines whether a Sighting is equal to another Sighting
     *
     * @param o the Object to be compared to another Sighting
     * @return true if the Sightings are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sighting sighting = (Sighting) o;

//        if (id != sighting.id) return false;
//        if (zip != null ? !zip.equals(sighting.zip) : sighting.zip != null)
//            return false;
//        if (Double.compare(sighting.longitude, longitude) != 0) return false;
//        if (Double.compare(sighting.latitude, latitude) != 0) return false;
//        if (date != null ? !date.equals(sighting.date) : sighting.date != null) return false;
//        if (locType != null ? !locType.equals(sighting.locType) : sighting.locType != null) {
//            return false;
//        }
//        if (address != null ? !address.equals(sighting.address) : sighting.address != null) {
//            return false;
//        }

        return (id == sighting.id) &&
                (zip != null ? zip.equals(sighting.zip) : sighting.zip == null) &&
                (Double.compare(sighting.longitude, longitude) == 0) &&
                (Double.compare(sighting.latitude, latitude) == 0) &&
                (date != null ? date.equals(sighting.date) : sighting.date == null) &&
                (locType != null ? locType.equals(sighting.locType) : sighting.locType == null) &&
                (address != null ? address.equals(sighting.address) : sighting.address == null) &&
                (city != null ? city.equals(sighting.city) : sighting.city == null)
                && borough == sighting.borough;

    }

    /**
     * Create the hashcode for the Sighting
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (locType != null ? locType.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (borough != null ? borough.hashCode() : 0);
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
