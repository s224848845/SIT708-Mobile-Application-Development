package com.example.task_31c_quiz_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/*
 * ResultActivity:
 * - Shows final score
 * - Displays greeting with user name
 * - "Take New Quiz" returns to main screen
 * - Main screen keeps the saved user name
 * - "Finish" closes the app
 * - Theme toggle is also available here
 */
public class ResultActivity extends AppCompatActivity {

    private TextView tvResultTitle, tvScore;
    private Button btnTakeNewQuiz, btnFinish;
    private Switch switchThemeResult;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        applySavedTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Link views
        tvResultTitle = findViewById(R.id.tvResultTitle);
        tvScore = findViewById(R.id.tvScore);
        btnTakeNewQuiz = findViewById(R.id.btnTakeNewQuiz);
        btnFinish = findViewById(R.id.btnFinish);
        switchThemeResult = findViewById(R.id.switchThemeResult);

        // Receive data from QuizActivity
        String userName = getIntent().getStringExtra("user_name");
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("total_questions", 0);

        // Show result
        tvResultTitle.setText("Congratulations " + userName + "!");
        tvScore.setText("Your score: " + score + "/" + totalQuestions);

        // Restore theme state
        int currentTheme = preferences.getInt(MainActivity.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        switchThemeResult.setChecked(currentTheme == AppCompatDelegate.MODE_NIGHT_YES);

        // Theme toggle logic
        switchThemeResult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int mode = isChecked
                    ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO;

            preferences.edit().putInt(MainActivity.KEY_THEME, mode).apply();
            AppCompatDelegate.setDefaultNightMode(mode);
        });

        // Return to main screen and keep saved name
        btnTakeNewQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Close the app completely
        btnFinish.setOnClickListener(v -> finishAffinity());
    }

    /*
     * Applies saved theme before displaying activity.
     */
    private void applySavedTheme() {
        int savedTheme = preferences.getInt(MainActivity.KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(savedTheme);
    }
}