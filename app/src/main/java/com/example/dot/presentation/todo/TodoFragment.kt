package com.example.dot.presentation.todo

import android.app.DatePickerDialog
import android.content.Intent
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

class TodoFragment : Fragment(), TodoViewModel.onGetAllTodoListener {

    private var binding: FragmentTodoBinding? = null
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        setupClickListener()

        var todoDate = LocalDate.now().toString()
        binding!!.date.text = todoDate
        val userId = GlobalApplication.prefs.getString("idx", "")
        todoViewModel.getAllTodo(userId, todoDate, onGetAllTodoListener = this)

        return binding!!.root
    }

    private fun setupClickListener() {

        binding!!.addTodo.setOnClickListener {
            var intent = Intent(this.activity, SavePriorityActivity::class.java)
            intent.putExtra("date", binding!!.date.text)
            startActivity(intent)
        }

        binding!!.calendar.setOnClickListener {
            val calendar = Calendar.getInstance()
            var date = ""
            val data = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                if(month in 0..9) {
                    date = "${year}-0${month+1}-${day}"
                    binding!!.date.text = date
                }
                else {
                    date = "${year}-${month+1}-${day}"
                    binding!!.date.text = date
                }
                val userId = GlobalApplication.prefs.getString("idx", "")
                todoViewModel.getAllTodo(userId, date, this)

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
    }

    override fun onSuccess(todoInfoResponseArray: JSONArray) {
        var important_urgent = ArrayList<TodoItem>()
        var important_noturgent = ArrayList<TodoItem>()
        var notimportant_urgent = ArrayList<TodoItem>()
        var notimportant_noturgent = ArrayList<TodoItem>()

        for(i in 0 until todoInfoResponseArray.length()) {
            var iObject = todoInfoResponseArray.getJSONObject(i)
            var id = iObject.getString("id")
            var title = iObject.getString("title")
            var startTime = iObject.getString("startTime")
            var endTime = iObject.getString("endTime")
            var todoItem = TodoItem(id, title, startTime, endTime)
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
}