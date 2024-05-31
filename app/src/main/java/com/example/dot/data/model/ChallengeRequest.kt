package com.example.dot.data.model

data class ChallengeRequest(
    var userId: Long? = null,
    var title: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var alarmed: Long? = null,
    var totalCount: Long? = null,
    var days: ArrayList<String>? = null
)
