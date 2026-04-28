package com.example.llm_enhancedlearningassistantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.llm.LocalLearningAssistant;
import com.example.llm_enhancedlearningassistantapp.models.QuizApiResponse;
import com.example.llm_enhancedlearningassistantapp.models.QuizQuestion;
import com.example.llm_enhancedlearningassistantapp.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    // UI components for displaying the quiz and AI-generated help.
    private TextView tvTopic, tvQuestion, tvPrompt, tvResponse, tvQuestionNumber;
    private RadioGroup rgOptions;
    private Button btnHint, btnNext, btnSubmit;
    private ProgressBar progressBar;

    // Stores the quiz questions currently being attempted.
    private ArrayList<QuizQuestion> questions = new ArrayList<>();

    // Tracks which question is currently shown on screen.
    private int currentIndex = 0;

    // Topic passed from the Home screen.
    private String topic;

    // Local helper used to simulate learning support features such as hints and explanations.
    private final LocalLearningAssistant assistant = new LocalLearningAssistant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        topic = getIntent().getStringExtra("topic");
        if (topic == null) topic = "Algorithms";

        tvTopic = findViewById(R.id.tvTopic);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvPrompt = findViewById(R.id.tvPrompt);
        tvResponse = findViewById(R.id.tvResponse);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        rgOptions = findViewById(R.id.rgOptions);
        btnHint = findViewById(R.id.btnHint);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);

        tvTopic.setText("Topic: " + topic);
        tvPrompt.setText("Prompt will appear here");
        tvResponse.setText("Loading quiz...");

        // AI hint for the currently visible question.
        btnHint.setOnClickListener(v -> generateHint());

        // Move to the next question after saving the selected answer.
        btnNext.setOnClickListener(v -> saveAnswerAndNext());

        // Finish the quiz and send results to the next screen.
        btnSubmit.setOnClickListener(v -> submitQuiz());

        // Load quiz data from backend first; if unavailable, use fallback demo questions.
        fetchQuiz(topic);

        // Keep back navigation simple and predictable.

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void fetchQuiz(String topic) {
        progressBar.setVisibility(View.VISIBLE);
        tvResponse.setText("Loading quiz...");

        RetrofitClient.getApiService().getQuiz(topic).enqueue(new Callback<QuizApiResponse>() {
            @Override
            public void onResponse(Call<QuizApiResponse> call, Response<QuizApiResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getQuiz() != null
                        && !response.body().getQuiz().isEmpty()) {

                    questions = new ArrayList<>(response.body().getQuiz());
                    showQuestion();
                } else {
                    // Fallback keeps the app usable even if backend data is unavailable.
                    loadFallbackQuestions();
                    tvResponse.setText("Backend quiz unavailable. Loaded demo quiz instead.");
                    showQuestion();
                }
            }

            @Override
            public void onFailure(Call<QuizApiResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // Fallback improves reliability for demo and assessment purposes.
                loadFallbackQuestions();
                tvResponse.setText("Network issue detected. Loaded demo quiz instead.");
                showQuestion();
            }
        });
    }

    private void loadFallbackQuestions() {
        questions.clear();

        QuizQuestion q1 = new QuizQuestion();
        q1.setQuestion("Which data structure follows FIFO order?");
        q1.setOptions(Arrays.asList("Stack", "Queue", "Tree", "Graph"));
        q1.setCorrect_answer("B");

        QuizQuestion q2 = new QuizQuestion();
        q2.setQuestion("What is the worst-case time complexity of linear search?");
        q2.setOptions(Arrays.asList("O(1)", "O(log n)", "O(n)", "O(n log n)"));
        q2.setCorrect_answer("C");

        QuizQuestion q3 = new QuizQuestion();
        q3.setQuestion("What is the main purpose of unit testing?");
        q3.setOptions(Arrays.asList(
                "To test an entire deployed system",
                "To test small individual components",
                "To replace debugging",
                "To design the UI"
        ));
        q3.setCorrect_answer("B");

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        currentIndex = 0;
    }

    private void showQuestion() {
        if (questions.isEmpty() || currentIndex >= questions.size()) return;

        QuizQuestion q = questions.get(currentIndex);

        tvQuestionNumber.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        tvQuestion.setText(q.getQuestion());

        rgOptions.removeAllViews();

        // Dynamically create answer options based on quiz data returned by the backend.
        for (int i = 0; i < q.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(q.getOptions().get(i));
            radioButton.setId(i);
            radioButton.setTextSize(16f);
            rgOptions.addView(radioButton);
        }

        if (q.getSelectedIndex() >= 0) {
            rgOptions.check(q.getSelectedIndex());
        } else {
            rgOptions.clearCheck();
        }

        // Show Next for middle questions and Submit for the last one.
        btnNext.setVisibility(currentIndex == questions.size() - 1 ? View.GONE : View.VISIBLE);
        btnSubmit.setVisibility(currentIndex == questions.size() - 1 ? View.VISIBLE : View.GONE);
    }

    private void saveSelectedAnswer() {
        if (questions.isEmpty()) return;
        int checkedId = rgOptions.getCheckedRadioButtonId();
        if (checkedId >= 0) {
            questions.get(currentIndex).setSelectedIndex(checkedId);
        }
    }

    private void saveAnswerAndNext() {
        if (rgOptions.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select an answer first", Toast.LENGTH_SHORT).show();
            return;
        }

        saveSelectedAnswer();
        currentIndex++;
        showQuestion();
    }

    private void submitQuiz() {
        if (rgOptions.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select an answer first", Toast.LENGTH_SHORT).show();
            return;
        }

        saveSelectedAnswer();

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("topic", topic);
        intent.putExtra("questions", questions);
        startActivity(intent);
        finish();
    }

    private void generateHint() {
        if (questions.isEmpty()) return;

        QuizQuestion question = questions.get(currentIndex);
        progressBar.setVisibility(View.VISIBLE);
        tvResponse.setText("Generating hint...");

        // Displays both the prompt and returned learning support text in the UI.
        assistant.generateHint(question, new LocalLearningAssistant.Callback() {
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