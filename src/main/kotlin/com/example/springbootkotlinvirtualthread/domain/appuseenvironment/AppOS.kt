package com.example.springbootkotlinvirtualthread.domain.appuseenvironment

import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.HeaderInvalidException

enum class AppOS {

    AOS,
    IOS,
    ;

    companion object {

        fun getBy(appOs: String): AppOS =
            AppOS.entries.firstOrNull {
                it.name == appOs.uppercase()
            } ?: throw HeaderInvalidException(ErrorCode.APP_OS_HEADER_INVALID, ErrorCode.APP_OS_HEADER_INVALID.message.plus(" ($appOs)"))
    }
}
