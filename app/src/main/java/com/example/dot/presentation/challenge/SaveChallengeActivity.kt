package com.example.dot.presentation.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TimePicker
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.data.model.ChallengeRequest
import com.example.dot.databinding.ActivitySaveChallengeBinding
import com.example.dot.util.GlobalApplication
import java.time.LocalTime

class SaveChallengeActivity : AppCompatActivity(), SaveChallengeViewModel.OnFinishedSaveChallengeListener {
    private var mbinding: ActivitySaveChallengeBinding? = null
    private val binding get() = mbinding!!
    private var alarmed: Long? = null
    private var totalCount: Long? = null
    private var period: ArrayList<String> = ArrayList()
    private lateinit var saveChallengeViewModel: SaveChallengeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivitySaveChallengeBinding.inflate(layoutInflater)
        saveChallengeViewModel = ViewModelProvider(this)[SaveChallengeViewModel::class.java]

        setupClickListener()
        setSpinnerAdapter()

        setContentView(binding.root)
    }

    fun setupClickListener() {
        //뒤로 가기 버튼
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        //완료 버튼
        binding.btnDone.setOnClickListener {
            val userId = GlobalApplication.prefs.getString("idx", "").toLong()
            var title = binding.title.text.toString()
            if(title.equals("") || title == null) { title = "title" }
            val startTime = binding.startTime.text.toString()
            val endTime = binding.endTime.text.toString()
            totalCount = binding.total.text.toString().toLong()
            val challengeRequest = ChallengeRequest(userId, title, startTime, endTime, alarmed, totalCount, period)
            saveChallengeViewModel.saveChallenge(challengeRequest, onFinishedSaveChallengeListener = this)
        }

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
    }
    fun onCheckClicked(view: View) {
        if(view is CheckBox) {
            if(view.isChecked) {
                binding.total.setText("0")
                binding.total.isEnabled = false
            }
            else {
                binding.total.isEnabled = true
            }
        }
    }

    //선택 요일 설정
    fun onToggleButtonClicked(view: View) {
        if( view is ToggleButton) {
            val day = view.textOn.toString()
            if(view.isChecked) {
                period.add(day)
            } else {
                period.remove(day)
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
                    0 -> {  alarmed = 0 }
                    1 -> {  alarmed = 5 }
                    2 -> {  alarmed = 10 }
                    3 -> {  alarmed = 15 }
                    4 -> {  alarmed = 30 }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                alarmed = 0
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

    override fun onSuccess(message: String) {
        Log.d("success", message)
    }

    override fun onFailure(message: String) {
        Log.d("fail", message)
    }
}