package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.domain.member.ThirdPartyAuthType
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppOS

class MemberSignUpSignInCommand {

    data class SignUpSignIn(
        val thirdPartyAuthType: ThirdPartyAuthType,
        val thirdPartyAuthUid: String,
        val email: String,
        val languageCode: String,
        val countryCode: String,
        val timeZoneCode: String,
        val deviceModelName: String,
        val appOs: AppOS,
        val appVersion: String,
        val appPushToken: String?
    )
}
