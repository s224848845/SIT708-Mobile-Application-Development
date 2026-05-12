package com.example.llm_enhancedlearningassistantapp.llm;

import com.example.llm_enhancedlearningassistantapp.models.LlmRequest;
import com.example.llm_enhancedlearningassistantapp.models.LlmResponse;
import com.example.llm_enhancedlearningassistantapp.models.QuizQuestion;
import com.example.llm_enhancedlearningassistantapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalLearningAssistant {

    public interface CallbackResult {
        void onSuccess(String prompt, String response);
        void onFailure(String prompt, String error);
    }

    public void generateHint(QuizQuestion question, CallbackResult callback) {
        String fallbackPrompt = PromptBuilder.buildHintPrompt(question);

        LlmRequest request = new LlmRequest(
                question.getQuestion(),
                question.getOptions()
        );

        RetrofitClient.getApiService().getHint(request).enqueue(new Callback<LlmResponse>() {
            @Override
            public void onResponse(Call<LlmResponse> call, Response<LlmResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getPrompt(), response.body().getResponse());
                } else {
                    callback.onFailure(fallbackPrompt, "LLM hint could not be generated. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<LlmResponse> call, Throwable t) {
                callback.onFailure(fallbackPrompt, "LLM backend error: " + t.getMessage());
            }
        });
    }

    public void explainAnswer(QuizQuestion question, CallbackResult callback) {
        String fallbackPrompt = PromptBuilder.buildExplanationPrompt(question);

        String selectedAnswer = question.getSelectedIndex() >= 0
                ? question.getOptions().get(question.getSelectedIndex())
                : "No answer selected";

        String correctAnswer = question.getCorrectIndex() >= 0
                ? question.getOptions().get(question.getCorrectIndex())
                : "Unknown";

        LlmRequest request = new LlmRequest(
                question.getQuestion(),
                selectedAnswer,
                correctAnswer
        );

        RetrofitClient.getApiService().explainAnswer(request).enqueue(new Callback<LlmResponse>() {
            @Override
            public void onResponse(Call<LlmResponse> call, Response<LlmResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getPrompt(), response.body().getResponse());
                } else {
                    callback.onFailure(fallbackPrompt, "LLM explanation could not be generated. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<LlmResponse> call, Throwable t) {
                callback.onFailure(fallbackPrompt, "LLM backend error: " + t.getMessage());
            }
        });
    }
}