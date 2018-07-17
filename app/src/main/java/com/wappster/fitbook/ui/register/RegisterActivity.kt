package com.wappster.fitbook.ui.register

import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wappster.fitbook.R
import com.wappster.fitbook.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setBackgroundDrawableResource(R.drawable.background_login)

        initializeViews()
        initializeClickListeners()
    }

    private fun initializeViews() {
        signInButton.paintFlags = signInButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initializeClickListeners() {
        signInButton.setOnClickListener { _ -> finish() }
    }
}
