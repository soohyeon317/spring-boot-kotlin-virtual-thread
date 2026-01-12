package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppOS

class MemberSignInRefreshCommand {

    data class RefreshSignIn(
        val accessToken: String,
        val refreshToken: String,
        val deviceModelName: String,
        val appOs: AppOS,
        val appVersion: String,
        val appPushToken: String?
    )
}
