package com.example.dot.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dot.R
import com.example.dot.data.api.ApiObject
import com.example.dot.databinding.ActivityMainBinding
import com.example.dot.presentation.common.ConfirmDialog
import com.example.dot.presentation.member.LoginActivity
import com.example.dot.util.GlobalApplication
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListener()
    }

    private fun setupClickListener() {
        //test - 사용자 이름 조회 API
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                if (GlobalApplication.prefs.getString("accessToken", "").isEmpty()) {
                    Toast.makeText(this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show()
                } else {
                    binding.userName.text = ""
                }
            } else if (user != null) {
                binding.userName.text = user.kakaoAccount?.profile?.nickname
            }
        }

        // kakao 로그아웃
        binding.kakaoLogoutButton.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    if (GlobalApplication.prefs.getString("accessToken", "").isEmpty()) {
                        Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                    } else {
                        val apiObject = ApiObject
                        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

                        apiObject.manageMember().memberLogout(accessToken)
                            .enqueue(object : Callback<Void> {
                                override fun onResponse(
                                    call: Call<Void>,
                                    response: Response<Void>
                                ) {
                                    when (response.code()) {
                                        200 -> {
                                            val dlgPopup =
                                                ConfirmDialog(this@MainActivity, "로그아웃 되었습니다.")
                                            dlgPopup.show()
                                            dlgPopup.window!!.setLayout(800, 450)
                                            dlgPopup.setCancelable(false)
                                            dlgPopup.findViewById<Button>(R.id.okBtn)
                                                .setOnClickListener {
                                                    dlgPopup.cancel()
                                                    GlobalApplication.prefs.setString(
                                                        "accessToken",
                                                        ""
                                                    )
                                                    val intent =
                                                        Intent(
                                                            this@MainActivity,
                                                            LoginActivity::class.java
                                                        )
                                                    startActivity(intent)
                                                    finish()
                                                }
                                        }

                                        500 -> Toast.makeText(
                                            this@MainActivity,
                                            "로그아웃 실패 : 서버 오류",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Log.d("로그아웃 통신 실패", t.message.toString())
                                }
                            })
                    }
                } else {
                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        // kakao 탈퇴
        binding.kakaoUnlinkButton.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}