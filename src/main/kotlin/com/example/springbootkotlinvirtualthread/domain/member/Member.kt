package com.example.springbootkotlinvirtualthread.domain.member

import com.example.springbootkotlinvirtualthread.util.serializedUTCDateTime
import java.time.LocalDateTime

data class Member (
    val id: Long?,
    val thirdPartyAuthType: ThirdPartyAuthType,
    val thirdPartyAuthUid: String,
    val email: String,
    val languageCode: String,
    val countryCode: String,
    val timeZoneCode: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    constructor(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String,
        email: String,
        languageCode: String,
        countryCode: String,
        timeZoneCode: String
    ): this(
        id = null,
        thirdPartyAuthType = thirdPartyAuthType,
        thirdPartyAuthUid = thirdPartyAuthUid,
        email = email,
        languageCode = languageCode,
        countryCode = countryCode,
        timeZoneCode = timeZoneCode,
        createdAt = null,
        updatedAt = null,
        deletedAt = null
    )

    fun isLatestLocaleInfo(
        latestLanguageCode: String,
        latestCountryCode: String,
        latestTimeZoneCode: String
    ): Boolean = (this.languageCode == latestLanguageCode &&
            this.countryCode == latestCountryCode &&
            this.timeZoneCode == latestTimeZoneCode)

    fun updateEmail(email: String): Member = this.copy(
        email = email
    )

    fun updateLocaleInfo(languageCode: String, countryCode: String, timeZoneCode: String): Member = this.copy(
        languageCode = languageCode,
        countryCode = countryCode,
        timeZoneCode = timeZoneCode,
    )

    fun toMemberForResponse(): MemberForResponse = MemberForResponse(
        accountId = this.id!!,
        thirdPartyAuthType = this.thirdPartyAuthType,
        thirdPartyAuthUid = this.thirdPartyAuthUid,
        email = this.email,
        languageCode = this.languageCode,
        countryCode = this.countryCode,
        timeZoneCode = this.timeZoneCode,
        createdAt = this.createdAt!!.serializedUTCDateTime(),
    )
}
