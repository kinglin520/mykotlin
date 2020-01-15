package com.example.mykotlin.bean

import java.io.Serializable

class BaseBean<T> : Serializable {
    //接口返回的业务码
    private var status: Int = 0
    //接口返回信息
    private var message: String? = null
    //接口返回数据
    private var results: T? = null

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getData(): T? {
        return results
    }

    fun setResults(results: T) {
        this.results = results
    }
}