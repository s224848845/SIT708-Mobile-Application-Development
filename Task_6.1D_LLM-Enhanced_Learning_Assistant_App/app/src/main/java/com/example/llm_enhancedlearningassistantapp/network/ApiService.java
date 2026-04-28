package com.example.llm_enhancedlearningassistantapp.network;

import com.example.llm_enhancedlearningassistantapp.models.QuizApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("getQuiz")
    Call<QuizApiResponse> getQuiz(@Query("topic") String topic);
}
