package com.example.mykotlin.http.config

class ApiConfig {
    /**
     * 服务器域名
     */
    private var mHostServer: String? = null

    /**
     * 读超时
     */
    private var mReadTimeOut: Int = 0
    /**
     * 连接超时
     */
    private var mConnectTimeOut: Int = 0

    fun getHostServer(): String? {
        return mHostServer
    }

    fun setHostServer(hostServer: String) {
        this.mHostServer = hostServer
    }


    fun getReadTimeOut(): Int {
        return mReadTimeOut
    }

    fun setReadTimeOut(readTimeOut: Int) {
        this.mReadTimeOut = readTimeOut
    }

    fun getConnectTimeOut(): Int {
        return mConnectTimeOut
    }

    fun setConnectTimeOut(writeTimeOut: Int) {
        this.mConnectTimeOut = writeTimeOut
    }

    override fun toString(): String {
        return "$mHostServer$mReadTimeOut$mConnectTimeOut"
    }
}