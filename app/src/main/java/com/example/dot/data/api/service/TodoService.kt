package com.example.dot.data.api.service

import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.TodoRequest
import retrofit2.Call
import retrofit2.http.*

interface TodoService {
    @POST("/todo/saveTodo")
    fun todoSave(
        @Header("Authorization") accessToken: String,
        @Body jsonParams: TodoRequest
    ): Call<ApiResponse>

    @GET("/todo/info/{idx}")
    fun todoById(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String
    ): Call<ApiResponse>

    @DELETE("/todo/delete/{idx}")
    fun deleteTodo(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String
    ): Call<Void>

    @GET("/todo/all/{idx}/{date}")
    fun allTodoByUserIdAndDate(
        @Header("Authorization") accessToken: String,
        @Path("idx") idx: String,
        @Path("date") date: String
    ): Call<ApiResponse>
}
