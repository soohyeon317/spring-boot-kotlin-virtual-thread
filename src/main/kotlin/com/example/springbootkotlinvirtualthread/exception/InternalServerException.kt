package com.example.springbootkotlinvirtualthread.exception

open class InternalServerException(code: ErrorCode, message: String?): ServiceRuntimeException(code, message)
