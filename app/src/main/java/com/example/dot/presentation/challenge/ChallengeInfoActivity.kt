package com.example.dot.presentation.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dot.data.model.ChallengeInfoResponse
import com.example.dot.databinding.ActivityChallengeInfoBinding

class ChallengeInfoActivity : AppCompatActivity(), ChallengeViewModel.onGetChallengeListener {
    private var mbinding: ActivityChallengeInfoBinding? = null

    private val binding get() = mbinding!!
    private lateinit var challengeViewNodel: ChallengeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityChallengeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        challengeViewNodel = ViewModelProvider(this)[ChallengeViewModel::class.java]
        val idx = intent.getStringExtra("idx")!!
        setData(idx)

        setupClickListener()
    }

    fun setupClickListener() {
        // 뒤로 가기
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    fun setData(idx : String) {
        challengeViewNodel.getChallenge(idx, this)
    }

    override fun onSuccessGetChallenge(challengeInfoResponse: ChallengeInfoResponse) {
        binding.titleValue.text = challengeInfoResponse.title
        binding.startTimeValue.text = challengeInfoResponse.startTime
        binding.endTimeValue.text = challengeInfoResponse.endTime
        binding.stateValue.text = challengeInfoResponse.count.toString() + " / " + challengeInfoResponse.totalCount.toString()
        binding.alarmValue.text = challengeInfoResponse.alarmed
        var days = challengeInfoResponse.days!!
        setToggleButton(days)
    }

    private fun setToggleButton(days: ArrayList<String>) {
        for(day: String in days) {
            when(day) {
                "MON" -> binding.mon.isChecked = true
                "TUE" -> binding.tue.isChecked = true
                "WED" -> binding.wed.isChecked = true
                "THU" -> binding.thu.isChecked = true
                "FRI" -> binding.fri.isChecked = true
                "SAT" -> binding.sat.isChecked = true
                "SUN" -> binding.sun.isChecked = true
            }
        }
    }

    override fun onFailureGetChallenge(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}