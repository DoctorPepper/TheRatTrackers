package com.lead.rattrackerapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private List<Sighting> currList;

    private Button reportButton;

    /**
     * Initialize the activity
     *
     * @param savedInstanceState the Bundle object containing
     *                           the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        reportButton = (Button) findViewById(R.id.report_mapscreen);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ReportSightingScreen.class);
                Bundle b = new Bundle();
                LatLng ll = mMap.getCameraPosition().target;
                b.putDouble("Lat", ll.latitude);
                b.putDouble("Lng", ll.longitude);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        SightingList list = SightingList.getInstance();
        if (list.getSize() == 0){
            SightingList.getInstance().loadSightings(getResources().openRawResource(R.raw.sightings));
        }
        currList = SightingList.getInstance().getSmallData(100);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * @param googleMap class of the Google Maps API
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng nyc = new LatLng(40.73, -73.93);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

        for (Sighting s : currList) {
            LatLng p = new LatLng(s.getLatitude(), s.getLongitude());
            mMap.addMarker(new MarkerOptions().position(p).alpha(0.5f));
        }
    }
}
