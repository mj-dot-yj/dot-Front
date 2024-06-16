package com.example.dot.presentation.challenge

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dot.R
import com.example.dot.data.model.ChallengeItem
import java.time.LocalDate

class ChallengeAdapter(val challengeList: ArrayList<ChallengeItem>): RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(positon: Int) {}
    }

    interface OnCheckClickListener {
        fun onCheckClick(check:Boolean, holder: ChallengeViewHolder, position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null
    var checkClickListener: OnCheckClickListener? = null
    inner class ChallengeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val challengeStartTime = itemView.findViewById<TextView>(R.id.startTime)
        val challengeEndTime = itemView.findViewById<TextView>(R.id.endTime)
        val count = itemView.findViewById<TextView>(R.id.count)
        val total = itemView.findViewById<TextView>(R.id.totalCount)
        val check = itemView.findViewById<Button>(R.id.check)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
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
        holder.total.text = challengeList[position].totalCount.toString()
        holder.check.text = LocalDate.now().toString() + "\n 완료!"

        if(challengeList[position].checked == "DONE") {
            holder.check.setBackgroundResource(R.drawable.check_border)
            holder.check.setTextColor(Color.DKGRAY)
        }

        holder.check.setOnClickListener {
            checkClickListener?.onCheckClick(true, holder, position)
        }
    }

    override fun getItemCount(): Int {
        return challengeList.count()
    }
}