package com.example.dot.data.api.service

import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.MemberRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberService {
    @POST("/member/login")
    fun memberLogin(
        @Body jsonParams: MemberRequest,
    ): Call<ApiResponse>

    @POST("/logout")
    fun memberLogout(
        @Header("Authorization") accessToken: String
    ): Call<Void>
}