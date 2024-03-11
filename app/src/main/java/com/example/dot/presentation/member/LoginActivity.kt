package com.example.dot.presentation.member

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.databinding.ActivityLoginBinding
import com.example.dot.presentation.HomeActivity
import com.example.dot.presentation.MainActivity
import com.example.dot.presentation.common.ConfirmDialog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity(), LoginViewModel.OnFinishedLoginListener {
    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        setupClickListener()
    }

    private fun setupClickListener() {
        // 회원가입 페이지
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 일반 로그인
        binding.loginButton.setOnClickListener {
            val id = binding.inputId.text.toString()
            val pw = binding.inputPw.text.toString()
            loginViewModel.login(id, pw, onFinishedLoginListener = this)!!
        }

        // 카카오 로그인 버튼
        binding.KakaoLoginButton.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    when {
                        error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                            createDialog("접근이 거부 됨(동의 취소)")
                        }

                        error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                            createDialog("유효하지 않은 앱")
                        }

                        error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                            createDialog("인증 수단이 유효하지 않아 인증할 수 없는 상태")
                        }

                        error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                            createDialog("요청 파라미터 오류")
                        }

                        error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                            createDialog("유효하지 않은 scope ID")
                        }

                        error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                            createDialog("설정이 올바르지 않음")
                        }

                        error.toString() == AuthErrorCause.ServerError.toString() -> {
                            createDialog("서버 내부 에러")
                        }

                        error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                            createDialog("앱이 요청 권한이 없음")
                        }

                        else -> { //Unknown
                            createDialog("기타 에러")
                        }
                    }
                } else if (token != null) {
                    onSuccess()
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    private fun createDialog(message: String) {
        val dialogPopup = ConfirmDialog(this@LoginActivity, message)
        dialogPopup.setOkPopup()
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialogPopup.cancel()
        }
    }

    override fun onSuccess() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailure(message: String) {
        createDialog(message)
    }
}
