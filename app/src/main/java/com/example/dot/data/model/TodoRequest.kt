package com.example.dot.data.model

data class TodoRequest(
    var user_id: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var start_time: String? = null,
    var end_time: String? = null,
    var alarmed: Long? = null,
    var priority: String? = null,
    var todo_date: String? = null
)
