package com.example.dot.data.model

data class ApiResponse(
    val isSuccess: Boolean,
    val message: String,
    val result: Object
)