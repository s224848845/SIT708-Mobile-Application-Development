package com.example.llm_enhancedlearningassistantapp.models;

import java.io.Serializable;
import java.util.List;

public class QuizHistory implements Serializable {

    private String topic;
    private String dateTime;
    private int score;
    private int totalQuestions;
    private List<QuizQuestion> questions;

    public QuizHistory(
            String topic,
            String dateTime,
            int score,
            int totalQuestions,
            List<QuizQuestion> questions
    ) {

        this.topic = topic;
        this.dateTime = dateTime;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.questions = questions;
    }

    public String getTopic() {
        return topic;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }
}