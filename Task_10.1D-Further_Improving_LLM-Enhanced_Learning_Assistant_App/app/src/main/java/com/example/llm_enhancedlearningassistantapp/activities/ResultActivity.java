package com.example.llm_enhancedlearningassistantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.llm.LocalLearningAssistant;
import com.example.llm_enhancedlearningassistantapp.models.QuizHistory;
import com.example.llm_enhancedlearningassistantapp.models.QuizQuestion;
import com.example.llm_enhancedlearningassistantapp.utils.PrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView tvScore, tvSummary, tvPrompt, tvResponse;
    private ProgressBar progressBar;
    private ArrayList<QuizQuestion> questions;
    private String topic;

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
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnProfile = findViewById(R.id.btnProfile);

        topic = getIntent().getStringExtra("topic");
        if (topic == null) topic = "Learning Quiz";

        questions = (ArrayList<QuizQuestion>) getIntent().getSerializableExtra("questions");
        if (questions == null) questions = new ArrayList<>();

        int correct = 0;
        for (QuizQuestion q : questions) {
            if (q.isCorrect()) correct++;
        }

        saveResult(correct);

        tvScore.setText("Your Score: " + correct + " / " + questions.size());
        tvSummary.setText("Your quiz result has been saved to History. Tap below to get an AI explanation for your first answer.");
        tvPrompt.setText("Prompt will appear here");
        tvResponse.setText("Response will appear here");

        btnExplain.setOnClickListener(v -> explainFirstQuestion());

        btnContinue.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, HomeActivity.class));
            finish();
        });

        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }

    private void saveResult(int correct) {
        PrefManager prefManager = new PrefManager(this);
        prefManager.addQuizStats(questions.size(), correct);

        String dateTime = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(new Date());

        QuizHistory history = new QuizHistory(
                topic,
                dateTime,
                correct,
                questions.size(),
                questions
        );

        prefManager.addQuizHistory(history);
    }

    private void explainFirstQuestion() {
        if (questions.isEmpty()) {
            tvResponse.setText("No questions available.");
            return;
        }

        QuizQuestion question = questions.get(0);
        progressBar.setVisibility(View.VISIBLE);
        tvResponse.setText("Generating explanation...");

        assistant.explainAnswer(question, new LocalLearningAssistant.CallbackResult() {
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