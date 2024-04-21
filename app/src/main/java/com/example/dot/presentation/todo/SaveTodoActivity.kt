package com.example.dot.presentation.todo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.data.model.TodoRequest
import com.example.dot.databinding.ActivitySaveTodoBinding
import com.example.dot.presentation.HomeActivity
import com.example.dot.presentation.common.ConfirmDialog
import com.example.dot.util.GlobalApplication
import java.time.LocalDate
import java.time.LocalTime

import java.time.format.DateTimeFormatter



class SaveTodoActivity : AppCompatActivity(), SaveTodoViewModel.OnFinishedSaveTodoListener {
    private var mBinding : ActivitySaveTodoBinding? = null
    private val binding get() = mBinding!!
    private lateinit var saveTodoViewModel: SaveTodoViewModel
    private var alarmed: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySaveTodoBinding.inflate(layoutInflater)
        saveTodoViewModel = ViewModelProvider(this)[SaveTodoViewModel::class.java]

        binding!!.startSwitch.isChecked = false
        binding!!.endSwitch.isChecked = false

        setupClickListener()
        setSpinnerAdapter()

        setContentView(binding.root)
    }

    private fun setupClickListener() {

        binding!!.backButton.setOnClickListener {
            val intent = Intent(this, SavePriorityActivity::class.java)
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
            var title = binding.TodoTitle.text.toString()
            if(title.equals("") || title == null) { title = "title" }
            var content = binding.content.text.toString()
            if(content.equals("") || content == null) { content = "content" }
            var user_id = GlobalApplication.prefs.getString("idx", "").toLong()
            var start_time = binding.startTime.text.toString()
            var end_time = binding.endTime.text.toString()
            var priority = getIntent().getStringExtra("priority")
            var Date = LocalDate.now().toString()

            val todoRequest = TodoRequest(user_id, title, content, start_time, end_time, alarmed, priority, Date)
            saveTodoViewModel.saveTodo(todoRequest, onFinishedsaveTodoListener = this)
        }
    }

    private fun setStartTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer,true)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)

        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            val startTime = LocalTime.of(hourOfDay, minute)
            binding.startTime.text = startTime.toString()
        }
    }

    private fun setEndTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer,true)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)

        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            val endTime = LocalTime.of(hourOfDay, minute)
            binding.endTime.text = endTime.toString()
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
                    0 -> { alarmed = 0 }
                    1 -> { alarmed = 5 }
                    2 -> { alarmed = 10 }
                    3 -> { alarmed = 15 }
                    4 -> { alarmed = 30 }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                alarmed = 0
            }
        }
    }

    override fun onSuccess(message: String) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, "할 일 저장 실패!", Toast.LENGTH_SHORT).show()
    }
}