package com.example.dot.presentation.todo

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dot.R
import com.example.dot.data.model.TodoItem

class TodoAdapter(val todoList: ArrayList<TodoItem>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    interface OnCheckClickListener {
        fun onCheckClick(check: Boolean, holder: TodoViewHolder, position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null
    var checkClickListener: OnCheckClickListener? = null

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val check = itemView.findViewById<CheckBox>(R.id.check)
        val title = itemView.findViewById<TextView>(R.id.title)
        val start_time = itemView.findViewById<TextView>(R.id.start_time)
        val end_time = itemView.findViewById<TextView>(R.id.end_time)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.check.isChecked = todoList[position].check
        holder.title.text = todoList[position].title
        holder.start_time.text = todoList[position].startTime
        holder.end_time.text = todoList[position].endTime

        if(holder.check.isChecked) {
            holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.check.setOnClickListener {
            if(holder.check.isChecked) {
                checkClickListener?.onCheckClick(true, holder, position)
            }
            else {
                checkClickListener?.onCheckClick(false, holder, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.count()
    }
}