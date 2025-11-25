package com.example.springbootkotlinvirtualthread.exception

open class AccessForbiddenException : ServiceRuntimeException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
