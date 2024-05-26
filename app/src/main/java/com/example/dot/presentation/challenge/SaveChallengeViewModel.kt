package com.example.dot.presentation.challenge

import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.ChallengeRequest
import com.example.dot.util.GlobalApplication
import retrofit2.Call
import retrofit2.Response


class SaveChallengeViewModel: ViewModel() {
    interface OnFinishedSaveChallengeListener {
        fun onSuccess(message: String)

        fun onFailure(message: String)
    }

    fun saveChallenge(
        challengeRequest: ChallengeRequest,
        onFinishedSaveChallengeListener: OnFinishedSaveChallengeListener
    ) {
        val apiObject = ApiObject
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        apiObject.manageChallenge().saveChallenge(accessToken, challengeRequest)
            .enqueue(object: retrofit2.Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    when(response.code()) {
                        201 -> {
                            onFinishedSaveChallengeListener.onSuccess("Challenge Save 성공")
                        }
                        500 -> {
                            onFinishedSaveChallengeListener.onFailure("Challenge Save 실패")
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    onFinishedSaveChallengeListener.onFailure("Challenge Save 통신 실패")
                }
            })
    }
}

