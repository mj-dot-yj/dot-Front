package com.example.dot.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.example.dot.R

class ConfirmDialog(context: Context, message: String) : Dialog(context) {
    private val dialog = Dialog(context)
    private var message: String? = null

    init {
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_check)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val contextText = findViewById<TextView>(R.id.contentText)
        contextText.text = message

        window!!.attributes.y = 500
        window!!.attributes.gravity = Gravity.TOP + Gravity.CENTER_HORIZONTAL
    }
}