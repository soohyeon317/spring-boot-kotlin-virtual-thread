package com.example.springbootkotlinvirtualthread.util

import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.InputInvalidException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

const val ZONE_ID_OF_UTC = "UTC"
const val DEFAULT_DATE_DELIMITER = "-"
const val DEFAULT_TIME_DELIMITER = ":"
const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val DEFAULT_UTC_DATE_TIME_FORMAT = "YYYY-MM-dd'T'HH:mm:ss'Z'"
const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
const val DEFAULT_HOUR_MINUTE_TIME_FORMAT = "HH:mm"

val DEFAULT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)

fun LocalDateTime.serializedLocalDateTime(
    pattern: String? = DEFAULT_DATE_TIME_FORMAT,
    oldZoneId: ZoneId? = ZoneId.systemDefault(),
    newZoneId: ZoneId? = ZoneId.systemDefault()
): String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern!!)
    val zdt = this.atZone(oldZoneId).withZoneSameInstant(newZoneId)
    return zdt.format(formatter)
}

fun LocalDateTime.toLocalizedDateTime(
    pattern: String? = DEFAULT_DATE_TIME_FORMAT,
    oldZoneId: ZoneId? = ZoneId.systemDefault(),
    newZoneId: ZoneId? = ZoneId.systemDefault()
): LocalDateTime {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern!!)
    val zdt = this.atZone(oldZoneId).withZoneSameInstant(newZoneId)
    return zdt.format(formatter).parseLocalDateTime()
}

fun LocalDateTime.serializedUTCDateTime(pattern: String? = DEFAULT_DATE_TIME_FORMAT): String =
    this.serializedLocalDateTime(pattern = pattern, newZoneId = ZoneId.of(ZONE_ID_OF_UTC))

fun String.parseLocalDateTime(
    pattern: String? = DEFAULT_DATE_TIME_FORMAT
): LocalDateTime =
    runCatching {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern!!)
        LocalDateTime.parse(this, formatter)
    }.onFailure {
        throw InputInvalidException(ErrorCode.INPUT_INVALID, it.message)
    }.getOrThrow()

fun String.toDateFormattedOutput(inputFormat: String, outputFormat: String): String = runCatching {
    val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
    val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
    val date = inputFormatter.parse(this)
    outputFormatter.format(date)
}.onFailure {
    throw InputInvalidException(ErrorCode.INPUT_INVALID, it.message)
}.getOrThrow()

fun LocalDateTime.isEqualOrBefore(target: LocalDateTime) = this.isEqual(target) || this.isBefore(target)

fun LocalDateTime.isEqualOrAfter(target: LocalDateTime) = this.isEqual(target) || this.isAfter(target)

fun Long.toLocalizedDateTime(
    zoneId: ZoneId ?= ZoneOffset.systemDefault()
): LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochMilli(this),
    zoneId
)

fun String.parseLocalDate(pattern: String? = null): LocalDate =
    runCatching {
        val formatter: DateTimeFormatter = if (pattern != null) {
            DateTimeFormatter.ofPattern(pattern)
        } else {
            DEFAULT_DATE_FORMATTER
        }
        LocalDate.parse(this, formatter)
    }.onFailure {
        throw InputInvalidException(ErrorCode.INPUT_INVALID, it.message)
    }.getOrThrow()

fun LocalDate.serializedDate(pattern: String): String =
    this.format(DateTimeFormatter.ofPattern(pattern))

fun LocalDate.isEqualOrAfter(target: LocalDate) = this.isEqual(target) || this.isAfter(target)

fun LocalDate.isEqualOrBefore(target: LocalDate) = this.isEqual(target) || this.isBefore(target)

fun LocalDate.isBetween(startDate: LocalDate, endDate: LocalDate) = (this.isEqual(startDate) || this.isAfter(startDate)) && (this.isEqual(endDate) || this.isBefore(endDate))

fun String.convertUtcZonedStringToLocalizedDateTime(zoneId: ZoneId? = ZoneId.systemDefault()): LocalDateTime {
    val utcZonedDateTime = ZonedDateTime.parse(this)
    val zonedDateTime = utcZonedDateTime.withZoneSameInstant(zoneId)
    return zonedDateTime.toLocalDateTime()
}

fun String.toLocalizedDateTime(
    pattern: String? = DEFAULT_DATE_TIME_FORMAT,
    oldZoneId: ZoneId? = ZoneId.systemDefault(),
    newZoneId: ZoneId? = ZoneId.systemDefault()
): LocalDateTime {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern!!)
    val localDateTime = LocalDateTime.parse(this, formatter)
    val zdt = localDateTime.atZone(oldZoneId).withZoneSameInstant(newZoneId)
    return zdt.toLocalDateTime()
}

fun String.toLocalDate(
    pattern: String? = DEFAULT_DATE_FORMAT
): LocalDate {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern!!)
    return LocalDate.parse(this, formatter)
}
