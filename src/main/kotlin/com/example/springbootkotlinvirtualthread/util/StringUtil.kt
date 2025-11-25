package com.example.springbootkotlinvirtualthread.util

import java.util.*

class StringUtil private constructor() {

    companion object {

        private const val DEFAULT_DATE_STRING_FORMAT = "%4d-%02d-%02d"
        private const val DEFAULT_DATE_INTEGER_FORMAT = "%4d%02d%02d"
        private const val DEFAULT_TIME_INTEGER_FORMAT = "%02d%02d"

        fun defaultDateStringFormat(year: Int, month: Int, day: Int): String =
            String.format(DEFAULT_DATE_STRING_FORMAT, year, month, day)

        fun defaultDateIntegerFormat(year: Int, month: Int, day: Int): String =
            String.format(DEFAULT_DATE_INTEGER_FORMAT, year, month, day)

        fun defaultTimeIntegerFormat(hour: Int, minute: Int): String =
            String.format(DEFAULT_TIME_INTEGER_FORMAT, hour, minute)

        fun encodeBase64(value: String): String {
            return Base64.getEncoder().withoutPadding().encodeToString(value.toByteArray())
        }

        fun decodeBase64(value: String): String {
            return String(Base64.getDecoder().decode(value))
        }
    }
}
