package com.example.dot.presentation.todo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dot.databinding.FragmentTodoBinding
import java.time.LocalDate

class TodoFragment : Fragment() {

    private var binding: FragmentTodoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        setupClickListener()

        binding!!.date.text = LocalDate.now().toString()

        return binding!!.root
    }

    private fun setupClickListener() {

        binding!!.addTodo.setOnClickListener {
            var intent = Intent(this.activity, SavePriorityActivity::class.java)
            startActivity(intent)
        }

    }
}