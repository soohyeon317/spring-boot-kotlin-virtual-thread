package com.example.springbootkotlinvirtualthread.exception

open class UnAuthorizedException : ServiceRuntimeException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
