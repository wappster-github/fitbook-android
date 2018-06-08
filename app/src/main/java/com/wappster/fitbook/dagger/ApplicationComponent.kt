package com.wappster.fitbook.dagger

import com.wappster.fitbook.ui.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, ApiModule::class, CacheModule::class, InterceptorModule::class])
interface ApplicationComponent {

    fun inject(splashActivity : SplashActivity)

}