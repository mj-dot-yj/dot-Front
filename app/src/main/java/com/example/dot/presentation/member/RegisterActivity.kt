package com.example.dot.presentation.member

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dot.R
import com.example.dot.databinding.ActivityRegisterBinding
import com.example.dot.presentation.common.ConfirmDialog

class RegisterActivity : AppCompatActivity() {
    private var mBinding: ActivityRegisterBinding? = null
    private val binding get() = mBinding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListener()
    }

    private fun setupClickListener() {
        //뒤로가기 버튼 (register -> login)
        binding.backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //회원가입 완료 버튼 클릭 시 로그인 화면 이동
        binding.registerButton.setOnClickListener {
            createDialog("회원가입에 성공하였습니다.")
        }
    }

    private fun createDialog(message: String) {
        val dialogPopup = ConfirmDialog(this@RegisterActivity, message)
        dialogPopup.setOkPopup()
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialogPopup.cancel()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}