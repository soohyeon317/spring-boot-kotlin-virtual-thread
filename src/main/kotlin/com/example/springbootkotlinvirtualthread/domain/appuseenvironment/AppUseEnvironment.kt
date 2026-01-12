package com.example.springbootkotlinvirtualthread.domain.appuseenvironment

import java.time.LocalDateTime

data class AppUseEnvironment (
    val id: Long?,
    val memberId: Long,
    val deviceModelName: String,
    val appOs: AppOS,
    val appVersion: String,
    val appPushToken: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    fun updateAppPushToken(appPushToken: String): AppUseEnvironment = this.copy(
        appPushToken = appPushToken
    )

    fun updateAppPushTokenByOldAppPushToken(oldAppPushToken: String?): AppUseEnvironment {
        return if (this.appPushToken == null) {
            this.copy(
                appPushToken = oldAppPushToken
            )
        } else {
            this
        }
    }
}
