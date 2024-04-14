package com.example.dot.data.api

import com.example.dot.data.api.service.MemberService
import com.example.dot.data.api.service.TodoService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiObject {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
        private val gson: Gson = GsonBuilder().setLenient().create()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        fun manageMember(): MemberService {
            return retrofit.create(MemberService::class.java)
        }

        fun manageTodo(): TodoService {
            return retrofit.create(TodoService::class.java)
        }

    }
}