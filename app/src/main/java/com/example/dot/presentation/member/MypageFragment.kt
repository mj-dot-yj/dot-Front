package com.example.dot.presentation.member

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dot.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {

    private var binding: FragmentMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        setupClickListener()

        return binding!!.root
    }

    private fun setupClickListener() {
        // 회원 탈퇴 버튼
        binding!!.withdrawalButton.setOnClickListener {
            val intent = Intent(this.activity, MemberWithdrawActivity::class.java)
            startActivity(intent)
        }
    }
}