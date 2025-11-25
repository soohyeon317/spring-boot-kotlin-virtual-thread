package com.example.springbootkotlinvirtualthread.exception

open class ResourceNotFoundException(code: ErrorCode, message: String?): ServiceRuntimeException(code, message)
