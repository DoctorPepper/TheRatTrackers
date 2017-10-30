package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView sightingList;
    Button logOutButton;
    FloatingActionButton mapButton;
    FloatingActionButton reportButton;
    FirebaseAuth mAuth;

    DatabaseReference mDatabase;

    /**
     * Initialize the activity
     *
     * @param savedInstanceState the Bundle object containing
     *                           the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get all of the user input buttons in order to later set listeners
        logOutButton = (Button) findViewById(R.id.button_log_out);
        mapButton = (FloatingActionButton) findViewById(R.id.button_main_map);
        reportButton = (FloatingActionButton) findViewById(R.id.button_reportSighting);
        sightingList = (RecyclerView) findViewById(R.id.sightings_list);
        sightingList.setLayoutManager(new LinearLayoutManager(this));

        //Get FirebaseAuth instance in order to keep track of the user's log-in/log-out
        mAuth = FirebaseAuth.getInstance();

        //Get database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Set up database query to only include last 50 sightings
        Query smallQuery = mDatabase.child("sighting").orderByChild("longDate").limitToLast(50);

        //Add 'single value' listener in order to add all sightings to view upon opening main
        smallQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SightingList.getInstance().reset();
                    for (DataSnapshot sighting: dataSnapshot.getChildren()) {
                        Sighting s = sighting.getValue(Sighting.class);
                        SightingList.getInstance().addSighting(s);
                    }
                final List<Sighting> currList = SightingList.getInstance().getData();
                RatDataAdapter rda = new RatDataAdapter(MainActivity.this, currList);
                rda.setClickListener(new RatDataAdapter.SightingClickListener() {
                    @Override
                    public void onItemClick(View v, int p) {
                        if (findViewById(R.id.info_card).getVisibility() == View.GONE) {
                            Sighting clickedSighting = currList.get(p);
                            findViewById(R.id.info_card).setVisibility(View.VISIBLE);
                            setTable(clickedSighting);
                        } else {
                            findViewById(R.id.info_card).setVisibility(View.GONE);
                        }
                    }
                });
                sightingList.setAdapter(rda);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Set log out listener in order to log the user out (firebase auth) and take them to log-in
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, StartScreen.class);
                startActivity(intent);
            }
        });
        //Set map button listener to take user to map activity
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DateSelectionScreen.class);
                startActivity(intent);
            }
        });
        //Set report button listener in order to take user to report screen
        reportButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(MainActivity.this, ReportSightingScreen.class);
                  startActivity(intent);
              }
        });
    }

    private void setTable(Sighting s) {
        TextView key = (TextView) findViewById(R.id.info_card_key);
        key.setText(String.valueOf(s.getId()));

        TextView date = (TextView) findViewById(R.id.info_card_date);
        date.setText(s.getDate());

        TextView locType = (TextView) findViewById(R.id.info_card_location_type);
        locType.setText(s.getLocationType());

        TextView zip = (TextView) findViewById(R.id.info_card_zip);
        zip.setText(s.getZip());

        TextView address = (TextView) findViewById(R.id.info_card_address);
        address.setText(s.getAddress());

        TextView city = (TextView) findViewById(R.id.info_card_city);
        city.setText(s.getCity());

        TextView borough = (TextView) findViewById(R.id.info_card_borough);
        borough.setText(s.getBorough().toString());

        TextView lon = (TextView) findViewById(R.id.info_card_long);
        lon.setText(String.valueOf(s.getLongitude()));

        TextView lat = (TextView) findViewById(R.id.info_card_lat);
        lat.setText(String.valueOf(s.getLatitude()));
    }
}
