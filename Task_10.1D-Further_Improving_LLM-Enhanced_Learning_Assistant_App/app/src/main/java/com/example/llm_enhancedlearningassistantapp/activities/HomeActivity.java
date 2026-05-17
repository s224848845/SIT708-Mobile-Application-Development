package com.example.llm_enhancedlearningassistantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private String chosenTopic = "Algorithms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PrefManager prefManager = new PrefManager(this);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        TextView tvTopic = findViewById(R.id.tvTopic);
        TextView tvDescription = findViewById(R.id.tvDescription);

        Button btnStartQuiz = findViewById(R.id.btnStartQuiz);
        Button btnProfile = findViewById(R.id.btnProfile);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnUpgrade = findViewById(R.id.btnUpgrade);
        ImageButton btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText("Hello, " + prefManager.getUsername());

        Set<String> interests = prefManager.getInterests();
        if (!interests.isEmpty()) {
            List<String> list = new ArrayList<>(interests);
            chosenTopic = list.get(0);
        }

        tvTopic.setText("Generated Task: " + chosenTopic);
        tvDescription.setText("A personalised quiz has been prepared from your selected interests. Use AI hints, explanations, history tracking, sharing, and upgrade features to support your learning.");

        btnStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra("topic", chosenTopic);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        btnUpgrade.setOnClickListener(v -> startActivity(new Intent(this, UpgradeActivity.class)));

        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }
}