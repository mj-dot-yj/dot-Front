package com.example.dot.presentation.member

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.SignupRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {

    interface OnFinishedSignupLister {
        fun onSuccess()

        fun onFailure(message : String)
    }

    fun signup(name:String, id:String, pw:String, phone:String, onFinishedsignupLister: OnFinishedSignupLister) {
        val apiObject = ApiObject
        val data = SignupRequest(name, id, pw, phone)
        Log.d("register signup name", data.name.toString())
        apiObject.manageMember().memberSignup(data).enqueue(object: Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
               when (response.code()){
                   201 -> {
                       Log.d("register success", "성공")
                   }
                   500 -> {
                       Log.d("register fail", "실패")
                   }


                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("register sign up", t.message.toString())
            }
        })
//        apiObject.manageMember().memberSignup(data).enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(
//                call: Call<ApiResponse>,
//                response: Response<ApiResponse>
//            ) {
//                when (response.code()) {
//                    200 -> {
//                        val result = response.body().toString()
//                        Log.d("success register", response.body().toString())
//                        onFinishedsignupLister.onSuccess()
//                    }
//                    201 -> {
//                        Log.d("code", "201")
//                    }
//                    500 -> {
//                        onFinishedsignupLister.onFailure("회원가입에 실패하였습니다.")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                Log.d("code", "통신 실패")
//                onFinishedsignupLister.onFailure("통신에 실패하였습니다.")
//            }
//        })
    }

}