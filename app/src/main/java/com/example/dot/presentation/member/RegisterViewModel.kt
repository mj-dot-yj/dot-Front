package com.example.dot.presentation.member

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.MemberInfoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    interface OnFinishedRegisterListener {
        fun onSuccess(message: String)

        fun onFailure(message: String)
    }

    fun signup(
        memberInfoRequest: MemberInfoRequest,
        onFinishedRegisterListener: OnFinishedRegisterListener
    ) {
        val apiObject = ApiObject

        apiObject.manageMember().memberSignup(memberInfoRequest)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    when (response.code()) {
                        201 -> {
                            Log.d("register success", "성공")
                            onFinishedRegisterListener.onSuccess("회원가입에 성공하였습니다.")
                        }

                        400 ->{
                            Log.d("register fail", "실패")
                            onFinishedRegisterListener.onFailure("형식을 맞춰주세요.")
                        }

                        500 -> {
                            Log.d("register fail", "실패")
                            onFinishedRegisterListener.onFailure("회원가입에 실패하였습니다.")
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("register sign up", t.message.toString())
                    onFinishedRegisterListener.onFailure("통신에 실패하였습니다.")
                }
            })
    }

}