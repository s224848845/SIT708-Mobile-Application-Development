package com.example.llm_enhancedlearningassistantapp.models;

import java.util.Set;

public class UserSession {
    private final String username;
    private final String email;
    private final String phone;
    private final Set<String> interests;

    public UserSession(String username, String email, String phone, Set<String> interests) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.interests = interests;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Set<String> getInterests() {
        return interests;
    }
}