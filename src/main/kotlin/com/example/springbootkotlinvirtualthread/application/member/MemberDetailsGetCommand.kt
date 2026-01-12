package com.example.springbootkotlinvirtualthread.application.member

class MemberDetailsGetCommand {

    data class GetMemberDetails(val memberId: Long,
                                val languageCode: String,
                                val countryCode: String,
                                val timeZoneCode: String)
}
