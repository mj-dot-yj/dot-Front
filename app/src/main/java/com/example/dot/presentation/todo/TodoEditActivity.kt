package com.example.dot.presentation.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.data.model.TodoInfoResponse
import com.example.dot.data.model.TodoRequest
import com.example.dot.databinding.ActivityTodoEditBinding
import com.example.dot.presentation.common.ConfirmDialog
import com.example.dot.util.GlobalApplication
import java.time.LocalTime

class TodoEditActivity : AppCompatActivity(), TodoInfoViewModel.OnGetDataListener,
    TodoEditViewModel.OnFinishedListener {
    private var mBinding: ActivityTodoEditBinding? = null
    private val binding get() = mBinding!!
    private lateinit var saveTodoViewModel: TodoEditViewModel
    private lateinit var todoInfoViewModel: TodoInfoViewModel
    private var alarmed: Long? = null
    private var priority: String? = null
    private var todoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTodoEditBinding.inflate(layoutInflater)
        saveTodoViewModel = ViewModelProvider(this)[TodoEditViewModel::class.java]
        todoInfoViewModel = ViewModelProvider(this)[TodoInfoViewModel::class.java]

        binding!!.startSwitch.isChecked = false
        binding!!.endSwitch.isChecked = false

        setupClickListener()
        setSpinnerAdapter()
        todoId = intent.getStringExtra("id")!!
        setData()

        setContentView(binding.root)
    }

    private fun setupClickListener() {

        binding!!.backButton.setOnClickListener {
            val intent = Intent(this, TodoInfoActivity::class.java)
            intent.putExtra("id", todoId)
            startActivity(intent)
            finish()
        }

        binding!!.startSwitch.setOnCheckedChangeListener { _, _ ->
            if (binding!!.startSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
                setStartTime()
            } else if (!binding!!.startSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
            }
        }

        binding!!.endSwitch.setOnCheckedChangeListener { _, _ ->
            if (binding!!.endSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
                setEndTime()
            } else if (!binding!!.endSwitch.isChecked) {
                binding.TimeContainer.removeAllViews()
            }
        }

        binding!!.btnDone.setOnClickListener {
            var title = binding.titleValue.text.toString()
            if (title.equals("") || title == null) {
                title = "title"
            }
            var content = binding.memoValue.text.toString()
            if (content.equals("") || content == null) {
                content = "content"
            }
            var user_id = GlobalApplication.prefs.getString("idx", "").toLong()
            var start_time = binding.startTimeValue.text.toString()
            var end_time = binding.endTimeValue.text.toString()
            var date = intent.getStringExtra("date")

            val todoRequest =
                TodoRequest(user_id, title, content, start_time, end_time, alarmed, priority, date)
            saveTodoViewModel.saveTodo(todoRequest, onFinishedListener = this, todoId!!)
        }
    }

    private fun setStartTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer, true)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)

        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            val startTime = LocalTime.of(hourOfDay, minute)
            binding.startTimeValue.text = startTime.toString()
        }
    }

    private fun setEndTime() {
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.set_time, binding.TimeContainer, true)
        val timePicker: TimePicker = v.findViewById(R.id.setTime)

        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            val endTime = LocalTime.of(hourOfDay, minute)
            binding.endTimeValue.text = endTime.toString()
        }
    }

    private fun setSpinnerAdapter() {
        val alarmList = resources.getStringArray(R.array.alarm)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarmList)
        binding.alarmValue.adapter = adapter
        binding.alarmValue.setSelection(0)

        binding.alarmValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        alarmed = 0
                    }

                    1 -> {
                        alarmed = 5
                    }

                    2 -> {
                        alarmed = 10
                    }

                    3 -> {
                        alarmed = 15
                    }

                    4 -> {
                        alarmed = 30
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                alarmed = 0
            }
        }

        val priorityList = resources.getStringArray(R.array.priority)
        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, priorityList)
        binding.priorityValue.adapter = adapter2
        binding.priorityValue.setSelection(0)

        binding.priorityValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        priority = "IMPORTANT_URGENT"
                    }

                    1 -> {
                        priority = "NOTIMPORTANT_URGENT"
                    }

                    2 -> {
                        priority = "IMPORTANT_NOTURGENT"
                    }

                    3 -> {
                        priority = "NOTIMPORTANT_NOTURGENT"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                priority = "IMPORTANT_URGENT"
            }
        }
    }

    private fun setData() {
        todoInfoViewModel.showTodoInfo(todoId!!, onGetDataListener = this)
    }

    override fun onSuccessGetData(todoInfoResponse: TodoInfoResponse) {
        binding!!.titleValue.setText(todoInfoResponse.title)
        binding!!.startTimeValue.text = todoInfoResponse.startTime
        binding!!.endTimeValue.text = todoInfoResponse.endTime
        binding!!.memoValue.setText(todoInfoResponse.content)

        when (todoInfoResponse.alarmed) {
            "0.0" -> {
                alarmed = 0
            }

            "5.0" -> {
                alarmed = 1
            }

            "10.0" -> {
                alarmed = 2
            }

            "15.0" -> {
                alarmed = 3
            }

            "20.0" -> {
                alarmed = 4
            }
        }
        binding!!.alarmValue.setSelection(alarmed!!.toInt())

        when (todoInfoResponse.priority) {
            "IMPORTANT_URGENT" -> {
                priority = "0"
            }

            "NOTIMPORTANT_URGENT" -> {
                priority = "1"
            }

            "IMPORTANT_NOTURGENT" -> {
                priority = "2"
            }

            "NOTIMPORTANT_NOTURGENT" -> {
                priority = "3"
            }
        }
        binding!!.priorityValue.setSelection(priority!!.toInt())
    }

    override fun onFailureGetData(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(message: String) {
        val dialogPopup = createDialog(message)
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialogPopup.cancel()
            val intent = Intent(this, TodoInfoActivity::class.java)
            intent.putExtra("id", todoId)
            startActivity(intent)
            finish()
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createDialog(message: String): ConfirmDialog {
        val dialogPopup = ConfirmDialog(this@TodoEditActivity, message)
        dialogPopup.setOkPopup()
        return dialogPopup
    }
}