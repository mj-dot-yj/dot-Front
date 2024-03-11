package com.example.dot.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.dot.R
import com.example.dot.databinding.ActivityHomeBinding
import com.example.dot.presentation.fragment.ChallengeFragment
import com.example.dot.presentation.fragment.MypageFragment
import com.example.dot.presentation.fragment.TodoFragment


private const val TAG_TODO = "todo_fragment"
private const val TAG_CHALLENGE = "challenge_fragment"
private const val TAG_MYPAGE = "mypage_fragment"

class HomeActivity : AppCompatActivity() {

    private var mbinding: ActivityHomeBinding? = null
    private val binding get() = mbinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mbinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_TODO, TodoFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.challengeFragment -> setFragment(TAG_CHALLENGE, ChallengeFragment())
                R.id.mypageFragment -> setFragment(TAG_MYPAGE, MypageFragment())
                R.id.todoFragment -> setFragment(TAG_TODO, TodoFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager : FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if(manager.findFragmentByTag(tag) == null) {
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val todo = manager.findFragmentByTag(TAG_TODO)
        val challenge = manager.findFragmentByTag(TAG_CHALLENGE)
        val mypage = manager.findFragmentByTag(TAG_MYPAGE)

        if(todo != null) {
            fragTransaction.hide(todo)
        }
        if(challenge != null) {
            fragTransaction.hide(challenge)
        }
        if(mypage != null) {
            fragTransaction.hide(mypage)
        }

        if(tag == TAG_TODO) {
            if(todo != null) {
                fragTransaction.show(todo)
            }
        }
        else if(tag == TAG_CHALLENGE) {
            if(challenge != null) {
                fragTransaction.show(challenge)
            }
        }
        else if(tag == TAG_MYPAGE) {
            if(mypage != null) {
                fragTransaction.show(mypage)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}