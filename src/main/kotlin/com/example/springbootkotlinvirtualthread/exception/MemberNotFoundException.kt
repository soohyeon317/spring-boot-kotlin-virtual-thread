package com.example.springbootkotlinvirtualthread.exception

class MemberNotFoundException : ResourceNotFoundException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
