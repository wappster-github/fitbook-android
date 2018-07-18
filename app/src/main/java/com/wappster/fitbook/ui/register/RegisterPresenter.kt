package com.wappster.fitbook.ui.register

import com.wappster.fitbook.network.FitBookApiService
import com.wappster.fitbook.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterPresenter(registerView: RegisterView) : BasePresenter<RegisterView>(registerView) {

    @Inject
    lateinit var apiService: FitBookApiService

    private var subscription: Disposable? = null

    fun onRegisterClicked(email: String, password: String) {
        view.showLoading()
        subscription = apiService
                .register(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { response -> view.updateUser(response.data) },
                        { t -> view.showError(t.localizedMessage) }
                )
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}