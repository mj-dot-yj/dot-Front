package com.example.dot.presentation.member

import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.MemberInfoResponse
import com.example.dot.util.GlobalApplication
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberInfoViewModel : ViewModel() {

    interface OnGetDataListener {
        fun onSuccessGetData(memberInfoResponse: MemberInfoResponse)
        fun onFailureGetData(message: String)
    }

    fun showMemberInfo(onGetDataListener: OnGetDataListener) {
        val idx = GlobalApplication.prefs.getString("idx", "")
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")
        ApiObject.manageMember().memberByEmail(accessToken, idx).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        var jsonObject = JSONObject(response.body()!!.result.toString())
                        val name = jsonObject.get("name").toString()
                        val email = jsonObject.get("email").toString()
                        val phone = jsonObject.get("phone").toString()
                        val memberInfoResponse = MemberInfoResponse(name, email, phone)
                        onGetDataListener.onSuccessGetData(memberInfoResponse)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onGetDataListener.onFailureGetData("통신에 실패하였습니다.")
            }
        })
    }
}