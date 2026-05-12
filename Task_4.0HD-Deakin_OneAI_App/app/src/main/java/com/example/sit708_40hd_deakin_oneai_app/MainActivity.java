package com.example.sit708_40hd_deakin_oneai_app;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.fragments.AboutFragment;
import com.example.sit708_40hd_deakin_oneai_app.fragments.AIFragment;
import com.example.sit708_40hd_deakin_oneai_app.fragments.DeakinFragment;
import com.example.sit708_40hd_deakin_oneai_app.fragments.HomeFragment;
import com.example.sit708_40hd_deakin_oneai_app.fragments.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * MainActivity controls the main navigation structure of Deakin OneAI.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private int selectedItemId = R.id.nav_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            selectedItemId = item.getItemId();

            if (selectedItemId == R.id.nav_home) {
                return loadFragment(new HomeFragment());
            } else if (selectedItemId == R.id.nav_ai) {
                return loadFragment(new AIFragment());
            } else if (selectedItemId == R.id.nav_schedule) {
                return loadFragment(new ScheduleFragment());
            } else if (selectedItemId == R.id.nav_deakin) {
                return loadFragment(new DeakinFragment());
            } else if (selectedItemId == R.id.nav_about) {
                return loadFragment(new AboutFragment());
            }

            return false;
        });

        setupBackNavigation();
    }

    /**
     * Android 16 compatible back navigation approach.
     * If the user is not on Home, pressing back returns to Home.
     */
    private void setupBackNavigation() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                // If a WebView/browser fragment was opened from Hub, return to previous screen first.
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    return;
                }

                if (selectedItemId != R.id.nav_home) {
                    selectedItemId = R.id.nav_home;
                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
                    loadFragment(new HomeFragment());
                } else {
                    finish();
                }
            }
        });
    }

    /**
     * Replaces the current screen with the selected fragment.
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) {
            return false;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        return true;
    }
}