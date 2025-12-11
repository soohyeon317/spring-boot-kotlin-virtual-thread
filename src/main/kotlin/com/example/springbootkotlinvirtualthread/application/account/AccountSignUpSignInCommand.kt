package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.domain.account.ThirdPartyAuthType
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppOS

class AccountSignUpSignInCommand {

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
