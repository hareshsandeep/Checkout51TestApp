package com.checkout51.testapp.ui.base

open class BaseMvpPresenterImpl<V : BaseView> : BasePresenter<V> {

    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}