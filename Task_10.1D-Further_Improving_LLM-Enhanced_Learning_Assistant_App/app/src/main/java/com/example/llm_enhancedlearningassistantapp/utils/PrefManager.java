package com.example.llm_enhancedlearningassistantapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.llm_enhancedlearningassistantapp.models.QuizHistory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrefManager {

    private static final String PREF_NAME = "learnmate_prefs";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_INTERESTS = "interests";
    private static final String KEY_LOGGED_IN = "logged_in";

    private static final String KEY_TOTAL_QUESTIONS = "total_questions";
    private static final String KEY_CORRECT_ANSWERS = "correct_answers";
    private static final String KEY_INCORRECT_ANSWERS = "incorrect_answers";

    private static final String KEY_ACCOUNT_PLAN = "account_plan";
    private static final String KEY_HISTORY = "quiz_history";

    private final SharedPreferences preferences;
    private final Gson gson = new Gson();

    public PrefManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // USER DATA

    public void saveUser(String username, String email, String phone) {

        preferences.edit()
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PHONE, phone)
                .putBoolean(KEY_LOGGED_IN, true)
                .apply();
    }

    public String getUsername() {
        return preferences.getString(KEY_USERNAME, "Student");
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, "student@email.com");
    }

    public String getPhone() {
        return preferences.getString(KEY_PHONE, "");
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_LOGGED_IN, false);
    }

    // INTERESTS

    public void saveInterests(Set<String> interests) {
        preferences.edit()
                .putStringSet(KEY_INTERESTS, interests)
                .apply();
    }

    public Set<String> getInterests() {

        Set<String> interests =
                preferences.getStringSet(KEY_INTERESTS, new HashSet<>());

        return interests == null
                ? Collections.emptySet()
                : interests;
    }

    // QUIZ STATISTICS

    public void addQuizStats(int totalQuestions, int correctAnswers) {

        int incorrectAnswers = totalQuestions - correctAnswers;

        preferences.edit()
                .putInt(
                        KEY_TOTAL_QUESTIONS,
                        getTotalQuestions() + totalQuestions
                )
                .putInt(
                        KEY_CORRECT_ANSWERS,
                        getCorrectAnswers() + correctAnswers
                )
                .putInt(
                        KEY_INCORRECT_ANSWERS,
                        getIncorrectAnswers() + incorrectAnswers
                )
                .apply();
    }

    public int getTotalQuestions() {
        return preferences.getInt(KEY_TOTAL_QUESTIONS, 0);
    }

    public int getCorrectAnswers() {
        return preferences.getInt(KEY_CORRECT_ANSWERS, 0);
    }

    public int getIncorrectAnswers() {
        return preferences.getInt(KEY_INCORRECT_ANSWERS, 0);
    }

    // ACCOUNT PLAN

    public void saveAccountPlan(String plan) {
        preferences.edit()
                .putString(KEY_ACCOUNT_PLAN, plan)
                .apply();
    }

    public String getAccountPlan() {
        return preferences.getString(KEY_ACCOUNT_PLAN, "Starter");
    }

    // QUIZ HISTORY

    public void addQuizHistory(QuizHistory history) {

        List<QuizHistory> historyList = getQuizHistory();

        historyList.add(0, history);

        preferences.edit()
                .putString(
                        KEY_HISTORY,
                        gson.toJson(historyList)
                )
                .apply();
    }

    public List<QuizHistory> getQuizHistory() {

        String json = preferences.getString(KEY_HISTORY, null);

        if (json == null || json.trim().isEmpty()) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<QuizHistory>>() {}.getType();

        List<QuizHistory> historyList =
                gson.fromJson(json, type);

        return historyList == null
                ? new ArrayList<>()
                : historyList;
    }

    // LOGOUT

    public void logout() {

        preferences.edit()
                .clear()
                .apply();
    }
}