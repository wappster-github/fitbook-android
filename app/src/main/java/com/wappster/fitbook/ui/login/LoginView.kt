package com.wappster.fitbook.ui.login

import android.support.annotation.StringRes
import com.wappster.fitbook.models.User
import com.wappster.fitbook.ui.base.BaseView

/**
 * Interface providing required method for a view to login
 */
interface LoginView : BaseView {
    /**
     * Update the logged user
     * @param user the user that logged in
     */
    fun updateUser(user: User)

    /**
     * Displays an error in the view
     * @param error the error to display in the view
     */
    fun showError(error: String)

    /**
     * Displays an error in the view
     * @param errorResId the resource id of the error to display in the view
     */
    fun showError(@StringRes errorResId: Int){
        this.showError(getContext().getString(errorResId))
    }

    /**
     * Displays the loading indicator of the view
     */
    fun showLoading()

    /**
     * Hides the loading indicator of the view
     */
    fun hideLoading()
}