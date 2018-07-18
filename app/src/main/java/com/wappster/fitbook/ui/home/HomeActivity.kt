package com.wappster.fitbook.ui.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.wappster.fitbook.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }
    }
}
