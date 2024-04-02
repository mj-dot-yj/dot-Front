package com.example.dot.presentation.member

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dot.data.model.MemberInfoResponse
import com.example.dot.databinding.FragmentMypageBinding

class MypageFragment : Fragment(), MemberInfoViewModel.OnGetDataListener {

    private var binding: FragmentMypageBinding? = null
    private lateinit var memberInfoViewModel: MemberInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        memberInfoViewModel = ViewModelProvider(this)[MemberInfoViewModel::class.java]

        setData()

        setupClickListener()

        return binding!!.root
    }

    private fun setData() {
        memberInfoViewModel.showMemberInfo(OnGetDataListener = this)
    }

    private fun setupClickListener() {
        // 회원 수정 버튼
        binding!!.editButton.setOnClickListener {
            val intent = Intent(this.activity, MemberEditActivity::class.java)
            startActivity(intent)
        }

        // 회원 탈퇴 버튼
        binding!!.withdrawalButton.setOnClickListener {
            val intent = Intent(this.activity, MemberWithdrawActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSuccessGetData(memberInfoResponse: MemberInfoResponse) {
        binding!!.email.text = memberInfoResponse.email
        binding!!.name.text = memberInfoResponse.name
    }

    override fun onFailureGetData(message: String) {
        Toast.makeText(this.activity, message, Toast.LENGTH_LONG).show()
    }
}