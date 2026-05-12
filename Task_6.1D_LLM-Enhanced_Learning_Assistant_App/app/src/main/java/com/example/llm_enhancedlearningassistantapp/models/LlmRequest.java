package com.example.llm_enhancedlearningassistantapp.models;

import java.util.List;

public class LlmRequest {
    private String question;
    private List<String> options;
    private String selectedAnswer;
    private String correctAnswer;

    public LlmRequest(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public LlmRequest(String question, String selectedAnswer, String correctAnswer) {
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.correctAnswer = correctAnswer;
    }
}