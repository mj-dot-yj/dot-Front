package com.example.dot.presentation.todo

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dot.data.model.TodoItem
import com.example.dot.databinding.FragmentTodoBinding
import com.example.dot.util.GlobalApplication
import org.json.JSONArray
import java.time.LocalDate
import java.util.Calendar

class TodoFragment : Fragment(), TodoViewModel.onGetAllTodoListener, TodoViewModel.onModifyStateListener {

    private var binding: FragmentTodoBinding? = null
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        setupClickListener()
        calendar = Calendar.getInstance()
        var todoDate = LocalDate.now().toString()
        binding!!.date.text = todoDate

        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        var date = binding!!.date.text.toString()
        val userId = GlobalApplication.prefs.getString("idx", "")
        todoViewModel.getAllTodo(userId, date, onGetAllTodoListener = this)
    }

    private fun setupClickListener() {

        binding!!.addTodo.setOnClickListener {
            var intent = Intent(this.activity, SavePriorityActivity::class.java)
            intent.putExtra("date", binding!!.date.text)
            startActivity(intent)
        }

        binding!!.calendar.setOnClickListener {
            var date = ""
            val data = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                if(month in 0..9) {
                    if(day in 0..9) {
                        date = "${year}-0${month+1}-0${day}"
                    }
                    else {
                        date = "${year}-0${month+1}-${day}"
                    }
                    binding!!.date.text = date
                }
                else {
                    if(day in 0..9) {
                        date = "${year}-${month+1}-0${day}"
                    }
                    else {
                        date = "${year}-${month+1}-${day}"
                    }
                    binding!!.date.text = date
                }
                val userId = GlobalApplication.prefs.getString("idx", "")
                calendar.set(year, month, day)
                todoViewModel.getAllTodo(userId, date, onGetAllTodoListener = this)

            }
            context?.let { it1 -> DatePickerDialog(it1, data, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show() }
        }
    }

    private fun showTodo(important_urgent: ArrayList<TodoItem>, important_noturgent: ArrayList<TodoItem>, notimportant_urgent: ArrayList<TodoItem>, notimportant_noturgent: ArrayList<TodoItem>) {
        val Adapter_important_urgent = TodoAdapter(important_urgent)
        val Adapter_important_noturgent = TodoAdapter(important_noturgent)
        val Adapter_notimportant_urgent = TodoAdapter(notimportant_urgent)
        val Adapter_notimportant_noturgent = TodoAdapter(notimportant_noturgent)
        Adapter_important_urgent.notifyDataSetChanged()
        Adapter_important_noturgent.notifyDataSetChanged()
        Adapter_notimportant_urgent.notifyDataSetChanged()
        Adapter_notimportant_noturgent.notifyDataSetChanged()

        binding!!.importantUrgent.adapter = Adapter_important_urgent
        binding!!.importantUrgent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Adapter_important_urgent.itemClickListener = object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = important_urgent[position]
                val intent = Intent(context, TodoInfoActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        //체크 버튼 클릭 시 취소선
        Adapter_important_urgent.checkClickListener = object : TodoAdapter.OnCheckClickListener {
            override fun onCheckClick(check: Boolean, holder: TodoAdapter.TodoViewHolder, position: Int) {
                var state = ""
                if(check) {
                    holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    state = "DONE"
                }
                else {
                    holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    state = "IN_PROGRESS"
                }
                todoViewModel.saveState(state, important_urgent[position].id, this@TodoFragment)
            }
        }

        binding!!.importantNoturgent.adapter = Adapter_important_noturgent
        binding!!.importantNoturgent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Adapter_important_noturgent.itemClickListener = object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = important_noturgent[position]
                val intent = Intent(context, TodoInfoActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        Adapter_important_noturgent.checkClickListener = object : TodoAdapter.OnCheckClickListener {
            override fun onCheckClick(check: Boolean, holder: TodoAdapter.TodoViewHolder, position: Int) {
                var state = ""
                if(check) {
                    holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    state = "DONE"
                }
                else {
                    holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    state = "IN_PROGRESS"
                }
                todoViewModel.saveState(state, important_noturgent[position].id, this@TodoFragment)
            }
        }

        binding!!.notimportantUrgent.adapter = Adapter_notimportant_urgent
        binding!!.notimportantUrgent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Adapter_notimportant_urgent.itemClickListener = object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = notimportant_urgent[position]
                val intent = Intent(context, TodoInfoActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        Adapter_notimportant_urgent.checkClickListener = object : TodoAdapter.OnCheckClickListener {
            override fun onCheckClick(check: Boolean, holder: TodoAdapter.TodoViewHolder, position: Int) {
                var state = ""
                if(check) {
                    holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    state = "DONE"
                }
                else {
                    holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    state = "IN_PROGRESS"
                }
                todoViewModel.saveState(state, notimportant_urgent[position].id, this@TodoFragment)
            }
        }

        binding!!.notimportantNoturgent.adapter = Adapter_notimportant_noturgent
        binding!!.notimportantNoturgent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Adapter_notimportant_noturgent.itemClickListener = object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = notimportant_noturgent[position]
                val intent = Intent(context, TodoInfoActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
        Adapter_notimportant_noturgent.checkClickListener = object : TodoAdapter.OnCheckClickListener {
            override fun onCheckClick(check: Boolean, holder: TodoAdapter.TodoViewHolder, position: Int) {
                var state = ""
                if(check) {
                    holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    state = "DONE"
                }
                else {
                    holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    state = "IN_PROGRESS"
                }
                todoViewModel.saveState(state, notimportant_noturgent[position].id, this@TodoFragment)
            }
        }
    }

    override fun onSuccess(todoInfoResponseArray: JSONArray) {
        var important_urgent = ArrayList<TodoItem>()
        var important_noturgent = ArrayList<TodoItem>()
        var notimportant_urgent = ArrayList<TodoItem>()
        var notimportant_noturgent = ArrayList<TodoItem>()

        for(i in 0 until todoInfoResponseArray.length()) {
            var iObject = todoInfoResponseArray.getJSONObject(i)
            var id = iObject.getString("id").toDouble().toInt().toString()
            var title = iObject.getString("title")
            var startTime = iObject.getString("startTime")
            var endTime = iObject.getString("endTime")
            var state = iObject.getString("state")
            var check = false
            if(state == "DONE") { check = true }
            var todoItem = TodoItem(id, check, title, startTime, endTime)
            var priority = iObject.getString("priority")

            when(priority){
                "IMPORTANT_URGENT" -> { important_urgent.add(todoItem) }
                "IMPORTANT_NOTURGENT" -> { important_noturgent.add(todoItem) }
                "NOTIMPORTANT_URGENT" -> { notimportant_urgent.add(todoItem) }
                "NOTIMPORTANT_NOTURGENT" -> { notimportant_noturgent.add(todoItem) }
            }
        }
        showTodo(important_urgent, important_noturgent, notimportant_urgent, notimportant_noturgent)
    }

    override fun onFailure(message: String) {
        Toast.makeText(context, "접속 실패", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessModifyState(message: String) {

    }

    override fun onFailureModifyState(message: String) {
        Toast.makeText(context, "진행 수정 실패", Toast.LENGTH_SHORT).show()
    }
}