package com.example.springbootkotlinvirtualthread.domain.account

import com.vespexx.signal.serviceapi.domain.account.LanguageCode

enum class LocaleInfoDefault(val default: String) {

    LANGUAGE_CODE(default = LanguageCode.ENGLISH.code),
    COUNTRY_CODE(default = "UNKNOWN"),
    TIME_ZONE_CODE(default = "UTC")
}