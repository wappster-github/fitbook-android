package com.wappster.fitbook.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.wappster.fitbook.R
import com.wappster.fitbook.models.User
import com.wappster.fitbook.ui.base.BaseActivity
import com.wappster.fitbook.ui.home.HomeActivity
import com.wappster.fitbook.utils.Constants.Companion.RC_SIGN_IN
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setBackgroundDrawableResource(R.drawable.background_login)

        presenter.onViewCreated()
        initializeClickListeners()
    }

    private fun initializeClickListeners() {
        signInButton.setOnClickListener{ presenter.onLoginClicked(emailEditText.text.toString(), passwordEditText.text.toString())}
        signUpButton.setOnClickListener { presenter.onRegisterClicked()}
        googleSignInButton.setOnClickListener { presenter.onGoogleSignInClicked() }
        facebookSignInButton.setOnClickListener { presenter.onFacebookSignInClicked() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun updateUser(user: User) {
        Log.d("LoginActivity", user.email)
        startActivity(Intent(this, HomeActivity::class.java))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }
}
