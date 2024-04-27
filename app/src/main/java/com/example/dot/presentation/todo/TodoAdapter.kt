package com.example.dot.presentation.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dot.R
import com.example.dot.data.model.TodoItem

class TodoAdapter(val todoList: ArrayList<TodoItem>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        holder.title.text = todoList[position].title
        holder.start_time.text = todoList[position].startTime
        holder.end_time.text = todoList[position].endTime
    }

    override fun getItemCount(): Int {
        return todoList.count()
    }
}