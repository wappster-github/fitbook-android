package com.wappster.fitbook.dagger

import com.wappster.fitbook.ui.base.BaseView
import com.wappster.fitbook.ui.login.LoginPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
* Component providing inject() methods for presenters.
*/
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class), (InterceptorModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param loginPresenter LoginPresenter in which to inject the dependencies
     */
    fun inject(loginPresenter: LoginPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        fun interceptorModule(interceptorModule: InterceptorModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}