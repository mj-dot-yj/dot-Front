package com.example.dot.data.api.service

import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberService {
    @POST("/member/login")
    fun memberLogin(
        @Body jsonParams: LoginRequest,
    ): Call<ApiResponse>

    @POST("/logout")
    fun memberLogout(
        @Header("Authorization") accessToken: String
    ): Call<Void>
}