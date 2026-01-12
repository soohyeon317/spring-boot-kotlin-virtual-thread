package com.example.springbootkotlinvirtualthread.domain.member

import kotlinx.serialization.Serializable

@Serializable
data class MemberForResponse(
    val accountId: Long,
    val thirdPartyAuthType: ThirdPartyAuthType,
    val thirdPartyAuthUid: String,
    val email: String,
    val languageCode: String,
    val countryCode: String,
    val timeZoneCode: String,
    val createdAt: String,
)
