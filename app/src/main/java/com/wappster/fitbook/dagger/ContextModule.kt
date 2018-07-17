package com.wappster.fitbook.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.wappster.fitbook.ui.base.BaseView
import dagger.Module
import dagger.Provides

/**
 * Module which provides all required dependencies about Context
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object ContextModule {
    /**
     * Provides the Context
     * @param baseView the BaseView used to provides the context
     * @return the Context to be provided
     */
    @Provides
    @JvmStatic
    internal fun provideContext(baseView: BaseView): Context {
        return baseView.getContext()
    }

    /**
     * Provides the Application Context
     * @param context Context in which the application is running
     * @return the Application Context to be provided
     */
    @Provides
    @JvmStatic
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }


    /**
     * Provides the Application Context
     * @param context Context in which the application is running
     * @return the SharedPreferences Instance
     */
    @Provides
    @JvmStatic
    internal fun provideSharedPrefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}