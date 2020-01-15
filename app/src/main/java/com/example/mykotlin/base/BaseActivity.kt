package com.example.mykotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity<V : IBaseView, T : BasePresenter<V>> : RxAppCompatActivity() {

    var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())
        mPresenter = initPresenter()
        initView()
        if (mPresenter != null) {
            lifecycle.addObserver(mPresenter!!)
        }
    }

    abstract fun getView():Int
    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    abstract fun initPresenter(): T

    abstract fun initView()

}