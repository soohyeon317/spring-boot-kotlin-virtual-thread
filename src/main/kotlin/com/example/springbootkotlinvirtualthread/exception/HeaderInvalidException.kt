package com.example.springbootkotlinvirtualthread.exception

class HeaderInvalidException : BadRequestException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
