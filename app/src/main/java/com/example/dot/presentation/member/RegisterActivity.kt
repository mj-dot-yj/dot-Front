package com.example.dot.presentation.member

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dot.R
import com.example.dot.databinding.ActivityRegisterBinding
import com.example.dot.presentation.common.ConfirmDialog
import java.util.regex.Pattern

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
            //회원가입 입력 유효성 체크
            validation_check()

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

    private fun validation_check() {
        val name = binding.inputName.text.toString()
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        val checkpassword = binding.inputCheckpassword.text.toString()
        val phone = binding.inputPhone.text.toString()
        val emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
        val pwPattern = "^[A-Za-z0-9]{6,12}$"
        val phonePattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"
        //항목이 비었을 때
        if(name=="" || email=="" || password=="" || checkpassword=="" || phone=="") {
            createDialog("회원정보를 모두 입력해주세요")
        }
        else {
            if(Pattern.matches(emailPattern, email)) {
                if(Pattern.matches(pwPattern, password)) {
                    if(password == checkpassword) {
                        if(Pattern.matches(phonePattern, phone)) {
                            createDialog("회원가입에 성공하였습니다.")
                        }
                        else {
                            Toast.makeText( this,"전화번호 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        Toast.makeText( this,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText( this,"비밀번호 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText( this,"이메일 형식이 옳지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}