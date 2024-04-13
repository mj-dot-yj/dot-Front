package com.example.dot.data.api.service

import com.example.dot.data.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TodoService {
    @GET("/todo/info/{idx}")
    fun todoById(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String
    ): Call<ApiResponse>
}
