package com.example.dot.presentation.member

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.dot.R
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.databinding.FragmentMypageBinding
import com.example.dot.presentation.common.ConfirmDialog
import com.example.dot.util.GlobalApplication
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {

    private var binding: FragmentMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        showMemberInfo()

        setupClickListener()

        return binding!!.root
    }

    private fun setupClickListener() {
        // 회원 탈퇴 버튼
        binding!!.withdrawalButton.setOnClickListener {
            val intent = Intent(this.activity, MemberWithdrawActivity::class.java)
            startActivity(intent)
        }
    }
    private fun showMemberInfo() {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")
        ApiObject.manageMember().MemberByEmail(accessToken).enqueue(object: Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>)
            {
                when(response.code()) {
                    200 -> {
                        var jsonObject = JSONObject(response.body()!!.result.toString())
                        binding!!.email.text = jsonObject.get("email").toString()
                        binding!!.name.text = jsonObject.get("name").toString()
                    }
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                createDialog("사용자 정보 조회 실패")
            }
        })
    }

    private fun createDialog(message: String) {
        val dialog = ConfirmDialog(Activity(), message)
        dialog.setOkPopup()
        dialog.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialog.cancel()
        }
    }
}