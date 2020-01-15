package com.example.mykotlin.ui

import com.example.mykotlin.bean.BaseBean
import com.example.mykotlin.bean.GirlInfoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GankService {
    @GET("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/{page}")
    fun getGirlInfo(@Path("page") page: String): Observable<BaseBean<List<GirlInfoBean>>>
}