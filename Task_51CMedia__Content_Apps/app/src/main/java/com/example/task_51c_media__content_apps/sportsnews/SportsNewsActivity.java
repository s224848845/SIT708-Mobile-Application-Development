package com.example.task_51c_media__content_apps.sportsnews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.sportsnews.fragment.HomeFragment;

public class SportsNewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_news);

        // Single-activity architecture with fragment navigation
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }
    }
}