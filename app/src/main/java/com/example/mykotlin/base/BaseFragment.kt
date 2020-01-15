package com.example.mykotlin.base

import android.os.Bundle
import android.view.View
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment<V : IBaseView, T : BasePresenter<V>> : RxFragment() {
    var mPresenter: T? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = initPresenter()
        initView()
        if (mPresenter != null) {
            lifecycle.addObserver(mPresenter!!)
        }
    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    protected abstract fun initPresenter(): T

    protected abstract fun initView()

}