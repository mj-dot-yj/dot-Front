package com.example.dot.presentation.member

import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.MemberRequest
import com.example.dot.util.GlobalApplication
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {
    interface OnFinishedLoginListener {
        fun onSuccess()
        fun onFailure(message: String)
    }

    fun login(id: String, pw: String, onFinishedLoginListener: OnFinishedLoginListener) {
        val apiObject = ApiObject
        val data = MemberRequest(id, pw)

        apiObject.manageMember().memberLogin(data).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        val jsonObj = JSONObject(response.body()!!.result.toString())
                        val grant = jsonObj.getString("grantType")
                        val accessToken = jsonObj.getString("accessToken")
                        GlobalApplication.prefs.setString("accessToken", "$grant $accessToken")
                        onFinishedLoginListener.onSuccess()

                    }

                    500 -> onFinishedLoginListener.onFailure("로그인에 실패하였습니다.")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onFinishedLoginListener.onFailure("통신에 실패하였습니다.")
            }
        })
    }
}