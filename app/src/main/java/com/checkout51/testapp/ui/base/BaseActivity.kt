package com.checkout51.testapp.ui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<in V : BaseView, T : BasePresenter<V>>
    : AppCompatActivity(), BaseView {

    protected abstract var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}