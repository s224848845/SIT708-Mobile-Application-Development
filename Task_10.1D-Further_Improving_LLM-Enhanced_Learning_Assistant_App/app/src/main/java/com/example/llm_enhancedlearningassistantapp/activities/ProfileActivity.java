package com.example.llm_enhancedlearningassistantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.utils.PrefManager;

public class ProfileActivity extends AppCompatActivity {

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefManager = new PrefManager(this);

        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvPlan = findViewById(R.id.tvPlan);
        TextView tvTotalQuestions = findViewById(R.id.tvTotalQuestions);
        TextView tvCorrectAnswers = findViewById(R.id.tvCorrectAnswers);
        TextView tvIncorrectAnswers = findViewById(R.id.tvIncorrectAnswers);

        Button btnShare = findViewById(R.id.btnShareProfile);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnUpgrade = findViewById(R.id.btnUpgrade);

        tvUsername.setText(prefManager.getUsername());
        tvEmail.setText(prefManager.getEmail());
        tvPlan.setText("Current Plan: " + prefManager.getAccountPlan());

        tvTotalQuestions.setText(String.valueOf(prefManager.getTotalQuestions()));
        tvCorrectAnswers.setText(String.valueOf(prefManager.getCorrectAnswers()));
        tvIncorrectAnswers.setText(String.valueOf(prefManager.getIncorrectAnswers()));

        btnBack.setOnClickListener(v -> finish());

        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        btnUpgrade.setOnClickListener(v -> startActivity(new Intent(this, UpgradeActivity.class)));
        btnShare.setOnClickListener(v -> shareProfile());
    }

    private void shareProfile() {
        String profileText =
                "LearnMate AI Profile\n\n" +
                        "Student: " + prefManager.getUsername() + "\n" +
                        "Email: " + prefManager.getEmail() + "\n" +
                        "Current Plan: " + prefManager.getAccountPlan() + "\n\n" +
                        "Total Questions: " + prefManager.getTotalQuestions() + "\n" +
                        "Correct Answers: " + prefManager.getCorrectAnswers() + "\n" +
                        "Incorrect Answers: " + prefManager.getIncorrectAnswers() + "\n\n" +
                        "Shared from LearnMate AI Learning Assistant.";

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My LearnMate AI Profile");
        sendIntent.putExtra(Intent.EXTRA_TEXT, profileText);

        startActivity(Intent.createChooser(sendIntent, "Share Profile"));
    }
}