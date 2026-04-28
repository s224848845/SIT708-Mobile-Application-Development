package com.example.llm_enhancedlearningassistantapp.llm;

import android.os.Handler;
import android.os.Looper;

import com.example.llm_enhancedlearningassistantapp.models.QuizQuestion;

public class LocalLearningAssistant {

    // Callback allows the activity to update the UI after simulated processing.
    public interface Callback {
        void onSuccess(String prompt, String response);
        void onFailure(String prompt, String error);
    }

    public void generateHint(QuizQuestion question, Callback callback) {
        String prompt = PromptBuilder.buildHintPrompt(question);

        // Simulates processing time so loading states can be demonstrated in the UI.
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (question.getQuestion() == null || question.getQuestion().trim().isEmpty()) {
                callback.onFailure(prompt, "Unable to generate hint right now.");
                return;
            }

            String response = "Think about the core concept being tested. Remove obviously wrong options first, then compare the remaining choices carefully.";
            callback.onSuccess(prompt, response);
        }, 1200);
    }

    public void explainAnswer(QuizQuestion question, Callback callback) {
        String prompt = PromptBuilder.buildExplanationPrompt(question);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (question.getSelectedIndex() < 0) {
                callback.onFailure(prompt, "No answer was selected, so an explanation cannot be generated.");
                return;
            }

            String response;
            if (question.isCorrect()) {
                response = "Your answer is correct because it matches the expected concept tested by the question.";
            } else {
                response = "Your answer is incorrect because it does not match the correct concept. Review the differences between your selected option and the correct one.";
            }
            callback.onSuccess(prompt, response);
        }, 1200);
    }
}