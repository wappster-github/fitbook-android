package com.wappster.fitbook.ui.login

import android.content.Intent
import android.os.Bundle
import com.wappster.fitbook.R
import com.wappster.fitbook.ui.base.BaseActivity
import com.wappster.fitbook.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setBackgroundDrawableResource(R.drawable.background_login)

        bindViews()
    }

    private fun bindViews() {
        signUpButton.setOnClickListener { _ -> startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))}
    }
}
