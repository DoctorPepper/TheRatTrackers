package com.lead.rattrackerapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.lead.rattrackerapp.Model.Sightings.Sighting;
import com.lead.rattrackerapp.Model.Sightings.SightingList;

import java.util.List;
import java.util.Locale;

/**
 * Creates an Activity for the map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, InfoWindowAdapter {

    private FusedLocationProviderClient mLocationClient;
    private int mLocationPermissionRequest = 1;

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = null;
        try {

            // Getting view from the layout file info_window_layout
            v = getLayoutInflater().inflate(R.layout.custom_info_window,
                    (ViewGroup) findViewById(R.layout.activity_maps));

            TextView sightingNo = v.findViewById(R.id.sightingNo);
            sightingNo.setText(marker.getTitle());

            TextView dateTxt = v.findViewById(R.id.dateTxt);
            dateTxt.setText(String.format(Locale.US, getResources().getString(R.string.date_label),
                    currList.get((Integer) marker.getTag()).getDate()));

            // Getting reference to the TextView to set latitude
            TextView addressTxt = v.findViewById(R.id.addressTxt);
            addressTxt.setText(String.format(Locale.US, getResources().getString(R.string.address_label),
                    currList.get(((Integer) marker.getTag())).getAddress()));

            TextView cityTxt =  v.findViewById(R.id.cityTxt);
            cityTxt.setText(String.format(Locale.US, getResources().getString(R.string.city_label),
                    currList.get(((Integer) marker.getTag())).getCity()));

            TextView boroughTxt = v.findViewById(R.id.boroughTxt);
            boroughTxt.setText(String.format(Locale.US, getResources().getString(R.string.borough_label),
                    currList.get(((Integer) marker.getTag())).getBorough().toString()));

            TextView latitudeTxt = v.findViewById(R.id.latitudeTxt);
            latitudeTxt.setText(String.format(Locale.US, getResources().getString(R.string.lat_label),
                    Double.toString(currList.get(((Integer) marker.getTag())).getLatitude())));

            TextView longitudeTxt = v.findViewById(R.id.longitudeTxt);
            longitudeTxt.setText(String.format(Locale.US, getResources().getString(R.string.long_label),
                    Double.toString(currList.get(((Integer) marker.getTag())).getLongitude())));

        } catch (Exception ev) {
            System.out.print(ev.getMessage());
        }

        return v;
    }

    private GoogleMap mMap;

    private List<Sighting> currList;

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


        Button reportButton = findViewById(R.id.report_mapscreen);
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
        long start = getIntent().getLongExtra("start", 0);
        long end = getIntent().getLongExtra("end", 0);
        //System.err.println("From " + start + " to " + end);
        currList = SightingList.getInstance().getDateRangeDate(start, end);
        mLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
        boolean fineCheck = (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                == PackageManager.PERMISSION_GRANTED;
        boolean coarseCheck = (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION))
                == PackageManager.PERMISSION_GRANTED;
        if (!fineCheck && !coarseCheck) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, mLocationPermissionRequest);
        }
        if (fineCheck || coarseCheck) {
            mLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng loc = new LatLng(location.getLatitude(),
                                        location.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                            } else {
                                LatLng nyc = new LatLng(40.73, -73.93);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                            }
                        }
                    });
        } else {
            LatLng nyc = new LatLng(40.73, -73.93);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
        LatLng nyc = new LatLng(40.73, -73.93);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        int index = 0;
        for (Sighting s : currList) {
            LatLng p = new LatLng(s.getLatitude(), s.getLongitude());
            Marker newMarker = mMap.addMarker(new MarkerOptions()
                    .position(p)
                    .alpha(0.5f)
                    .title("Sighting #" + Integer.toString(s.getId()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rat_nowhiskers)));
            newMarker.setTag(index);
            System.out.println(index);
            index++;
        }
        mMap.setInfoWindowAdapter(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == mLocationPermissionRequest) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean fineCheck = (ContextCompat.checkSelfPermission(
                        this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                        == PackageManager.PERMISSION_GRANTED;
                    boolean coarseCheck = (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION))
                        == PackageManager.PERMISSION_GRANTED;
                    mLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    LatLng loc = new LatLng(location.getLatitude(),
                                            location.getLongitude());
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                                } else {
                                    LatLng nyc = new LatLng(40.73, -73.93);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                                }
                            }
                        });
            }
        }
    }

}
