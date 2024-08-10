package com.example.dot.presentation.challenge

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dot.R
import com.example.dot.data.model.ChallengeInfoResponse
import com.example.dot.data.model.ChallengeItem
import com.example.dot.databinding.FragmentChallengeBinding
import com.example.dot.util.GlobalApplication
import org.json.JSONArray
import org.json.JSONObject

class ChallengeFragment : Fragment(), ChallengeViewModel.onGetAllChallengeListener, ChallengeViewModel.onModifyStateListener {
    private var binding: FragmentChallengeBinding? = null
    private lateinit var challengeViewModel: ChallengeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeBinding.inflate(inflater, container, false)
        challengeViewModel = ViewModelProvider(this)[ChallengeViewModel::class.java]

        var idx = GlobalApplication.prefs.getString("idx", "")
        challengeViewModel.getAllChallenge(idx, onGetAllChallengeListener = this)

        setupClickListener()

        return binding!!.root
    }

    fun setupClickListener() {
        //Challenge 추가 기능
        binding!!.textMyOwn.setOnClickListener {
            val intent = Intent(this.activity, SaveChallengeActivity::class.java)
            startActivity(intent)
        }
    }

    fun showChallenge(challengeList: ArrayList<ChallengeItem>) {
        var challengeAdapter = ChallengeAdapter(challengeList)
        challengeAdapter.notifyDataSetChanged()
        binding!!.challengeRecyclerView.adapter = challengeAdapter
        binding!!.challengeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //challenge 완료 버튼
        challengeAdapter.checkClickListener = object : ChallengeAdapter.OnCheckClickListener {
            override fun onCheckClick(check: Boolean, holder: ChallengeAdapter.ChallengeViewHolder, position: Int) {
                holder.check.setBackgroundResource(R.drawable.check_border)
                holder.check.setTextColor(Color.DKGRAY)
                val count = challengeList[position].count + 1
                holder.count.text = count.toString()
                challengeViewModel.saveState(count, challengeList[position].id, this@ChallengeFragment)
            }
        }

        //challenge 상세 화면 이동
        challengeAdapter.itemClickListener = object : ChallengeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                var item = challengeList[position]
                var intent = Intent(context, ChallengeInfoActivity::class.java)
                intent.putExtra("idx", item.id)
                startActivity(intent)
            }
        }

    }
    override fun onSuccessGetAllChallenge(List: JSONArray) {
        val challengeList = ArrayList<ChallengeItem>()
        for(i in 0 until List.length()) {
            var iObject = List.getJSONObject(i)
            var id = iObject.getString("id").toDouble().toInt().toString()
            var title = iObject.getString("title")
            var startTime = iObject.getString("startTime")
            var endTime = iObject.getString("endTime")
            var count = iObject.getLong("count")
            var totalCount = iObject.getLong("totalCount")
            var days = iObject.getJSONArray("days")
            var checked = iObject.getString("checked")
            challengeList.add(ChallengeItem(id, title, startTime, endTime, count, totalCount, days, checked))
        }
        showChallenge(challengeList)
    }

    override fun onFailureGetAllChallenge(message: String) {
        Log.d("getAllChallenge fail", message)
    }

    override fun onSuccessModifyState(message: String) {
        Log.d("modifyState success", message)
    }

    override fun onFailureModifyState(message: String) {
        Log.d("modifyState fail", message)
    }
}