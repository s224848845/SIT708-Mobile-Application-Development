package com.example.llm_enhancedlearningassistantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.llm.LocalLearningAssistant;
import com.example.llm_enhancedlearningassistantapp.models.QuizQuestion;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView tvScore, tvSummary, tvPrompt, tvResponse;
    private ProgressBar progressBar;
    private ArrayList<QuizQuestion> questions;

    // Reuses the same local AI-style helper for explanation generation.
    private final LocalLearningAssistant assistant = new LocalLearningAssistant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tvScore);
        tvSummary = findViewById(R.id.tvSummary);
        tvPrompt = findViewById(R.id.tvPrompt);
        tvResponse = findViewById(R.id.tvResponse);
        progressBar = findViewById(R.id.progressBar);
        Button btnExplain = findViewById(R.id.btnExplain);
        Button btnContinue = findViewById(R.id.btnContinue);

        questions = (ArrayList<QuizQuestion>) getIntent().getSerializableExtra("questions");
        if (questions == null) questions = new ArrayList<>();

        int correct = 0;
        for (QuizQuestion q : questions) {
            if (q.isCorrect()) correct++;
        }

        tvScore.setText("Your Score: " + correct + " / " + questions.size());
        tvSummary.setText("Tap below to get an AI explanation for your first answer.");
        tvPrompt.setText("Prompt will appear here");
        tvResponse.setText("Response will appear here");


        // Lets the user request a learning explanation after seeing their score.
        btnExplain.setOnClickListener(v -> explainFirstQuestion());

        // Sends the user back to the dashboard for another task attempt.
        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, HomeActivity.class));
            finish();
        });
    }

    private void explainFirstQuestion() {
        if (questions.isEmpty()) {
            tvResponse.setText("No questions available.");
            return;
        }

        QuizQuestion question = questions.get(0);
        progressBar.setVisibility(View.VISIBLE);
        tvResponse.setText("Generating explanation...");

        assistant.explainAnswer(question, new LocalLearningAssistant.Callback() {
            @Override
            public void onSuccess(String prompt, String response) {
                progressBar.setVisibility(View.GONE);
                tvPrompt.setText(prompt);
                tvResponse.setText(response);
            }

            @Override
            public void onFailure(String prompt, String error) {
                progressBar.setVisibility(View.GONE);
                tvPrompt.setText(prompt);
                tvResponse.setText(error);
            }
        });
    }
}