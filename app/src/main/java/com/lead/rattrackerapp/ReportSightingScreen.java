package com.lead.rattrackerapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.Borough;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportSightingScreen extends AppCompatActivity {

    private Button submitButton;
    private Button cancelButton;
    private String TAG = "ReportingScreen";

    /**
     * Initialize the activity
     *
     * @param savedInstanceState the Bundle object containing
     *                           the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sightings);
        Bundle b = getIntent().getExtras();

        final TextInputEditText addr = (TextInputEditText) findViewById(R.id.addressReport);
        final TextInputEditText city = (TextInputEditText) findViewById(R.id.cityReport);
        final TextInputEditText czip = (TextInputEditText) findViewById(R.id.cityZip);
        final TextInputEditText locType = (TextInputEditText) findViewById(R.id.location_type);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.date_input);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.time_input);

        submitButton = (Button) findViewById(R.id.submitReport);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData(addr, city, czip, datePicker, timePicker, locType.getText().toString());
            }
        });

        cancelButton = (Button) findViewById(R.id.cancelReport);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (b != null) {
            double lat = b.getDouble("Lat");
            double lng = b.getDouble("Lng");
            Geocoder geoCoder = new Geocoder(getBaseContext());
            try {
                List<Address> matches = geoCoder.getFromLocation(lat, lng, 1);
                Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                addr.setText(bestMatch.getAddressLine(0));
                city.setText(bestMatch.getAdminArea());
                czip.setText(bestMatch.getPostalCode());
            } catch (Exception e) {
                Log.w(TAG, "Some or all info not displayed");
            }
        }
    }

    /**
     * Gets the date from the DatePicker
     *
     * @param datePicker the DatePicker object
     * @return the date of the calender
     */
    public Date getDateFromPicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    /**
     * Submits all the data entered
     *
     * @param addr the address entered
     * @param city the city entered
     * @param czip the zip code entered
     * @param datePicker the date entered
     * @param timePicker the time entered
     * @param locType the location type entered
     */
    public void submitData(TextInputEditText addr, TextInputEditText city, TextInputEditText czip,
                           DatePicker datePicker, TimePicker timePicker, String locType) {
        if (addr.getText() == null || city.getText() == null || czip.getText() == null) {
            Toast.makeText(getApplicationContext(), "You must fill all inputs",
                    Toast.LENGTH_SHORT).show();
        } else {
            DateFormat dateFormatter = DateFormat.getDateInstance();
            Date date = getDateFromPicker(datePicker);
            String dateString = dateFormatter.format(date);
            double[] geoCoords = new double[2];
            try {
                geoCoords = getLocationFromAddress(addr.getText().toString() +
                        ", " + city.getText().toString());
            } catch (Exception e) {
                //TODO: Figure this out further
            }
            Borough borough = Borough.NONE;
            try {
                borough = getBouroughFromAddress(addr.getText().toString() +
                        ", " + city.getText().toString());
                if (borough == null) {
                    borough = Borough.NONE;
                }
            } catch (Exception e) {
                //TODO: Figure this out further
            }
            Sighting sighting = new Sighting(SightingList.getInstance().getSize() + 1, dateString, locType,
                    czip.getText().toString(),
                    addr.getText().toString(),
                    city.getText().toString(), borough, geoCoords[0], geoCoords[1]);
            Log.v(TAG, sighting.toString());
            SightingList sightingList = SightingList.getInstance();
            sightingList.addSighting(sighting);
            Intent intent = new Intent(ReportSightingScreen.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Gets the location form the Address
     *
     * @param strAddress the address entered as a String
     * @return the location
     */
    public double[] getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            double[] geoCoords = new double[2];
            geoCoords[0] = (location.getLatitude());
            geoCoords[1] = (location.getLongitude());

            return geoCoords;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Gets the borough from the Address
     *
     * @param strAddress the address entered as a String
     * @return the borough
     */
    public Borough getBouroughFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return Borough.NONE;
            }
            Address location = address.get(0);
            String borough = location.getSubLocality();
            if (borough == null) {
                return null;
            }
            Log.v(TAG, borough);
            if (borough.equals(Borough.BRONX.getName())) {
                return Borough.BRONX;
            }
            if (borough.equals(Borough.MANHATTAN.getName())) {
                return Borough.MANHATTAN;
            }
            if (borough.equals(Borough.STATEN_ISLAND.getName())) {
                return Borough.STATEN_ISLAND;
            }
            if (borough.equals(Borough.QUEENS.getName())) {
                return Borough.QUEENS;
            }
            if (borough.equals(Borough.BROOKLYN.getName())) {
                return Borough.BRONX;
            } else {
                return Borough.NONE;
            }
        } catch (IOException e) {
            return Borough.NONE;
        }
    }

}
