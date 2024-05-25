package com.example.dot.presentation.challenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dot.R
import com.example.dot.data.model.ChallengeItem

class ChallengeAdapter(val challengeList: ArrayList<ChallengeItem>): RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    inner class ChallengeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val challengeStartTime = itemView.findViewById<TextView>(R.id.challengeStartTime)
        val challengeEndTime = itemView.findViewById<TextView>(R.id.challengeEndTime)
        val count = itemView.findViewById<TextView>(R.id.count)
        val total = itemView.findViewById<TextView>(R.id.total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.challenge_item, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.title.text = challengeList[position].title
        holder.challengeStartTime.text = challengeList[position].startTime
        holder.challengeEndTime.text = challengeList[position].endTime
        holder.count.text = challengeList[position].count.toString()
        holder.total.text = challengeList[position].total.toString()
    }

    override fun getItemCount(): Int {
        return challengeList.count()
    }
}