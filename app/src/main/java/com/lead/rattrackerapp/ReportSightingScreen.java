package com.lead.rattrackerapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.Borough;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportSightingScreen extends AppCompatActivity {

    private Button submitButton;
    private Button cancelButton;
    private String TAG = "ReportingScreen";
    private TextInputEditText addr;
    private TextInputEditText city;
    private TextInputEditText czip;
    private TextInputEditText locType;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Spinner boroughSpinner;

    private DatabaseReference mDatabase;
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

        //Get all user input fields to get information
        addr = (TextInputEditText) findViewById(R.id.addressReport);
        city = (TextInputEditText) findViewById(R.id.cityReport);
        czip = (TextInputEditText) findViewById(R.id.cityZip);
        locType = (TextInputEditText) findViewById(R.id.location_type);
        datePicker = (DatePicker) findViewById(R.id.date_input);
        timePicker = (TimePicker) findViewById(R.id.time_input);
        boroughSpinner = (Spinner) findViewById(R.id.borough_spinner);
        boroughSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                Borough.values()));
        submitButton = (Button) findViewById(R.id.submitReport);
        cancelButton = (Button) findViewById(R.id.cancelReport);

        //Get database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Set submit button listener to submit data and take user back to main
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

        //Set cancel button listener to leave this screen
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //If we got here through the maps activity, the bundle 'b' will have useful information.
        //If b is not null, get that information out and set it as the defaults
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

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return calendar.getTime();
    }

    /**
     * Submits all the data entered
     *
     */
    public void submitData() {
        //If any of the required fields are null, throw a toast and get out
        if (addr.getText() == null || city.getText() == null || czip.getText() == null) {
            Toast.makeText(getApplicationContext(), "You must fill all inputs",
                    Toast.LENGTH_SHORT).show();
        } else {
            //Get date from datepicker
            //TODO: Incorporate timePicker to get dateTime
            Date date = getDateFromPicker(datePicker);
            String dateString = DateFormat.format("MM/dd/yyyy hh:mm:ss a", date).toString();
            //Try to get geoCoordinates from Address String
            double[] geoCoords = new double[2];
            try {
                geoCoords = getLocationFromAddress(addr.getText().toString() +
                        ", " + city.getText().toString());
            } catch (Exception e) {
                //TODO: Figure this out further
            }

            //Create sighting based upon gathered information
            Sighting sighting = new Sighting(SightingList.getInstance().getNextKey(), dateString,
                    locType.getText().toString(), czip.getText().toString(),
                    addr.getText().toString(), city.getText().toString(),
                    (Borough) boroughSpinner.getSelectedItem(), geoCoords[0], geoCoords[1]);
            mDatabase.child("sighting").child(String.valueOf(sighting.getId())).setValue(sighting);
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
}
