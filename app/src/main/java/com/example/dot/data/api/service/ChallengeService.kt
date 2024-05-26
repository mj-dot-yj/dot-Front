package com.example.dot.data.api.service

import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.ChallengeRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChallengeService {

    @POST("/challenge/saveChallenge")
    fun saveChallenge(
        @Header("Authorization") accessToken: String,
        @Body jsonParams: ChallengeRequest
    ): Call<ApiResponse>
}