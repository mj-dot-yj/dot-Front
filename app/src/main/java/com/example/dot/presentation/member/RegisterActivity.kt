package com.example.dot.presentation.member

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.data.model.MemberInfoRequest
import com.example.dot.databinding.ActivityRegisterBinding
import com.example.dot.presentation.common.ConfirmDialog

class RegisterActivity : AppCompatActivity(), RegisterViewModel.OnFinishedRegisterListener {
    private var mBinding: ActivityRegisterBinding? = null
    private val binding get() = mBinding!!
    private lateinit var registerViewModel : RegisterViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

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
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val checkPassword = binding.inputCheckpassword.text.toString()
            val phone = binding.inputPhone.text.toString()
            val memberInfoRequest = MemberInfoRequest(name, email, password, checkPassword, phone)
            register(memberInfoRequest)
        }
    }

    private fun createDialog(message: String) : ConfirmDialog {
        val dialogPopup = ConfirmDialog(this@RegisterActivity, message)
        dialogPopup.setOkPopup()
        return dialogPopup
    }

    override fun onSuccess(message: String) {
        createDialog(message)
        val dialogPopup = createDialog(message)
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onFailure(message: String) {
        val dialogPopup = createDialog(message)
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialogPopup.cancel()
        }
    }

    private fun register(memberInfoRequest : MemberInfoRequest){
        val resultCheckValidation = memberInfoRequest.checkValidation()
        if (resultCheckValidation.isEmpty()){
            registerViewModel.signup(memberInfoRequest, onFinishedRegisterListener = this)!!
        } else {
            val dialogPopup = createDialog(resultCheckValidation)
            dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
                dialogPopup.cancel()
            }
        }
    }
}
