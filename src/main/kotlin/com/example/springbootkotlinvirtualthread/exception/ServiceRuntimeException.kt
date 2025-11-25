package com.example.springbootkotlinvirtualthread.exception

open class ServiceRuntimeException(open var code: ErrorCode, override var message: String?) : RuntimeException()
