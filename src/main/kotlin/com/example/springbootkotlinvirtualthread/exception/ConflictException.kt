package com.example.springbootkotlinvirtualthread.exception

open class ConflictException(code: ErrorCode, message: String?): ServiceRuntimeException(code, message)
