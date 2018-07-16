package com.wappster.fitbook.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.wappster.fitbook.BuildConfig
import com.wappster.fitbook.R
import com.wappster.fitbook.dagger.*
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class FitBookApplication : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent

        lateinit var instance: FitBookApplication

        fun checkIfHasNetwork(): Boolean {
            val cm = instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    override fun onCreate() {
        super.onCreate()
        initDi()
        initCalligraphy()
        initTimber()
    }

    private fun initCalligraphy() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Quicksand-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }

    fun initDi() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .cacheModule(CacheModule())
                .interceptorModule(InterceptorModule())
                .apiModule(ApiModule())
                .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}