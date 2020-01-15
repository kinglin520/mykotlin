package com.example.mykotlin.http.config

import com.example.mykotlin.bean.BaseBean
import com.example.mykotlin.base.BasePresenter
import com.example.mykotlin.base.IBaseView
import com.example.mykotlin.http.RxSubscriber
import io.reactivex.Observable

class RequestConfig<R, T : BaseBean<R>> {

    private var mTag: String? = null
    private var mPresenter: BasePresenter<IBaseView>? = null
    private var mAsSuccessCondition: String? = null
    private var mObservable: Observable<T>? = null
    private var iBaseView: IBaseView? = null


    fun observable(observable: Observable<T>): RequestConfig<R, T> {
        this.mObservable = observable
        return this
    }

    /**
     * 网络请求Tag，用于打印日志，方便追踪调试。
     * 描述：可不配置
     *
     * @param tag
     * @return
     */
    fun tag(tag: String): RequestConfig<R, T> {
        this.mTag = tag
        return this
    }

    fun presenter(presenter: BasePresenter<IBaseView>): RequestConfig<R, T> {
        this.mPresenter = presenter
        return this
    }

    /**
     * @param condition 当服务器返回的code不为1000时，框架认为接口请求错误最终回调错误方法，此方法可以配置条件，将非1000的服务器返回码视作成功
     * 参数规范示例  {"1001,1002,1003"}  即将code值用","拼接在一起
     * @return
     */
    fun asSuccessWhen(condition: String): RequestConfig<R, T> {
        this.mAsSuccessCondition = condition
        return this
    }

    /**
     * 服务绑定界面生命周期
     */
    fun life(view: IBaseView): RequestConfig<R, T> {
        iBaseView = view
        return this
    }

    fun doRequest(rxSubscriber: RxSubscriber<R, T>) {

        rxSubscriber.setRequestConfig(this)
        mObservable?.let { iBaseView?.let { it1 -> rxSubscriber.doSubscribe(it, it1) } }
    }


    /*****************************************************以下是属性的get方法 */
    fun getTag(): String? {
        return mTag
    }

    fun getPresenter(): BasePresenter<IBaseView>? {
        return mPresenter
    }

    fun getAsSuccessCondition(): String? {
        return mAsSuccessCondition
    }

    fun getObservable(): Observable<T>? {
        return mObservable
    }
}