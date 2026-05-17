package com.example.llm_enhancedlearningassistantapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.models.QuizHistory;
import com.example.llm_enhancedlearningassistantapp.models.QuizQuestion;
import com.example.llm_enhancedlearningassistantapp.utils.PrefManager;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout historyContainer;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageButton btnBack = findViewById(R.id.btnBack);
        historyContainer = findViewById(R.id.historyContainer);
        tvEmpty = findViewById(R.id.tvEmpty);

        btnBack.setOnClickListener(v -> finish());

        loadHistory();
    }

    private void loadHistory() {
        PrefManager prefManager = new PrefManager(this);
        List<QuizHistory> historyList = prefManager.getQuizHistory();

        historyContainer.removeAllViews();

        if (historyList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        tvEmpty.setVisibility(View.GONE);

        for (QuizHistory history : historyList) {
            TextView item = new TextView(this);
            item.setText(buildHistoryText(history));
            item.setTextSize(15f);
            item.setTextColor(0xFFFFFFFF);
            item.setPadding(28, 24, 28, 24);
            item.setBackgroundColor(0xFF0867D8);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 24);
            item.setLayoutParams(params);

            historyContainer.addView(item);
        }
    }

    private String buildHistoryText(QuizHistory history) {
        StringBuilder builder = new StringBuilder();

        builder.append(history.getTopic()).append("\n");
        builder.append(history.getDateTime()).append("\n");
        builder.append("Score: ").append(history.getScore())
                .append(" / ").append(history.getTotalQuestions()).append("\n\n");

        List<QuizQuestion> questions = history.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion q = questions.get(i);

            builder.append(i + 1).append(". ").append(q.getQuestion()).append("\n");

            int selectedIndex = q.getSelectedIndex();
            int correctIndex = q.getCorrectIndex();

            String selected = selectedIndex >= 0 && selectedIndex < q.getOptions().size()
                    ? q.getOptions().get(selectedIndex)
                    : "Not answered";

            String correct = correctIndex >= 0 && correctIndex < q.getOptions().size()
                    ? q.getOptions().get(correctIndex)
                    : "Unavailable";

            builder.append("Your answer: ").append(selected).append("\n");
            builder.append("Correct answer: ").append(correct).append("\n");
            builder.append(q.isCorrect() ? "Status: Correct ✓" : "Status: Incorrect ✗");
            builder.append("\n\n");
        }

        return builder.toString();
    }
}