package com.example.dot.presentation.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import com.example.dot.R
import com.example.dot.databinding.ActivitySaveChallengeBinding
import java.time.LocalTime

class SaveChallengeActivity : AppCompatActivity() {
    private var mbinding: ActivitySaveChallengeBinding? = null
    private val binding get() = mbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivitySaveChallengeBinding.inflate(layoutInflater)

        setupClickListener()
        setSpinnerAdapter()

        setContentView(binding.root)
    }

    fun setupClickListener() {
        //시작·종료 시간 설정
        binding.startSwitch.setOnCheckedChangeListener { _, _ ->
            if(binding.startSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
                setStartTime()
            }
            else {
                binding.TimeContainer.removeAllViews()
            }
        }
        binding.endSwitch.setOnCheckedChangeListener { _, _ ->
            if(binding.endSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
                setEndTime()
            }
            else {
                binding.TimeContainer.removeAllViews()
            }
        }

        //목표 횟수 무제한
        binding.countCheck.setOnCheckedChangeListener { _, checked ->
            if(checked) {

            }
            else {

            }
        }

        //반복 주기 설정
        binding.mon.setOnCheckedChangeListener { _, checked ->
            if(checked) {
                //체크될 경우 함수 실행

            }
        }
    }

    private fun setSpinnerAdapter() {
        val alarmList = resources.getStringArray(R.array.alarm)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarmList)
        binding.Alarm.adapter = adapter
        binding.Alarm.setSelection(0)

        binding.Alarm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {}
                    1 -> {}
                    2 -> {}
                    3 -> {}
                    4 -> {}
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun setStartTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)

        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            val startTime = LocalTime.of(hourOfDay, minute)
            binding.startTime.text = startTime.toString()
        }
    }

    private fun setEndTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)

        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            val endTime = LocalTime.of(hourOfDay, minute)
            binding.endTime.text = endTime.toString()
        }
    }


}