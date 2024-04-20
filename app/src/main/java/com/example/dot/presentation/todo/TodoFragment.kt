package com.example.dot.presentation.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dot.data.model.TodoItem
import com.example.dot.databinding.FragmentTodoBinding
import com.example.dot.util.GlobalApplication
import org.json.JSONArray
import java.time.LocalDate

class TodoFragment : Fragment(), TodoViewModel.onGetAllTodoListener {

    private var binding: FragmentTodoBinding? = null
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoId: String

    private var important_urgent = ArrayList<TodoItem>()
    private var important_noturgent = ArrayList<TodoItem>()
    private var notimportant_urgent = ArrayList<TodoItem>()
    private var notimportant_noturgent = ArrayList<TodoItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        setupClickListener()

        binding!!.date.text = LocalDate.now().toString()

        val userId = GlobalApplication.prefs.getString("idx", "")
        todoViewModel.getAllTodo(userId, onGetAllTodoListener = this)

        return binding!!.root
    }

    private fun setupClickListener() {

        binding!!.addTodo.setOnClickListener {
            var intent = Intent(this.activity, SavePriorityActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showTodo() {
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

        binding!!.importantNoturgent.adapter = Adapter_important_noturgent
        binding!!.importantNoturgent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding!!.notimportantUrgent.adapter = Adapter_notimportant_urgent
        binding!!.notimportantUrgent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding!!.notimportantNoturgent.adapter = Adapter_notimportant_noturgent
        binding!!.notimportantNoturgent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override fun onSuccess(todoInfoResponseArray: JSONArray) {
        for(i in 0 until todoInfoResponseArray.length()) {
            var iObject = todoInfoResponseArray.getJSONObject(i)
            var title = iObject.getString("title")
            var startTime = iObject.getString("startTime")
            var endTime = iObject.getString("endTime")
            var todoItem = TodoItem(title, startTime, endTime)
            var priority = iObject.getString("priority")
            when(priority){
                "IMPORTANT_URGENT" -> { important_urgent.add(todoItem) }
                "IMPORTANT_NOTURGENT" -> { important_noturgent.add(todoItem) }
                "NOTIMPORTANT_URGENT" -> { notimportant_urgent.add(todoItem) }
                "NOTIMPORTANT_NOTURGENT" -> { notimportant_noturgent.add(todoItem) }
            }
        }
        showTodo()
    }

    override fun onFailure(message: String) {

    }
}