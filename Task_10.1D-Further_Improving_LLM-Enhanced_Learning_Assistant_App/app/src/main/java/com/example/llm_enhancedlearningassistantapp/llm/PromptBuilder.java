package com.example.llm_enhancedlearningassistantapp.llm;

import com.example.llm_enhancedlearningassistantapp.models.QuizQuestion;

public class PromptBuilder {

    // Builds the prompt text shown in the UI when a hint is requested.
    public static String buildHintPrompt(QuizQuestion question) {
        return "Generate a short study hint for this question without revealing the final answer.\n"
                + "Question: " + question.getQuestion() + "\n"
                + "Options: " + question.getOptions();
    }

    // Builds the prompt text shown in the UI when an explanation is requested.
    public static String buildExplanationPrompt(QuizQuestion question) {
        String selectedAnswer = question.getSelectedIndex() >= 0
                ? question.getOptions().get(question.getSelectedIndex())
                : "No answer selected";

        String correctAnswer = question.getCorrectIndex() >= 0
                ? question.getOptions().get(question.getCorrectIndex())
                : "Unknown";

        return "Explain in simple student-friendly language why the selected answer is correct or incorrect.\n"
                + "Question: " + question.getQuestion() + "\n"
                + "Selected Answer: " + selectedAnswer + "\n"
                + "Correct Answer: " + correctAnswer;
    }
}