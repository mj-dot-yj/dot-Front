package com.example.dot.data.model

import org.json.JSONArray

data class ChallengeItem(
    var id: String,
    var title: String,
    var startTime: String,
    var endTime: String,
    var count: Long,
    var totalCount: Long,
    var days: JSONArray,
    var checked: String
)
