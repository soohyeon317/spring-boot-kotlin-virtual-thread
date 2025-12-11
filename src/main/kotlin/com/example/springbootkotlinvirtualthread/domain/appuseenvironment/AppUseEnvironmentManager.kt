package com.example.springbootkotlinvirtualthread.domain.appuseenvironment

import org.springframework.stereotype.Component

@Component
class AppUseEnvironmentManager(
    private val appUseEnvironmentRepository: AppUseEnvironmentRepository,
) {

    fun create(
        accountId: Long,
        deviceModelName: String,
        appOs: AppOS,
        appVersion: String,
        appPushToken: String?,
    ): AppUseEnvironment {
        val existingAppUseEnvironment =
            appUseEnvironmentRepository.findTopByAccountIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
                accountId = accountId,
                deviceModelName = deviceModelName
            )
        var newAppUseEnvironment = AppUseEnvironment(
            id = null,
            accountId = accountId,
            deviceModelName = deviceModelName,
            appOs = appOs,
            appVersion = appVersion,
            appPushToken = appPushToken,
            createdAt = null,
            updatedAt = null,
            deletedAt = null,
        )

        if (existingAppUseEnvironment != null) {
            val deletedExistingAppUseEnvironment = appUseEnvironmentRepository.save(
                appUseEnvironment = existingAppUseEnvironment,
                willDelete = true
            )
            newAppUseEnvironment = newAppUseEnvironment.updateAppPushTokenByOldAppPushToken(
                oldAppPushToken = deletedExistingAppUseEnvironment.appPushToken,
            )
        }

        return appUseEnvironmentRepository.save(
            appUseEnvironment = newAppUseEnvironment,
        )
    }
}
