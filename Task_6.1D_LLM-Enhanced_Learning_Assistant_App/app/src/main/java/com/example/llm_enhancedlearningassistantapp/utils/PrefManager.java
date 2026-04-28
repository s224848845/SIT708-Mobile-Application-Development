package com.example.llm_enhancedlearningassistantapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PrefManager {
    private static final String PREF_NAME = "learnmate_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_INTERESTS = "interests";
    private static final String KEY_LOGGED_IN = "logged_in";

    private final SharedPreferences preferences;

    public PrefManager(Context context) {
        // SharedPreferences is used for lightweight local storage of user profile details.
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(String username, String email, String phone) {
        preferences.edit()
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PHONE, phone)
                .putBoolean(KEY_LOGGED_IN, true)
                .apply();
    }

    public void saveInterests(Set<String> interests) {
        preferences.edit().putStringSet(KEY_INTERESTS, interests).apply();
    }

    public String getUsername() {
        return preferences.getString(KEY_USERNAME, "Student");
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, "");
    }

    public String getPhone() {
        return preferences.getString(KEY_PHONE, "");
    }

    public Set<String> getInterests() {
        Set<String> interests = preferences.getStringSet(KEY_INTERESTS, new HashSet<>());
        return interests == null ? Collections.emptySet() : interests;
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public void logout() {
        // Clears the saved session so the app returns to the login state.
        preferences.edit().clear().apply();
    }
}