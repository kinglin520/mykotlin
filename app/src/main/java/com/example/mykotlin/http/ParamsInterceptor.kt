package com.example.mykotlin.http

import android.text.TextUtils
import android.util.Log
import com.blankj.utilcode.util.StringUtils
import com.example.mykotlin.BaseApplication
import com.example.mykotlin.cache.CacheInject
import com.example.mykotlin.utils.SharedPreferencesUtil
import okhttp3.*
import okhttp3.internal.Util
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

class ParamsInterceptor : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    val CHANNEL_ID = "1"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()

        val method = originalRequest.url().toString()

        //组装参数
        val bodyStr = bodyToString(originalRequest.body())

        val requestBuilder = originalRequest.newBuilder()

        val stringBuilder = StringBuilder()

        requestBuilder.addHeader("Content-Type", "application/json;charset=UTF-8")
            .addHeader("Authorization", stringBuilder.toString())  // token

        if ("POST" == originalRequest.method()) {
            requestBuilder.post(
                RequestBody.create(
                    MediaType.parse("application/json;charset=UTF-8"),
                    bodyStr
                )
            )
        }

        originalRequest = requestBuilder.build()

        var response: Response? = null

        val contentType: MediaType?

        try {
            response = chain.proceed(originalRequest)
        } catch (e: Exception) {
//             创建response 放入缓存数据
            val newResponse = Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(504)
                .message("Unsatisfiable Request (only-if-cached)")
                .body(Util.EMPTY_RESPONSE)
                .sentRequestAtMillis(-1L)
                .receivedResponseAtMillis(System.currentTimeMillis())
                .build()
            contentType = newResponse.body()!!.contentType()
//             取出缓存
            val result = SharedPreferencesUtil.getString(
                BaseApplication.getApplication()!!,
                method + bodyStr,
                ""
            )
            if (!StringUtils.isEmpty(result)) {
                return if (CacheInject.map.containsKey(method) && CacheInject.map.get(method) as Boolean) {
                    newResponse.newBuilder()
                        .body(ResponseBody.create(contentType, result))
                        .build()
                } else {
                    newResponse.newBuilder().code(200)
                        .body(ResponseBody.create(contentType, result))
                        .build()
                }
            }
            throw e
        }

        Log.e("APi请求URL:", method)
        Log.e("headers参数:", originalRequest.headers().toString())
        Log.e("body参数:", bodyStr)

        val code = response?.code()

        val responseBody = response?.body()

        contentType = responseBody?.contentType()
        if (code == 200) {
            try { // 日志保存

                val contentLength = responseBody?.contentLength()
                var result = ""
                if (contentLength != 0L) {
                    var charset: Charset? = UTF8
                    if (contentType != null) {
                        charset = contentType.charset(UTF8)
                    }
                    val source = responseBody?.source()
                    source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                    val buffer = source?.buffer()
                    result = buffer?.clone()!!.readString(charset!!)
                }
                if (!TextUtils.isEmpty(result)) {
//                    if (CacheInject.map.containsKey(method)) {
//                        BaseApplication.getApplication()?.let {
//                            SharedPreferencesUtil.putString(
//                                it,
//                                method + bodyStr,
//                                result
//                            )
//                        }
//                    }
//                    val isSuccess = result.contains("SUCCESS")
////                    Map<String, String> map = new HashMap<>();
//////                    map.put("date", TimeUtils.getNowTimeString());
////                    map.put("type", method);
////                    map.put("url", method);
////                    map.put("flag", isSuccess || result.contains("成功") ? "1" : "0");
////                    map.put("request", originalRequest.headers().toString());
////                    map.put("response", result);
////                    new ApiLogManager().saveLog(map);
//                    if (isSuccess) {
//                        DBManager.putCache(method, bodyStr, result)//存入缓存
//                    } else {
//                        result = DBManager.getCache(method, bodyStr)//取出缓存
//                        if (!StringUtils.isEmpty(result)) {
//                            return response.newBuilder()
//                                .body(ResponseBody.create(contentType, result))
//                                .build()
//                        }
//                    }

                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        return response!!
    }

    /**
     * 设置requestBody的编码格式为json
     */
    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }
}