package com.example.dot.data.api.service

import com.example.dot.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MemberService {
    @POST("/member/login")
    fun memberLogin(
        @Body jsonParams: LoginRequest,
    ): Call<ApiResponse>

    @POST("/member/signUp")
    fun memberSignup(
        @Body jsonParams: SignupRequest
    ): Call<ApiResponse>

    @POST("/logout")
    fun memberLogout(
        @Header("Authorization") accessToken: String
    ): Call<Void>

    @POST("/member/checkPassword")
    fun memberCheckPassword(
        @Header("Authorization") accessToken: String,
        @Body jsonParams: CheckPwRequest
    ): Call<ApiResponse>

    @DELETE("/member/delete")
    fun memberWithdraw(
        @Header("Authorization") accessToken: String
    ): Call<Void>

    @POST("/member/info")
    fun MemberByEmail(
        @Header("Authorization") accessToken: String
    ): Call<ApiResponse>
}