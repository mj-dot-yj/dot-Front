package com.example.dot.presentation.todo

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dot.R
import com.example.dot.data.model.TodoInfoResponse
import com.example.dot.databinding.ActivityTodoInfoBinding
import com.example.dot.presentation.common.ConfirmDialog

class TodoInfoActivity : AppCompatActivity(), TodoInfoViewModel.OnGetDataListener, TodoInfoViewModel.OnDeleteListener {
    private var mBinding: ActivityTodoInfoBinding? = null
    private val binding get() = mBinding!!
    private lateinit var todoInfoViewModel: TodoInfoViewModel
    private lateinit var todoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityTodoInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoInfoViewModel = ViewModelProvider(this)[TodoInfoViewModel::class.java]

        todoId = intent.getStringExtra("id")!!
        setData(todoId)

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.btnEdit.setOnClickListener { }

        binding.btnDelete.setOnClickListener {
            todoInfoViewModel.deleteInfo(todoId, onDeleteListener = this)
        }
    }

    private fun setData(idx: String) {
        todoInfoViewModel.showTodoInfo(idx, onGetDataListener = this)
    }

    override fun onSuccessGetData(todoInfoResponse: TodoInfoResponse) {
        binding!!.titleValue.text = todoInfoResponse.title
        binding!!.startTimeValue.text = todoInfoResponse.startTime
        binding!!.endTimeValue.text = todoInfoResponse.endTime
        binding!!.stateValue.text = todoInfoResponse.state
        binding!!.alarmValue.text = todoInfoResponse.alarmed
        binding!!.memoValue.text = todoInfoResponse.content

    }

    override fun onFailureGetData(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(message: String) {
        val dialogPopup = createDialog(message)
        dialogPopup.findViewById<Button>(R.id.okBtn).setOnClickListener {
            dialogPopup.cancel()
            onBackPressed()
        }
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun createDialog(message: String) : ConfirmDialog {
        val dialogPopup = ConfirmDialog(this@TodoInfoActivity, message)
        dialogPopup.setOkPopup()
        return dialogPopup
    }
}
