package com.example.springbootkotlinvirtualthread.application.appuseenvironment

class AppPushTokenUpdateCommand {

    data class UpdateAppPushToken(
        val accountId: Long,
        val deviceModelName: String,
        val appPushToken: String,
    )
}
