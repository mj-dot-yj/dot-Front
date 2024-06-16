package com.example.dot.data.model

data class ChallengeInfoResponse(
    var id: String? = null,
    var title: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var alarmed: String? = null,
    var count: Long? = null,
    var totalCount: Long? = null,
    var days: ArrayList<String>? = null

) {
    fun setTime() {
        startTime = startTime!!.replace("-", ":")
        endTime = endTime!!.replace("-", ":")
    }
}