package com.example.llm_enhancedlearningassistantapp.network;

import com.example.llm_enhancedlearningassistantapp.models.LlmRequest;
import com.example.llm_enhancedlearningassistantapp.models.LlmResponse;
import com.example.llm_enhancedlearningassistantapp.models.QuizApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("getQuiz")
    Call<QuizApiResponse> getQuiz(@Query("topic") String topic);

    @POST("getHint")
    Call<LlmResponse> getHint(@Body LlmRequest request);

    @POST("explainAnswer")
    Call<LlmResponse> explainAnswer(@Body LlmRequest request);
}