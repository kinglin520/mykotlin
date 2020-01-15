package com.example.mykotlin.base

import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

abstract class BasePresenter<V : IBaseView> : IBasePresenter {

    var mView: WeakReference<V>? = null

    constructor(mView: V) {
        this.mView = WeakReference(mView)
    }

    fun getView(): V? = this.mView?.get()


    override fun onDestroy(owner: LifecycleOwner) {
        this.mView?.clear()
        this.mView = null
        owner.lifecycle.removeObserver(this)
    }
}