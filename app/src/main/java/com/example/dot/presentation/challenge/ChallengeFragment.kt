package com.example.dot.presentation.challenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dot.R
import com.example.dot.databinding.FragmentChallengeBinding
import com.example.dot.util.GlobalApplication

class ChallengeFragment : Fragment() {
    private var binding: FragmentChallengeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeBinding.inflate(inflater, container, false)

        binding!!.textMyOwn.setOnClickListener {
            val intent = Intent(this.activity, SaveChallengeActivity::class.java)
            startActivity(intent)
        }

        return binding!!.root
    }

}