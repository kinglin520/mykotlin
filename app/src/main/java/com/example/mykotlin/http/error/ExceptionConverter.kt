package com.example.mykotlin.http.error

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionConverter {
    companion object {
        /**
         * 将非[ApiException]转换为该种异常
         * @param throwable
         * @return ApiException
         */
        fun convertException(throwable: Throwable): ApiException {

            val apiException: ApiException
            var errorMessage = ""
            var errorType: ErrorType? = null
            val errorCode: Int

            if (throwable is ApiException) {
                apiException = throwable
            } else if (throwable is HttpException) {

                val httpException = throwable as HttpException
                when (httpException.code()) {
                    ErrorCode.CODE_REQUEST_TIMEOUT -> {
                        errorMessage = "网络请求超时"
                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_REQUEST_TIMEOUT
                    }
                    ErrorCode.CODE_NOT_FOUND -> {
                        errorMessage = "抱歉!!您访问的页面不存在"
                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_NOT_FOUND
                    }
                    //Http码为401时需要用户重新登录，网关对用户Token进行了验证，防止多设备登录，多设备登陆时接口请求返回401码
                    ErrorCode.CODE_UNAUTHORIZED -> {
                        //401码需要获取服务器返回的数据
                        val mResponseBody = httpException.response()?.errorBody()
                        try {
                            errorMessage =
                                if (mResponseBody == null) "网络错误" else mResponseBody!!.string()

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_UNAUTHORIZED
                    }
                    ErrorCode.CODE_FORBIDDEN -> {
                        errorMessage = "网络错误"
                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_FORBIDDEN
                    }
                    ErrorCode.CODE_INTERNAL_SERVER_ERROR -> {
                        errorMessage = "服务器维护中，请稍后重试"
                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_INTERNAL_SERVER_ERROR
                    }
                    ErrorCode.CODE_BAD_GATEWAY -> {
                        errorMessage = "网络错误"
                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_BAD_GATEWAY
                    }
                    ErrorCode.CODE_SERVICE_UNAVAILABLE -> {
                        errorMessage = "网络错误"
                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_SERVICE_UNAVAILABLE
                    }
                    ErrorCode.CODE_GATEWAY_TIMEOUT -> {
                        errorMessage = "网络连接超时"
                        errorType = ErrorType.ERROR_HTTP
                        errorCode = ErrorCode.CODE_GATEWAY_TIMEOUT
                    }
                    else -> {
                        errorMessage = "未知网络错误"
                        errorType = ErrorType.ERROR_UNKNOWN_HTTP
                        errorCode = ErrorCode.CODE_UNKNOWN
                    }
                }

                apiException = ApiException(errorCode, errorType, errorMessage, throwable)

            } else if (throwable is JsonSyntaxException ||
                throwable is JsonParseException
                || throwable is JSONException
                || throwable is ParseException
            ) {

                errorMessage = "数据解析错误"
                errorType = ErrorType.ERROR_PARSE
                errorCode = ErrorCode.CODE_PARSE

                apiException = ApiException(errorCode, errorType, errorMessage, throwable)

            } else if (throwable is ConnectException) {
                errorMessage = "当前无网络，请检查网络"
                errorType = ErrorType.ERROR_NETWORK
                errorCode = ErrorCode.CODE_NETWORK

                apiException = ApiException(errorCode, errorType, errorMessage, throwable)

            } else if (throwable is UnknownHostException) {
                errorMessage = "似乎已断开与互联网的连接"
                errorType = ErrorType.ERROR_NETWORK
                errorCode = ErrorCode.CODE_NETWORK

                apiException = ApiException(errorCode, errorType, errorMessage, throwable)

            } else if (throwable is SocketTimeoutException) {
                errorMessage = "网络请求超时"
                errorType = ErrorType.ERROR_NETWORK
                errorCode = ErrorCode.CODE_NETWORK

                apiException = ApiException(errorCode, errorType, errorMessage, throwable)

            } else {
                errorMessage = "未知错误"
                errorType = ErrorType.ERROR_UNKNOWN
                errorCode = ErrorCode.CODE_UNKNOWN

                apiException = ApiException(errorCode, errorType, errorMessage, throwable)

            }

            return apiException
        }
    }
}