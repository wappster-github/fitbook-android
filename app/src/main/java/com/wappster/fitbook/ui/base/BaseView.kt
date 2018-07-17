package com.wappster.fitbook.ui.base

import android.content.Context

/**
 * Base view any view must implement.
 */
interface BaseView{
    /**
     * Returns the context in which the application is running.
     * @return the context in which the application is running
     */
    fun getContext(): Context
}