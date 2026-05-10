package com.example.task71lostandfoundapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Displays saved lost/found adverts on Google Maps.
 * The first map load shows all items.
 * The radius search button filters items within X km of the Melbourne search centre.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private DBHelper dbHelper;
    private EditText edtRadius;
    private Button btnSearchRadius;

    // Melbourne default location for testing
    private final double userLatitude = -37.8136;
    private final double userLongitude = 144.9631;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DBHelper(this);
        edtRadius = findViewById(R.id.edtRadius);
        btnSearchRadius = findViewById(R.id.btnSearchRadius);

        // Gets the SupportMapFragment from XML and waits until Google Map is ready.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Applies radius-based filtering when user enters distance and clicks Search.
        btnSearchRadius.setOnClickListener(v -> showItemsWithinRadius());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Enables interactive map controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);

        showAllItemsOnMap();
    }

    private void showAllItemsOnMap() {
        googleMap.clear();

        ArrayList<Advert> adverts = dbHelper.getAllAdverts();

        if (adverts.isEmpty()) {
            LatLng melbourne = new LatLng(userLatitude, userLongitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne, 12));
            Toast.makeText(this, "No saved items found", Toast.LENGTH_SHORT).show();
            return;
        }

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

        // Adds a marker for every saved advert.
        for (Advert advert : adverts) {
            LatLng itemLocation = new LatLng(advert.getLatitude(), advert.getLongitude());

            // Red marker for Lost items and green marker for Found items.
            float markerColour = advert.getPostType().equalsIgnoreCase("Lost")
                    ? BitmapDescriptorFactory.HUE_RED
                    : BitmapDescriptorFactory.HUE_GREEN;

            googleMap.addMarker(new MarkerOptions()
                    .position(itemLocation)
                    .title(advert.getPostType() + ": " + advert.getName())
                    .snippet(advert.getCategory() + " | " + advert.getLocation())
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColour)));

            boundsBuilder.include(itemLocation);
        }

        // Moves camera so all advert markers are visible.
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 120));
        Toast.makeText(this, adverts.size() + " item(s) shown on map", Toast.LENGTH_SHORT).show();
    }

    private void showItemsWithinRadius() {
        googleMap.clear();

        String radiusText = edtRadius.getText().toString().trim();

        if (radiusText.isEmpty()) {
            Toast.makeText(this, "Enter radius in km", Toast.LENGTH_SHORT).show();
            return;
        }

        // Database helper calculates distance and returns only nearby adverts.
        double radiusKm = Double.parseDouble(radiusText);

        ArrayList<Advert> adverts =
                dbHelper.getAdvertsWithinRadius(userLatitude, userLongitude, radiusKm);

        LatLng userLocation = new LatLng(userLatitude, userLongitude);

        googleMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .title("Search Centre: Melbourne")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        if (adverts.isEmpty()) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
            Toast.makeText(this, "No items found within " + radiusKm + " km", Toast.LENGTH_SHORT).show();
            return;
        }

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        boundsBuilder.include(userLocation);

        for (Advert advert : adverts) {
            LatLng itemLocation = new LatLng(advert.getLatitude(), advert.getLongitude());

            googleMap.addMarker(new MarkerOptions()
                    .position(itemLocation)
                    .title(advert.getPostType() + ": " + advert.getName())
                    .snippet(advert.getCategory() + " | " + advert.getLocation()));

            boundsBuilder.include(itemLocation);
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 120));
        Toast.makeText(this, adverts.size() + " nearby item(s) found", Toast.LENGTH_SHORT).show();
    }
}