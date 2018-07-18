package com.wappster.fitbook.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp
import com.wappster.fitbook.BuildConfig
import com.wappster.fitbook.R
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class FitBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initFacebook()
        initFirebase()
        initFabric()
        initCalligraphy()
        initTimber()
    }

    private fun initFacebook() {
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this);
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initFabric() {
        Fabric.with(this, Crashlytics(), Answers())
    }

    private fun initCalligraphy() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Quicksand-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}