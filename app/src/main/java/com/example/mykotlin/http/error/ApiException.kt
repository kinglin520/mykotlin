package com.example.mykotlin.http.error

class ApiException : Exception {
    /**
     * 错误码
     */
    private var code: Int = 0
    /**
     * 错误类型
     */
    private var errorType: ErrorType? = null

    constructor(code: Int, errorType: ErrorType, message: String?, cause: Throwable) : super(
        message,
        cause
    ) {
        this.code = code
        this.errorType = errorType
    }


    constructor(message: String?, cause: Throwable) : super(message, cause)

    /**
     * 获取错误码
     * @return
     */
    fun getCode(): Int {
        return code
    }

    fun getErrorType(): ErrorType? {
        return errorType
    }

    fun getMessageInfo(): String? = super.message

}