package com.example.springbootkotlinvirtualthread.exception

open class BadRequestException(code: ErrorCode, message: String?): ServiceRuntimeException(code, message)
