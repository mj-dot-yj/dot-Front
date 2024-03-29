package com.example.dot.presentation.member

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.databinding.ActivityMemberWithdrawBinding
import com.example.dot.presentation.common.ConfirmDialog
import com.example.dot.util.GlobalApplication

class MemberWithdrawActivity : AppCompatActivity(),
    MemberWithdrawViewModel.OnFinishedLoginListener {
    private var mBinding: ActivityMemberWithdrawBinding? = null
    private val binding get() = mBinding!!
    private lateinit var memberWithdrawViewModel: MemberWithdrawViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_withdraw)

        mBinding = ActivityMemberWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memberWithdrawViewModel = ViewModelProvider(this)[MemberWithdrawViewModel::class.java]

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.withdrawButton.setOnClickListener {
            val inputPw = binding.inputPassword.text.toString()
            memberWithdrawViewModel.checkPassword(inputPw, onFinishedLoginListener = this)
        }
    }

    override fun onCheckSuccess() {
        // 탈퇴 진행
        memberWithdrawViewModel.memberWithdraw(onFinishedLoginListener = this)
    }

    override fun onSuccess(message: String) {
        // 탈퇴 성공
        createDialogAndChange(message)
        GlobalApplication.prefs.setString("accessToken", "")
    }

    override fun onFailure(message: String) {
        createDialog(message)
    }

    private fun createDialog(message: String) {
        val dialogPopup = ConfirmDialog(this@MemberWithdrawActivity, message)
        dialogPopup.setOkPopup()
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialogPopup.cancel()
        }
    }

    private fun createDialogAndChange(message: String) {
        val dialogPopup = ConfirmDialog(this@MemberWithdrawActivity, message)
        dialogPopup.setOkPopup()
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            val intent = Intent(this@MemberWithdrawActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}