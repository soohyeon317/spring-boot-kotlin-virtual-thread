package com.example.springbootkotlinvirtualthread.domain.appuseenvironment

import java.time.LocalDateTime

interface AppUseEnvironmentRepository {

    fun save(
        appUseEnvironment: AppUseEnvironment,
        willDelete: Boolean? = null
    ): AppUseEnvironment

    fun findTopByAccountIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
        accountId: Long,
        deviceModelName: String
    ): AppUseEnvironment?

    fun deleteAllByAccountIdAndDeviceModelNameAndDeletedAtIsNull(
        accountId: Long,
        deviceModelName: String,
        deletedAt: LocalDateTime
    )

    fun deleteAllByAccountIdAndDeletedAtIsNull(
        accountId: Long,
        deletedAt: LocalDateTime
    )
}
