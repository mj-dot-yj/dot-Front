package com.example.dot.dto

data class ApiResponse(
    val isSuccess : Boolean,
    val message : String,
    val result : Object
)