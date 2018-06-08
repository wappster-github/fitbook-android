package com.wappster.fitbook.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wappster.fitbook.FitBookApplication
import com.wappster.fitbook.ui.login.LoginActivity
import com.wappster.fitbook.ui.tutorial.TutorialActivity
import com.wappster.fitbook.utils.Constants
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FitBookApplication.applicationComponent.inject(this)
        checkFirstLaunch()
    }

    private fun checkFirstLaunch() {
        if (sharedPreferences.getBoolean(Constants.IS_FIRST_LAUNCH, true)) {
            sharedPreferences.edit().putBoolean(Constants.IS_FIRST_LAUNCH, false).apply()
            startActivity(Intent(this, TutorialActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
