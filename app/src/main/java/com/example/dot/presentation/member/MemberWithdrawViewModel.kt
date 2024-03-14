package com.example.dot.presentation.member

import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.CheckPwRequest
import com.example.dot.util.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberWithdrawViewModel : ViewModel() {
    interface OnFinishedLoginListener {
        fun onCheckSuccess()
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

    fun checkPassword(
        pw: String,
        onFinishedLoginListener: MemberWithdrawViewModel.OnFinishedLoginListener
    ) {
        val apiObject = ApiObject
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")
        val data = CheckPwRequest(pw)

        apiObject.manageMember().memberCheckPassword(accessToken, data)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            onFinishedLoginListener.onCheckSuccess()
                        }

                        500 -> onFinishedLoginListener.onFailure("비밀번호가 일치하지 않습니다.")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    onFinishedLoginListener.onFailure("통신에 실패하였습니다.")
                }
            })
    }

    fun memberWithdraw(
        onFinishedLoginListener: MemberWithdrawViewModel.OnFinishedLoginListener
    ) {
        val apiObject = ApiObject
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        apiObject.manageMember().memberWithdraw(accessToken)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response.code()) {
                        200 -> {
                            onFinishedLoginListener.onSuccess("탈퇴 되었습니다.")
                        }

                        500 -> onFinishedLoginListener.onFailure("탈퇴에 실패하였습니다.")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onFinishedLoginListener.onFailure("통신에 실패하였습니다.")
                }
            })
    }
}