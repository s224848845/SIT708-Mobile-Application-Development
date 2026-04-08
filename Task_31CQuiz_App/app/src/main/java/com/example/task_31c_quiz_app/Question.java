package com.example.task_31c_quiz_app;
/*
 * Model class for one quiz question.
 * This keeps the question text, four answer options,
 * and the index of the correct answer.
 */
public class Question {

    private final String questionText;
    private final String[] options;
    private final int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Returns the question sentence
    public String getQuestionText() {
        return questionText;
    }

    // Returns the four answer options
    public String[] getOptions() {
        return options;
    }

    // Returns the correct answer position (0 to 3)
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}