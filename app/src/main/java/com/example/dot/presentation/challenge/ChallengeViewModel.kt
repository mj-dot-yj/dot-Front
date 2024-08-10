package com.example.dot.presentation.challenge

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.ChallengeInfoResponse
import com.example.dot.util.GlobalApplication
import com.google.gson.Gson
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChallengeViewModel: ViewModel() {

    interface onGetChallengeListener {
        fun onSuccessGetChallenge(challengeInfoResponse: ChallengeInfoResponse)

        fun onFailureGetChallenge(message: String)

    }
    interface onGetAllChallengeListener {
        fun onSuccessGetAllChallenge(challengeList: JSONArray)

        fun onFailureGetAllChallenge(message: String)
    }
    interface onModifyStateListener {
        fun onSuccessModifyState(message: String)

        fun onFailureModifyState(message: String)
    }


    fun getChallenge(idx: String, onGetChallengeListener: onGetChallengeListener) {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        ApiObject.manageChallenge().challengeById(accessToken, idx)
            .enqueue(object: Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    when(response.code()) {
                        200 -> {
                            val challenge = response.body()!!.result.toString()
                            val challengeInfoResponse = Gson().fromJson(challenge, ChallengeInfoResponse::class.java)
                            challengeInfoResponse.setTime()

                            onGetChallengeListener.onSuccessGetChallenge(challengeInfoResponse)
                        }
                        500 -> { onGetChallengeListener.onFailureGetChallenge("getChallenge 실패") }
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    onGetChallengeListener.onFailureGetChallenge("getChallenge 통신 실패")
                }
            })
    }

    fun  getAllChallenge(idx: String, onGetAllChallengeListener: onGetAllChallengeListener) {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        ApiObject.manageChallenge().allChallengeByUserId(accessToken, idx)
            .enqueue(object: Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    when(response.code()) {
                        200 -> {
                            val challengeList = response.body()!!.result.toString()
                            if(challengeList != null && challengeList != "[]") {
                                val jsonArray = JSONArray(challengeList)
                                onGetAllChallengeListener.onSuccessGetAllChallenge(jsonArray)
                            }
                        }
                        500 -> { onGetAllChallengeListener.onFailureGetAllChallenge("getAllChallenge 실패") }
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    onGetAllChallengeListener.onFailureGetAllChallenge("getAllChallenge 통신 실패")
                }
            })
    }

    fun saveState(count: Long, challengeId: String, onModifyStateListener: onModifyStateListener) {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        ApiObject.manageChallenge().modifyState(accessToken, count, challengeId).enqueue(object: Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                when(response.code()) {
                    200 -> { onModifyStateListener.onSuccessModifyState("state 수정 성공") }
                    500 -> { onModifyStateListener.onFailureModifyState("state 수정 실패") }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onModifyStateListener.onFailureModifyState("saveState 통신 실패")
            }
        })
    }
}