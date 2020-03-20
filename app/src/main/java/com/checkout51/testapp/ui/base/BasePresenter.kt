package com.checkout51.testapp.ui.base

interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}