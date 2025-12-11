package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppOS

class AccountSignInRefreshCommand {

    data class RefreshSignIn(
        val accessToken: String,
        val refreshToken: String,
        val deviceModelName: String,
        val appOs: AppOS,
        val appVersion: String,
        val appPushToken: String?
    )
}
