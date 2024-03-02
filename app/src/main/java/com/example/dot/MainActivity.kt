package com.example.dot

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.Constants
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //test - 사용자 이름 조회 API
        val user_name = findViewById<TextView>(R.id.user_name)
        UserApiClient.instance.me { user, error ->
            if(error != null) {
                Toast.makeText(this, "사용자 정보 요청 실패", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
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