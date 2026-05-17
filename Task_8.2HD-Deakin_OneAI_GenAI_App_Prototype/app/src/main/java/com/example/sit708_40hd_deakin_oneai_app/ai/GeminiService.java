package com.example.sit708_40hd_deakin_oneai_app.ai;

import android.os.Handler;
import android.os.Looper;

import com.example.sit708_40hd_deakin_oneai_app.BuildConfig;
import com.example.sit708_40hd_deakin_oneai_app.utils.PromptUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Handles Gemini API communication for Deakin OneAI.
 */
public class GeminiService {

    public interface GeminiCallback {
        void onSuccess(String result);
        void onError(String error);
    }

    // open Google AI Studio and copy the current model name shown there.
    // Common current examples: gemini-2.5-flash, gemini-2.5-pro, gemini-1.5-flash.
    private static final String MODEL_NAME = "gemini-2.5-flash";

    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/"
                    + MODEL_NAME
                    + ":generateContent";

    private static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();

    public void generateLearningResponse(String featureType,
                                         String userInput,
                                         GeminiCallback callback) {

        String cleanedInput = PromptUtils.cleanPrompt(userInput);

        if (PromptUtils.isBlank(cleanedInput)) {
            callback.onError("Please enter a question, topic, lesson note, or assignment description first.");
            return;
        }

        if (isUnsafePrompt(cleanedInput)) {
            callback.onError("Safety Notice: This request appears unsafe or outside the academic purpose of Deakin OneAI.");
            return;
        }

        if (BuildConfig.GEMINI_API_KEY == null || BuildConfig.GEMINI_API_KEY.trim().isEmpty()) {
            callback.onError("Gemini API key is missing. Add GEMINI_API_KEY to local.properties, then Sync Gradle and rebuild.");
            return;
        }

        try {
            String finalPrompt = buildPrompt(featureType, cleanedInput);
            String payload = buildRequestJson(finalPrompt);

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-goog-api-key", BuildConfig.GEMINI_API_KEY)
                    .post(RequestBody.create(payload, JSON))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    postError(callback, "Network error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body() != null ? response.body().string() : "";

                    if (!response.isSuccessful()) {
                        String friendlyError =
                                "Gemini API error: " + response.code() + "\n\n"
                                        + "Possible causes:\n"
                                        + "1. The model name is not available for your API key.\n"
                                        + "2. The Gemini API key is invalid or restricted.\n"
                                        + "3. The Generative Language API is not enabled for the project.\n\n"
                                        + "Current model used: " + MODEL_NAME + "\n\n"
                                        + body;

                        postError(callback, friendlyError);
                        return;
                    }

                    try {
                        postSuccess(callback, parseGeminiText(body));
                    } catch (Exception e) {
                        postError(callback, "Could not parse Gemini response. Please try again.");
                    }
                }
            });

        } catch (Exception e) {
            callback.onError("Request creation failed: " + e.getMessage());
        }
    }

    private String buildRequestJson(String prompt) throws Exception {
        JSONObject textPart = new JSONObject();
        textPart.put("text", prompt);

        JSONArray parts = new JSONArray();
        parts.put(textPart);

        JSONObject content = new JSONObject();
        content.put("parts", parts);

        JSONArray contents = new JSONArray();
        contents.put(content);

        JSONObject json = new JSONObject();
        json.put("contents", contents);

        return json.toString();
    }

    private String parseGeminiText(String responseBody) throws Exception {
        JSONObject json = new JSONObject(responseBody);

        return json
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")
                .trim();
    }

    private boolean isUnsafePrompt(String input) {
        String text = input.toLowerCase();

        return text.contains("steal password")
                || text.contains("malware")
                || text.contains("phishing")
                || text.contains("bypass login")
                || text.contains("exploit vulnerability")
                || text.contains("illegal")
                || text.contains("credential theft");
    }

    private String buildPrompt(String featureType, String userInput) {
        String systemInstruction =
                "You are Deakin OneAI, a safe academic assistant for Deakin University students. "
                        + "Support study planning, concept explanation, lesson summarisation, assignment preparation, "
                        + "productivity, and Deakin student workflows. Use clear headings and practical steps.";

        switch (featureType) {
            case "SUMMARY":
                return systemInstruction + "\n\nSummarise this lesson note:\n" + userInput;

            case "PLAN":
                return systemInstruction + "\n\nCreate a weekly study plan for:\n" + userInput;

            case "CHECKLIST":
                return systemInstruction + "\n\nCreate an assignment checklist for:\n" + userInput;

            case "EXPLAIN":
                return systemInstruction + "\n\nExplain this concept simply with an example:\n" + userInput;

            case "FLASHCARDS":
                return systemInstruction + "\n\nCreate 5 revision flashcards. Format as Q and A:\n" + userInput;

            default:
                return systemInstruction + "\n\nAnswer this student question:\n" + userInput;
        }
    }

    private void postSuccess(GeminiCallback callback, String result) {
        mainHandler.post(() -> callback.onSuccess(result));
    }

    private void postError(GeminiCallback callback, String error) {
        mainHandler.post(() -> callback.onError(error));
    }
}