package com.example.mykotlin.ui

import com.example.mykotlin.bean.BaseBean
import com.example.mykotlin.base.BasePresenter
import com.example.mykotlin.bean.GirlInfoBean
import com.example.mykotlin.http.Api
import com.example.mykotlin.http.RxSubscriber
import com.example.mykotlin.http.error.ErrorType

class TestPresenter : BasePresenter<TestView> {

    constructor(mView: TestView) : super(mView)

    fun getGirlInfo(page: String) {
        Api.observable(Api.getService(GankService::class.java)!!.getGirlInfo(page))
            .asSuccessWhen("1002")
            .life(getView()!!)
            .doRequest(object : RxSubscriber<List<GirlInfoBean>, BaseBean<List<GirlInfoBean>>>() {
                override fun _onSuccess(r: List<GirlInfoBean>?, successMessage: String?) {
                    r?.let { getView()?.setGirlInfo(it) }

                }

                override fun _onError(
                    errorType: ErrorType?,
                    errorCode: Int,
                    message: String?,
                    data: String?
                ) {
                    getView()?.showErrorTip(errorType, errorCode, message)

                }

            })
    }

}