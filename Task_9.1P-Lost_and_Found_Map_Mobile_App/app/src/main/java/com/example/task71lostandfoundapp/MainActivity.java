package com.example.task71lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main home screen of the application.
 * Three main actions:
 * 1. Create a new lost/found advert
 * 2. View all saved adverts as a list
 * 3. View saved adverts on Google Map
 */

public class MainActivity extends AppCompatActivity {

    Button btnCreateAdvert, btnShowItems, btnShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAdvert = findViewById(R.id.btnCreateAdvert);
        btnShowItems = findViewById(R.id.btnShowItems);
        btnShowMap = findViewById(R.id.btnShowMap);

        // Opens the form screen for creating a new advert.
        btnCreateAdvert.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateAdvertActivity.class))
        );

        // Opens the list screen that displays all saved lost/found items.
        btnShowItems.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ItemListActivity.class))
        );

        // Opens the map screen where saved adverts are displayed using markers.
        btnShowMap.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MapActivity.class))
        );
    }
}