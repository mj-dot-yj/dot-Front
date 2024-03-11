package com.example.dot.presentation.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import com.example.dot.databinding.DialogCheckBinding

class ConfirmDialog(context: Context, message: String) : Dialog(context) {
    private val dialog = Dialog(context)
    private var message: String? = null

    private var mBinding: DialogCheckBinding? = null
    private val binding get() = mBinding!!

    init {
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DialogCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.contentText.text = message

        window!!.attributes.y = 500
        window!!.attributes.gravity = Gravity.TOP + Gravity.CENTER_HORIZONTAL
    }

    fun setOkPopup() {
        this.show()
        this.window!!.setLayout(800, 450)
        this.setCancelable(false)
    }
}