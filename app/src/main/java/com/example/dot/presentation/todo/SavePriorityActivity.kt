package com.example.dot.presentation.todo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.example.dot.databinding.ActivitySavePriorityBinding
import com.example.dot.presentation.HomeActivity

class SavePriorityActivity : AppCompatActivity() {
    private var mBinding: ActivitySavePriorityBinding? = null
    private val binding get() = mBinding!!
    private lateinit var priority: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySavePriorityBinding.inflate(layoutInflater)

        setupTouchListener()

        setupClickListener()

        setContentView(binding.root)
    }

    private fun setCheckInvisibility() {
        binding!!.checkImportantUrgent.visibility = View.INVISIBLE
        binding!!.checkImportantNoturgent.visibility = View.INVISIBLE
        binding!!.checkNotimportantNoturgent.visibility = View.INVISIBLE
        binding!!.checkNotimportantUrgent.visibility = View.INVISIBLE
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTouchListener() {

        binding!!.importantUrgent.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    setCheckInvisibility()
                    binding!!.checkImportantUrgent.visibility = View.VISIBLE
                    binding!!.nextButton.visibility = View.VISIBLE
                    priority = "IMPORTANT_URGENT"
                }
            }
            true
        }

        binding!!.notimportantUrgent.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    setCheckInvisibility()
                    binding!!.checkNotimportantUrgent.visibility = View.VISIBLE
                    binding!!.nextButton.visibility = View.VISIBLE
                    priority = "NOTIMPORTANT_URGENT"
                }
            }
            true
        }

        binding!!.importantNoturgent.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    setCheckInvisibility()
                    binding!!.checkImportantNoturgent.visibility = View.VISIBLE
                    binding!!.nextButton.visibility = View.VISIBLE
                    priority = "IMPORTANT_NOTURGENT"
                }
            }
            true
        }

        binding!!.notimportantNoturgent.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    setCheckInvisibility()
                    binding!!.checkNotimportantNoturgent.visibility = View.VISIBLE
                    binding!!.nextButton.visibility = View.VISIBLE
                    priority = "NOTIMPORTANT_NOTURGENT"
                }
            }
            true
        }
    }

    private fun setupClickListener() {

        binding!!.backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding!!.nextButton.setOnClickListener {
            val intent = Intent(this, SaveTodoActivity::class.java)
            val date = getIntent().getStringExtra("date")
            intent.putExtra("date", date)
            intent.putExtra("priority", priority)
            startActivity(intent)
        }
    }
}