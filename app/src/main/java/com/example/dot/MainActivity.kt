package com.example.dot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dot.util.GlobalApplicaion
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //test - 사용자 이름 조회 API
        val user_name = findViewById<TextView>(R.id.user_name)
        UserApiClient.instance.me { user, error ->
            if(error != null) {
                if (GlobalApplicaion.prefs.getString("accessToken", "").isEmpty()){
                    Toast.makeText(this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show()
                } else {
                    user_name.text = ""
                }
            }
            else if(user != null) {
                user_name.text = user.kakaoAccount?.profile?.nickname
            }
        }

        //kakao 로그아웃
        val kakao_logout_button = findViewById<Button>(R.id.kakao_logout_button)
        kakao_logout_button.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    if (GlobalApplicaion.prefs.getString("accessToken", "").isEmpty()) {
                        Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                    } else {
                        val api = Api.create()
                        val accessToken = GlobalApplicaion.prefs.getString("accessToken", "")

                        api.userLogout(accessToken).enqueue(object : Callback<Void> {
                            override fun onResponse(
                                call: Call<Void>,
                                response: Response<Void>
                            ) {
                                when (response.code()) {
                                    200 -> {
                                        GlobalApplicaion.prefs.setString("accessToken", "")
                                        val intent =
                                            Intent(this@MainActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                        finish()
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
                }
                else {
                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        //kakao 탈퇴
        val kakao_unlink_button = findViewById<Button>(R.id.kakao_unlink_button)
        kakao_unlink_button.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if(error!=null) {
                    Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}