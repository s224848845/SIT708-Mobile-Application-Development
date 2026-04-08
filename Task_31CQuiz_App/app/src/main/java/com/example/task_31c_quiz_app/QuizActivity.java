package com.example.task_31c_quiz_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

/*
 * QuizActivity:
 * - Displays one question at a time
 * - Lets user choose one answer
 * - Submit button checks answer
 * - Correct option turns green
 * - Wrong selected option turns red
 * - User cannot change the answer after submission
 * - Progress bar updates as quiz progresses
 * - Next button loads next question
 * - After last question, ResultActivity opens
 */
public class QuizActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvProgress;
    private TextView tvQuestion;

    private RadioGroup radioGroupOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;

    private Button btnSubmit, btnNext;
    private Switch switchThemeQuiz;

    private List<Question> questionList;

    private int currentQuestionIndex = 0;
    private int score = 0;

    // This helps prevent re-submitting the same question
    private boolean hasAnsweredCurrentQuestion = false;

    private String userName;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        applySavedTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get the user name sent from MainActivity
        userName = getIntent().getStringExtra("user_name");

        // Link views
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
        tvQuestion = findViewById(R.id.tvQuestion);

        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext = findViewById(R.id.btnNext);
        switchThemeQuiz = findViewById(R.id.switchThemeQuiz);

        // Load question list
        questionList = QuizRepository.getQuestions();

        // Restore theme switch state
        int currentTheme = preferences.getInt(MainActivity.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        switchThemeQuiz.setChecked(currentTheme == AppCompatDelegate.MODE_NIGHT_YES);

        // Theme toggle logic
        switchThemeQuiz.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int mode = isChecked
                    ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO;

            preferences.edit().putInt(MainActivity.KEY_THEME, mode).apply();
            AppCompatDelegate.setDefaultNightMode(mode);
        });

        // Button listeners
        btnSubmit.setOnClickListener(v -> submitAnswer());
        btnNext.setOnClickListener(v -> moveToNextQuestion());

        // Load first question
        loadQuestion();
    }

    /*
     * Applies saved theme before screen is shown.
     */
    private void applySavedTheme() {
        int savedTheme = preferences.getInt(MainActivity.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(savedTheme);
    }

    /*
     * Loads current question and resets the option states.
     */
    private void loadQuestion() {
        // Get current question
        Question currentQuestion = questionList.get(currentQuestionIndex);

        // Display question text
        tvQuestion.setText(currentQuestion.getQuestionText());

        // Display answer options
        String[] options = currentQuestion.getOptions();
        rbOption1.setText(options[0]);
        rbOption2.setText(options[1]);
        rbOption3.setText(options[2]);
        rbOption4.setText(options[3]);

        // Reset selection and colours
        radioGroupOptions.clearCheck();
        resetOptionColours();

        // Enable all radio buttons for new question
        rbOption1.setEnabled(true);
        rbOption2.setEnabled(true);
        rbOption3.setEnabled(true);
        rbOption4.setEnabled(true);

        hasAnsweredCurrentQuestion = false;

        // Submit should be enabled, Next disabled until answer submitted
        btnSubmit.setEnabled(true);
        btnNext.setEnabled(false);

        // Update progress text and bar
        tvProgress.setText("Question " + (currentQuestionIndex + 1) + " of " + questionList.size());

        // Progress before answering current question
        int progressValue = (currentQuestionIndex * 100) / questionList.size();
        progressBar.setProgress(progressValue);
    }

    /*
     * Handles answer submission and feedback colouring.
     */
    private void submitAnswer() {
        // Stop if already answered
        if (hasAnsweredCurrentQuestion) {
            return;
        }

        int selectedRadioButtonId = radioGroupOptions.getCheckedRadioButtonId();

        // Check whether user selected an answer
        if (selectedRadioButtonId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        hasAnsweredCurrentQuestion = true;

        // Disable submit after answering
        btnSubmit.setEnabled(false);
        btnNext.setEnabled(true);

        Question currentQuestion = questionList.get(currentQuestionIndex);
        int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();

        RadioButton[] optionButtons = {rbOption1, rbOption2, rbOption3, rbOption4};

        // Find selected option index
        int selectedIndex = -1;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].getId() == selectedRadioButtonId) {
                selectedIndex = i;
                break;
            }
        }

        // Always highlight the correct answer in green
        optionButtons[correctAnswerIndex].setBackgroundColor(Color.GREEN);

        // If user selected wrong answer, highlight it red
        if (selectedIndex != correctAnswerIndex) {
            optionButtons[selectedIndex].setBackgroundColor(Color.RED);
        } else {
            // Increase score only if selected answer is correct
            score++;
        }

        // Disable all options so answer cannot be changed after submission
        rbOption1.setEnabled(false);
        rbOption2.setEnabled(false);
        rbOption3.setEnabled(false);
        rbOption4.setEnabled(false);

        // Progress after completing current question
        int progressValue = ((currentQuestionIndex + 1) * 100) / questionList.size();
        progressBar.setProgress(progressValue);
    }

    /*
     * Moves to next question, or result screen if quiz is finished.
     */
    private void moveToNextQuestion() {
        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            loadQuestion();
        } else {
            // Quiz finished -> move to ResultActivity
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("user_name", userName);
            intent.putExtra("score", score);
            intent.putExtra("total_questions", questionList.size());
            startActivity(intent);
            finish();
        }
    }

    /*
     * Resets button background colours for new question.
     */
    private void resetOptionColours() {
        rbOption1.setBackgroundColor(Color.TRANSPARENT);
        rbOption2.setBackgroundColor(Color.TRANSPARENT);
        rbOption3.setBackgroundColor(Color.TRANSPARENT);
        rbOption4.setBackgroundColor(Color.TRANSPARENT);
    }
}