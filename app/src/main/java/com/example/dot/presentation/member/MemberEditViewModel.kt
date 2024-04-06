package com.example.dot.presentation.member

import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.MemberInfoRequest
import com.example.dot.util.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberEditViewModel : ViewModel(){
    interface OnFinishedEditListener {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

    fun editMemberInfo(
        memberInfoRequest: MemberInfoRequest,
        onFinishedEditListener: MemberEditViewModel.OnFinishedEditListener
    ) {
        val apiObject = ApiObject
        val idx = GlobalApplication.prefs.getString("idx", "")
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        apiObject.manageMember().memberEdit(accessToken, memberInfoRequest, idx)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            onFinishedEditListener.onSuccess("수정하였습니다.")
                        }

                        500 -> onFinishedEditListener.onFailure("수정에 실패하였습니다.")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    onFinishedEditListener.onFailure("통신에 실패하였습니다.")
                }
            })
    }
}