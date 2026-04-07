package com.example.sit708_40hd_deakin_oneai_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.fragments.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * MainActivity is the entry point of the app.
 * It manages bottom navigation and switches between fragments.
 */
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Load default fragment (Home)
        loadFragment(new HomeFragment());

        // Handle tab clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_deakin) {
                selectedFragment = new DeakinFragment();
            } else if (itemId == R.id.nav_files) {
                selectedFragment = new FilesFragment();
            } else if (itemId == R.id.nav_schedule) {
                selectedFragment = new ScheduleFragment();
            } else if (itemId == R.id.nav_github) {
                selectedFragment = new GithubFragment();
            }

            return loadFragment(selectedFragment);
        });
    }

    /**
     * Helper method to switch fragments
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}