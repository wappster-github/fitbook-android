package com.wappster.fitbook.ui.register

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.wappster.fitbook.R
import com.wappster.fitbook.models.User
import com.wappster.fitbook.ui.base.BaseActivity
import com.wappster.fitbook.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setBackgroundDrawableResource(R.drawable.background_login)

        presenter.onViewCreated()
        initializeViews()
        initializeClickListeners()
    }


    override fun instantiatePresenter(): RegisterPresenter {
        return RegisterPresenter(this)
    }

    private fun initializeViews() {
        signInButton.paintFlags = signInButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initializeClickListeners() {
        signInButton.setOnClickListener { finish() }
        signUpButton.setOnClickListener { presenter.onRegisterClicked(emailEditText.text.toString(), passwordEditText.text.toString()) }
    }

    override fun updateUser(user: User) {
        Log.d("RegisterActivity", user.email)
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun showError(error: String) {
        Log.d("RegisterActivity", error)
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

}
