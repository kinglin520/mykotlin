package com.example.mykotlin.base

import com.example.mykotlin.http.error.ErrorType
import com.trello.rxlifecycle2.LifecycleTransformer

interface IBaseView {

    fun <T> bindToLifecycle(): LifecycleTransformer<T>

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    fun showErrorTip(errorType: ErrorType?, errorCode: Int, message: String?)

}