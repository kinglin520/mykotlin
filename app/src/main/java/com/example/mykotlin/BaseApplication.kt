package com.example.mykotlin

import android.app.Application
import com.example.mykotlin.http.Api
import com.example.mykotlin.http.config.ApiConfig

class BaseApplication : Application() {
    companion object {
        var _context: Application? = null
        fun getApplication(): Application? {
            return _context

        }
    }


    override fun onCreate() {
        super.onCreate()
        _context = this
        initApiConfig()
    }

    private fun initApiConfig() {
        val apiConfig = ApiConfig()
        apiConfig.setConnectTimeOut(30000)
        apiConfig.setReadTimeOut(30000)
        apiConfig.setHostServer("https://www.baidu.com")
        Api.setConfig(apiConfig)
    }
}