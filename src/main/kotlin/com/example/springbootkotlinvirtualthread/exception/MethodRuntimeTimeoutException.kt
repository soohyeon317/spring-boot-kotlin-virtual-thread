package com.example.springbootkotlinvirtualthread.exception

class MethodRuntimeTimeoutException : InternalServerException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
