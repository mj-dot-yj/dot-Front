package com.example.dot.data.model

data class TodoInfoResponse(
    val id: String? = null,
    val title: String? = null,
    val content: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    val alarmed: String? = null,
    val priority: String? = null,
    val state: String? = null,
    val todoDate: String? = null,
) {
    fun setTime() {
        startTime = startTime!!.replace("-", ":")
        endTime = endTime!!.replace("-", ":")
    }
}
