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
        ImageButton btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText("Hello, " + prefManager.getUsername());

        Set<String> interests = prefManager.getInterests();
        if (!interests.isEmpty()) {
            List<String> list = new ArrayList<>(interests);
            chosenTopic = list.get(0);
        }

        tvTopic.setText("Generated Task: " + chosenTopic);
        tvDescription.setText("A personalized quiz has been prepared from your selected interests. Use AI hint and explanation tools to support your learning.");

        btnStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra("topic", chosenTopic);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}