package com.example.task_41p_personal_event_planner_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enables edge-to-edge layout so the app can draw behind system bars.
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Applies padding so content is not hidden behind the status bar or navigation bar.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Finds the NavHostFragment that manages fragment navigation in this activity.
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            // Gets the NavController from the NavHostFragment.
            NavController navController = navHostFragment.getNavController();

            // Connects the BottomNavigationView with the NavController,
            // allowing users to switch between fragments using bottom navigation.
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }
}