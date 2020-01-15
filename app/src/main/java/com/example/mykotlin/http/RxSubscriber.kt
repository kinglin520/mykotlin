package com.example.mykotlin.http

import android.text.TextUtils
import com.example.mykotlin.bean.BaseBean
import com.example.mykotlin.base.IBaseView
import com.example.mykotlin.http.config.RequestConfig
import com.example.mykotlin.http.error.ApiException
import com.example.mykotlin.http.error.ErrorCode
import com.example.mykotlin.http.error.ErrorType
import com.example.mykotlin.http.error.ExceptionConverter
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.Charset

abstract class RxSubscriber<R, T : BaseBean<R>> : DisposableObserver<R>() {

    /**
     * Http 返回码401监听，返回401需要退出登录
     */
    private var mGlobalErrorListener: GlobalErrorListener? = null

    private var mRequestConfig: RequestConfig<R, T>? = null
    /**
     * onNext方法的data，成功回调
     */
    private var mOnNextData: R? = null
    /**
     * 服务器错误码的data，向_onError()方法传递
     */
    private var mErrorData: R? = null
    /**
     * 缓存数据
     */
    private var cacheData: String? = null
    /**
     * JavaBean的Message字段的信息
     */
    private var mSuccessMessage: String? = null

    fun setRequestConfig(requestConfig: RequestConfig<R, T>) {
        this.mRequestConfig = requestConfig
    }

    /*************************************************************************************************************************/

    /**
     * 进行订阅请求网络
     *
     * @param observable
     */
    fun doSubscribe(observable: Observable<T>, view: IBaseView) {
        observable.flatMap { t ->
            mSuccessMessage = t.getMessage()
            mErrorData = t.getData()
            if (t.getStatus() === ErrorCode.CODE_SERVER_SUCCESS) {
                Observable.just(t.getData())
                //成功直接返回数据

            } else if (mRequestConfig != null &&
                !TextUtils.isEmpty(mRequestConfig!!.getAsSuccessCondition())
                && mRequestConfig!!.getAsSuccessCondition()!!.contains(t.getStatus().toString() + "")
            ) {
                //成功直接返回数据
                Observable.just(t.getData())

            } else {
                val mThrowable = Throwable("接口返回了错误业务码-----" + t.getStatus())

                throw ApiException(t.getStatus(), ErrorType.ERROR_API, t.getMessage(), mThrowable)
            }//返回非1000的错误码
        }.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(view.bindToLifecycle())
            .doOnSubscribe { view.showLoading() }
            .doFinally { view.hideLoading() }
            .onErrorResumeNext(
                Function<Throwable, ObservableSource<out R>> { throwable ->
                    if (throwable is HttpException) {
                        val body = throwable.response()?.errorBody()
                        if (body!!.contentLength() > 0) {
                            try {
                                val source = body.source()
                                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                                val buffer = source.buffer()
                                cacheData = buffer.clone().readString(Charset.forName("UTF-8"))
                            } catch (IOe: IOException) {
                                IOe.printStackTrace()
                            }
                        }
                    }
                    Observable.error(ExceptionConverter.convertException(throwable))
                })
            .subscribe(this)
    }

    override fun onNext(r: R) {

        mOnNextData = r
    }

    override fun onError(e: Throwable) {

        e.printStackTrace()

        doDispose()

        if (e is ApiException) {
            val exception = e
            //401重新登录
            if (exception.getCode() === ErrorCode.CODE_UNAUTHORIZED) {
                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener!!.onReturn401Code(this, exception.getMessageInfo())
                }

                _onError(
                    exception.getErrorType(),
                    exception.getCode(),
                    exception.getMessageInfo(),
                    cacheData
                )
            } else if (exception.getCode() === ErrorCode.CODE_SERVER_9105) {
                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener!!.onReturn9105Code(this, exception.getMessageInfo())
                }
                _onError(
                    exception.getErrorType(),
                    exception.getCode(),
                    exception.getMessageInfo(),
                    cacheData
                )
            } else if (exception.getCode() === ErrorCode.CODE_SERVER_9107) {
                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener!!.onReturn9107Code(this, exception.getMessageInfo())
                }
                _onError(exception.getErrorType(), exception.getCode(), "9107", cacheData)
            } else if (exception.getCode() === ErrorCode.CODE_SERVER_9108) {
                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener!!.onReturn9108Code(this, exception.getMessageInfo())
                }
                _onError(exception.getErrorType(), exception.getCode(), "9108", cacheData)
            } else if (exception.getCode() === ErrorCode.CODE_SERVER_9109) {
                //                LogUtils.e("LogOut---CODE_SERVER_9109");

                if (mGlobalErrorListener != null) {
                    mGlobalErrorListener!!.onReturn9109Code(this, exception.getMessageInfo())
                }
                _onError(
                    exception.getErrorType(),
                    exception.getCode(),
                    exception.getMessageInfo(),
                    cacheData
                )
            } else {  //正常错误回调

                //向错误回调传递data字段数据
                _onError(
                    exception.getErrorType(),
                    exception.getCode(),
                    exception.getMessageInfo(),
                    cacheData
                )
            }

        } else {
            _onError(ErrorType.ERROR_UNKNOWN, ErrorCode.CODE_UNKNOWN, "未知错误", cacheData)
        }
    }

    override fun onComplete() {

        doDispose()

        _onSuccess(mOnNextData, mSuccessMessage)

    }

    /**
     * 解除订阅关系
     */
    private fun doDispose() {
        dispose()
    }

    /******************************************关于重新登录的逻辑模块 */
    //401返回码监听listener,需要在Application的onCreate()方法中注册
    interface GlobalErrorListener {

        fun onReturn401Code(rxSubscriber: RxSubscriber<*, *>, message: String?)

        fun onReturn9105Code(rxSubscriber: RxSubscriber<*, *>, message: String?)

        fun onReturn9107Code(rxSubscriber: RxSubscriber<*, *>, message: String?)

        fun onReturn9108Code(rxSubscriber: RxSubscriber<*, *>, message: String?)

        fun onReturn9109Code(rxSubscriber: RxSubscriber<*, *>, message: String?)
    }

    fun registerGlobalErrorListener(listener: GlobalErrorListener) {
        mGlobalErrorListener = listener
    }


    protected abstract fun _onSuccess(r: R?, successMessage: String?)


    protected abstract fun _onError(
        errorType: ErrorType?,
        errorCode: Int,
        message: String?,
        data: String?
    )

}