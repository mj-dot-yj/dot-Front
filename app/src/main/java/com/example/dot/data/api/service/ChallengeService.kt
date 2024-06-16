package com.example.dot.data.api.service

import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.ChallengeRequest
import retrofit2.Call
import retrofit2.http.*

interface ChallengeService {

    @POST("/challenge/saveChallenge")
    fun saveChallenge(
        @Header("Authorization") accessToken: String,
        @Body jsonParams: ChallengeRequest
    ): Call<ApiResponse>

    @GET("/challenge/info/{idx}")
    fun challengeById(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String
    ): Call<ApiResponse>

    @GET("/challenge/all/{idx}")
    fun allChallengeByUserId(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String
    ): Call<ApiResponse>

    @POST("/challenge/modify/state/{idx}")
    fun modifyState(
        @Header("Authorization") accessToken: String,
        @Body count: Long,
        @Path("idx") idx: String
    ): Call<ApiResponse>
}