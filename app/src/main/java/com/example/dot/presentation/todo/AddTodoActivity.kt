package com.example.dot.presentation.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import com.example.dot.R
import com.example.dot.databinding.ActivityAddTodoBinding
import com.example.dot.presentation.HomeActivity

class AddTodoActivity : AppCompatActivity() {
    private var mBinding : ActivityAddTodoBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddTodoBinding.inflate(layoutInflater)

        binding!!.startSwitch.isChecked = false
        binding!!.endSwitch.isChecked = false

        setupClickListener()
        setSpinnerAdapter()

        setContentView(binding.root)
    }

    private fun setupClickListener() {

        binding!!.backButton.setOnClickListener {
            val intent = Intent(this, AddPriorityActivity::class.java)
            startActivity(intent)
        }

        binding!!.startSwitch.setOnCheckedChangeListener { _, _ ->
            if(binding!!.startSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
                setStartTime()
            }
            else if(!binding!!.startSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
            }
        }

        binding!!.endSwitch.setOnCheckedChangeListener { _, _ ->
            if(binding!!.endSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
                setEndTime()
            }
            else if(!binding!!.endSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
            }
        }

        binding!!.btnDone.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setStartTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer,true)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)
        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            if(hourOfDay < 12) { binding!!.startTime.text = "오전 ${hourOfDay} : ${minute}" }
            else if(hourOfDay == 12) { binding!!.startTime.text = "오후 ${hourOfDay} : ${minute}" }
            else { binding!!.startTime.text = "오후 ${hourOfDay - 12} : ${minute}" }
        }
    }

    private fun setEndTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer,true)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)
        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            if(hourOfDay < 12) { binding!!.endTime.text = "오전 ${hourOfDay} : ${minute}" }
            else if(hourOfDay == 12) { binding!!.endTime.text = "오후 ${hourOfDay} : ${minute}" }
            else { binding!!.endTime.text = "오후 ${hourOfDay - 12} : ${minute}" }
        }
    }

    private fun setSpinnerAdapter() {
        val alarmList = resources.getStringArray(R.array.alarm)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarmList)
        binding.Alarm.adapter = adapter
        binding.Alarm.setSelection(0)

        binding.Alarm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}