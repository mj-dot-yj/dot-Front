package com.example.dot

import com.example.dot.dto.ApiResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST("/member/login")
    fun userLogin(
        @Body jsonParams : UserRequest,
    ): Call<ApiResponse>

    @POST("/logout")
    fun userLogout(
        @Header("Authorization") accessToken : String
    ): Call<Void>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
        val gson : Gson =   GsonBuilder().setLenient().create()

        fun create() : Api{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(Api::class.java)
        }
    }
}