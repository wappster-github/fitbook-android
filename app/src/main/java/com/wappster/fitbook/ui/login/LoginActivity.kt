package com.wappster.fitbook.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.wappster.fitbook.R
import com.wappster.fitbook.models.User
import com.wappster.fitbook.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setBackgroundDrawableResource(R.drawable.background_login)

        presenter.onViewCreated()
        bindViews()
    }

    private fun bindViews() {
        signInButton.setOnClickListener{ _ -> presenter.onLoginClicked(emailEditText.text.toString(), passwordEditText.text.toString())}
        signUpButton.setOnClickListener { _ -> presenter.onRegisterClicked()}
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun updateUser(user: User) {
        Log.d("LoginActivity", user.email)
    }

    override fun showError(error: String) {
        Log.d("LoginActivity", error)
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun instantiatePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }
}
