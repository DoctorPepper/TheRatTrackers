package com.lead.rattrackerapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ReportSightingScreen extends AppCompatActivity {

    private Button submitButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sightings);
        Bundle b = getIntent().getExtras();

        TextInputEditText addr = (TextInputEditText) findViewById(R.id.addressReport);
        TextInputEditText city = (TextInputEditText) findViewById(R.id.cityReport);
        TextInputEditText czip = (TextInputEditText) findViewById(R.id.cityZip);

        submitButton = (Button) findViewById(R.id.submitReport);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                addr.setText(bestMatch.getThoroughfare());
                city.setText(bestMatch.getLocality());
                czip.setText(bestMatch.getPostalCode());
            } catch (Exception e) {
                System.out.println("o crap");
            }
        }


//        setContentView(R.layout.activity_report_sightings);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
