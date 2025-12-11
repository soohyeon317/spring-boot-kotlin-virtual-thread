package com.example.springbootkotlinvirtualthread.application.appuseenvironment

fun interface AppPushTokenUpdateUseCase {

    fun updateAppPushToken(command: AppPushTokenUpdateCommand.UpdateAppPushToken)
}
