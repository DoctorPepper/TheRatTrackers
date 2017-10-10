package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView sightingList;
    TextView sightingInfo;
    Button logOutButton;
    Button mapButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOutButton = (Button) findViewById(R.id.button_log_out);
        mapButton = (Button) findViewById(R.id.button_main_map);
        sightingInfo = (TextView) findViewById(R.id.sighting_info);
        sightingList = (RecyclerView) findViewById(R.id.sightings_list);
        sightingList.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, StartScreen.class);
                startActivity(intent);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        SightingList.getInstance().loadSightings(getResources().openRawResource(R.raw.sightings));
        final List<Sighting> currList = SightingList.getInstance().getSmallData(50);
        RatDataAdapter rda = new RatDataAdapter(this, currList);
        rda.setClickListener(new RatDataAdapter.SightingClickListener() {
            @Override
            public void onItemClick(View v, int p) {
                sightingInfo.setText(currList.get(p).toString());
            }
        });
        sightingList.setAdapter(rda);
    }
}
