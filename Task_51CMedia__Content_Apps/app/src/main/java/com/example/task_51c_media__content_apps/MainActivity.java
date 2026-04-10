package com.example.task_51c_media__content_apps;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task_51c_media__content_apps.istream.LoginActivity;
import com.example.task_51c_media__content_apps.sportsnews.SportsNewsActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnSportsApp;
    private Button btnIStreamApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Entry screen for both subtasks
        btnSportsApp = findViewById(R.id.btnSportsApp);
        btnIStreamApp = findViewById(R.id.btnIStreamApp);

        btnSportsApp.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SportsNewsActivity.class)));

        btnIStreamApp.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }
}