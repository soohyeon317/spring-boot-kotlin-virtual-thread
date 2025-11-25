package com.example.springbootkotlinvirtualthread.exception

class DistributedLockAcquisitionFailureException : InternalServerException {
    constructor(code: ErrorCode, message: String?) : super(code, message)
    constructor(code: ErrorCode) : this(code, code.message)
}
