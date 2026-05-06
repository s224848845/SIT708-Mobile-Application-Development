package com.example.task81c_llmchatbot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Callback interface used to return Gemini API results back to ChatActivity.
 * The API call runs asynchronously, so success and error results are handled here.
 */
interface LLMCallback {
    void onSuccess(String reply);
    void onError(String error);
}

/**
 * LLMService handles communication with Google's Gemini API.
 *
 * Important:
 * For this prototype, the API key is placed here only for testing.
 */
public class LLMService {

    // Gemini API key from Google AI Studio.
    private static final String API_KEY = "AIzaSyBVIc3S60digIDg-uI1ubEp4eC7oPbywpI";

    // Gemini API endpoint.
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent";

    private final OkHttpClient client = new OkHttpClient();

    /**
     * Sends the user's message to Gemini and returns the chatbot response.
     */
    public void sendMessageToLLM(String userMessage, LLMCallback callback) {
        try {
            JSONObject jsonBody = new JSONObject();

            JSONArray contentsArray = new JSONArray();
            JSONObject contentObject = new JSONObject();
            JSONArray partsArray = new JSONArray();

            JSONObject textPart = new JSONObject();
            textPart.put("text", userMessage);

            partsArray.put(textPart);
            contentObject.put("parts", partsArray);
            contentsArray.put(contentObject);

            jsonBody.put("contents", contentsArray);

            RequestBody body = RequestBody.create(
                    jsonBody.toString(),
                    MediaType.parse("application/json")
            );


            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("x-goog-api-key", API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError("Network error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        callback.onError("API error: " + response.code());
                        return;
                    }

                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        String botReply = jsonResponse
                                .getJSONArray("candidates")
                                .getJSONObject(0)
                                .getJSONObject("content")
                                  .getJSONArray("parts")
                                .getJSONObject(0)
                                .getString("text");

//                        callback.onSuccess(botReply.trim());

                        // Remove markdown formatting symbols for cleaner chat display
                        botReply = botReply.replace("**", "");
                        botReply = botReply.replace("*", "");
                        botReply = botReply.replace("#", "");

                        callback.onSuccess(botReply.trim());

                    } catch (Exception e) {
                        callback.onError("Response parsing error");
                    }
                }
            });

        } catch (Exception e) {
            callback.onError("Request creation error");
        }
    }
}