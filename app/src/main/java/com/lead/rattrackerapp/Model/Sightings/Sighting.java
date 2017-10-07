package com.lead.rattrackerapp.Model.Sightings;

/**
 * Created by ejjac on 10/7/2017.
 *
 * Holds data for a single rat sighting
 */

public class Sighting {
    private int id;
    private String date;
    private String locationType;
    private String zip;
    private String address;
    private String city;
    private Borough borough;
    private double longitude;
    private double latitude;

    Sighting(int id, String date, String locationType, String zip, String address, String city,
                    Borough borough, double longitude, double latitude) {
        this.id = id;
        this.date = date;
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Borough getBorough() {
        return borough;
    }

    public void setBorough(Borough borough) {
        this.borough = borough;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String shortLabel() {
        return "Sighting " + id + ": " + '\t' +
                "Date='" + date + '\'' + '\t' +
                "Address='" + address + '\'' +  '\t' +
                "City='" + city + '\'';
    }

    @Override
    public String toString() {
        return "Sighting " + id + ":\n" +
                "Date='" + date + '\'' + "\n" +
                "Location Type='" + locationType + '\'' + "\n" +
                "Zip=" + zip + "\n" +
                "Address='" + address + '\'' + "\n" +
                "City='" + city + '\'' + "\n" +
                "Borough=" + borough + "\n" +
                "Longitude=" + longitude + "\n" +
                "Latitude=" + latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sighting sighting = (Sighting) o;

        if (id != sighting.id) return false;
        if (zip != null ? !zip.equals(sighting.zip) : sighting.zip != null)
            return false;
        if (Double.compare(sighting.longitude, longitude) != 0) return false;
        if (Double.compare(sighting.latitude, latitude) != 0) return false;
        if (date != null ? !date.equals(sighting.date) : sighting.date != null) return false;
        if (locationType != null ? !locationType.equals(sighting.locationType) : sighting.locationType != null)
            return false;
        if (address != null ? !address.equals(sighting.address) : sighting.address != null)
            return false;
        if (city != null ? !city.equals(sighting.city) : sighting.city != null) return false;
        return borough == sighting.borough;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (locationType != null ? locationType.hashCode() : 0);
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
