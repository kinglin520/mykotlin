package com.example.mykotlin.http

import com.blankj.utilcode.util.StringUtils
import com.example.mykotlin.bean.BaseBean
import com.example.mykotlin.http.config.ApiConfig
import com.example.mykotlin.http.config.RequestConfig
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class Api {

    /**
     * 钱包网络请求添加认证头
     */
    val TYPE_HEADER = 1

    private val mRetrofitList = ArrayList<Retrofit>()

    private var mRetrofit: Retrofit? = null

    /*************************缓存设置*********************/
/*
   1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    val CACHE_STALE_SEC = (60 * 60 * 24 * 2).toLong()

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=$CACHE_STALE_SEC"
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private val CACHE_CONTROL_AGE = "max-age=0"

    init {
//        LogUtils.e("HostServer---API" + mApiConfig?.toString())
        val url = mApiConfig?.getHostServer()

        if (StringUtils.isEmpty(url)) {
            throw  Exception("please config server host address!")
        }

        mRetrofit = Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .build()
    }

    companion object {
        private var mApiConfig: ApiConfig? = null
        val instance: Api by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Api()
        }

        fun <T> getService(clazz: Class<T>): T? {
            return instance.mRetrofit?.create(clazz)
        }

        fun <R, T : BaseBean<R>> observable(observable: Observable<T>): RequestConfig<R, T> {
            val mRequestConfig = RequestConfig<R, T>()
            mRequestConfig.observable(observable)
            return mRequestConfig
        }

        /**
         * 设置Api的配置类，该方法请在Application中调用
         *
         * @param config
         */
        fun setConfig(config: ApiConfig) {
            mApiConfig = config
//            LogUtils.e("HostServer---set" + config.toString())
        }


    }

    fun getOkHttpClient(): OkHttpClient {
        //Log拦截器
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //网络缓存文件夹
//        val cacheFile = File(BaseApplication.getApplication().getCacheDir(), "cache")
        //100Mb;
//        val cache = Cache(cacheFile, (1024 * 1024 * 100).toLong())

        return OkHttpClient.Builder()
            .readTimeout(mApiConfig?.getReadTimeOut()!!.toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout(mApiConfig?.getConnectTimeOut()!!.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(ParamsInterceptor())
//            .cache(cache)
            .build()
    }

//    private fun getRetrofit(): Retrofit? {
//        if (mRetrofit == null) {
//            synchronized(Api::class.java) {
//                if (mRetrofit == null) {
//                    LogUtils.e("HostServer---API" + mApiConfig?.toString())
//                    val url = mApiConfig?.getHostServer()
//                    mRetrofit = Retrofit.Builder()
//                        .client(getOkHttpClient())
//                        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                        .baseUrl(url)
//                        .build()
//                }
//            }
//        }
//        return mRetrofit
//    }

//    fun observable2(observable: Observable<*>): RequestString {
//        val mRequestConfig = RequestString()
//        mRequestConfig.observable(observable)
//        return mRequestConfig
//    }


}