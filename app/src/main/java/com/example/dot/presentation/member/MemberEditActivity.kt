package com.example.dot.presentation.member

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.data.model.MemberInfoRequest
import com.example.dot.data.model.MemberInfoResponse
import com.example.dot.databinding.ActivityMemberEditBinding
import com.example.dot.presentation.common.ConfirmDialog

class MemberEditActivity : AppCompatActivity(),
    MemberInfoViewModel.OnGetDataListener,
    MemberEditViewModel.OnFinishedEditListener{
    private var mBinding: ActivityMemberEditBinding? = null
    private val binding get() = mBinding!!
    private lateinit var memberEditViewModel: MemberEditViewModel
    private lateinit var memberInfoViewModel: MemberInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMemberEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memberEditViewModel = ViewModelProvider(this)[MemberEditViewModel::class.java]
        memberInfoViewModel = ViewModelProvider(this)[MemberInfoViewModel::class.java]


        setData()

        setupClickListener()
    }

    private fun setData() {
        memberInfoViewModel.showMemberInfo(OnGetDataListener = this)
    }

    private fun setupClickListener() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.editButton.setOnClickListener {
            val name = binding!!.inputName.text.toString()
            val email = binding!!.inputEmail.text.toString()
            val password = binding!!.inputPassword.text.toString()
            val phone = binding!!.inputPhone.text.toString()
            val memberInfoRequest = MemberInfoRequest(name, email, password, phone)
            memberEditViewModel.editMemberInfo(memberInfoRequest, onFinishedEditListener = this)
        }
    }

    override fun onSuccessGetData(memberInfoResponse: MemberInfoResponse) {
        binding!!.inputName.setText(memberInfoResponse.name)
        binding!!.inputEmail.setText(memberInfoResponse.email)
        binding!!.inputPhone.setText(memberInfoResponse.phone)
    }

    override fun onFailureGetData(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(message: String) {
        createDialog(message)
        val dialogPopup = createDialog(message)
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            onBackPressed()
        }
    }

    override fun onFailure(message: String) {
        val dialogPopup = createDialog(message)
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialogPopup.cancel()
        }
    }

    private fun createDialog(message: String) : ConfirmDialog {
        val dialogPopup = ConfirmDialog(this@MemberEditActivity, message)
        dialogPopup.setOkPopup()
        return dialogPopup
    }
}