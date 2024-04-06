package com.example.dot.data.api.service

import com.example.dot.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
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
        @Body jsonParams: MemberInfoRequest
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

    @DELETE("/member/delete/{idx}")
    fun memberWithdraw(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String
    ): Call<Void>

    @POST("/member/info/{idx}")
    fun memberByEmail(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String
    ): Call<ApiResponse>

    @POST("/member/modify/{idx}")
    fun memberEdit(
        @Header("Authorization") accessToken: String,
        @Body jsonParams: MemberInfoRequest,
        @Path("idx") idx: String
    ): Call<ApiResponse>
}