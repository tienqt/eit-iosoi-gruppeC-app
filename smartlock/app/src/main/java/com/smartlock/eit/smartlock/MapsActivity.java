package com.smartlock.eit.smartlock;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String bike_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent map_intent = getIntent();

        bike_id = map_intent.getStringExtra("BikeId");

        positioning_task pos = new positioning_task();
        pos.execute();
    }

    public class positioning_task extends AsyncTask<String, Void, Void> {

        String coordinates;
        String stolen_coordinates;

        @Override
        protected void onPostExecute(Void params) {

            String[] data = coordinates.split(",");
            String[] stolen_data = stolen_coordinates.split(",");

            float stolen_lat = Float.parseFloat(stolen_data[0]);
            float stolen_lng = Float.parseFloat(stolen_data[1]);

            if (data.length > 3) {
                float latitude = Float.parseFloat(data[2]);
                float longitude = Float.parseFloat(data[3]);

                LatLng marker = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(marker).title("Your bike."));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 14));
            }

            if (stolen_lat != 0 && stolen_lng != 0){

                LatLng stolen_marker = new LatLng(stolen_lat, stolen_lng);
                mMap.addMarker(new MarkerOptions().position(stolen_marker).title("Theft location."));

            }
        }

        @Override
        protected Void doInBackground(String... params) {

            coordinates = utils.getHttp("http://drawroulette.com/eit/getbikecoordinate.php?bikeid=" + bike_id, getApplicationContext());
            stolen_coordinates = utils.getHttp("http://drawroulette.com/eit/getstolencoordinate.php?bikeid=" + bike_id, getApplicationContext());

            return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
