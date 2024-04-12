package com.example.dot.presentation.todo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.example.dot.databinding.ActivityAddPriorityBinding
import com.example.dot.presentation.HomeActivity
import com.example.dot.presentation.MainActivity
import java.util.zip.Inflater

class AddPriorityActivity : AppCompatActivity() {
    private var mBinding: ActivityAddPriorityBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddPriorityBinding.inflate(layoutInflater)

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
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
    }
}