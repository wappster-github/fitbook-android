package com.wappster.fitbook.ui.login

import android.content.Intent
import com.wappster.fitbook.network.FitBookApiService
import com.wappster.fitbook.ui.base.BasePresenter
import com.wappster.fitbook.ui.register.RegisterActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter(loginView: LoginView) : BasePresenter<LoginView>(loginView) {

    @Inject
    lateinit var apiService: FitBookApiService

    private var subscription: Disposable? = null

    override fun onViewCreated() {}

    fun onLoginClicked(email : String, password : String) {
        view.showLoading()
        subscription = apiService
                .login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { response -> view.updateUser(response.data) },
                        { t -> view.showError(t.localizedMessage) }
                )
    }

    fun onRegisterClicked() {
        view.getContext().startActivity(Intent(view.getContext(), RegisterActivity::class.java))
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}