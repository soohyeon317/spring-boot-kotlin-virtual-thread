package com.example.springbootkotlinvirtualthread.domain.appuseenvironment

import java.time.LocalDateTime

interface AppUseEnvironmentRepository {

    fun save(
        appUseEnvironment: AppUseEnvironment,
        willDelete: Boolean = false
    ): AppUseEnvironment

    fun findTopByMemberIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
        memberId: Long,
        deviceModelName: String
    ): AppUseEnvironment?

    fun deleteAllByMemberIdAndDeviceModelNameAndDeletedAtIsNull(
        memberId: Long,
        deviceModelName: String,
        deletedAt: LocalDateTime
    )

    fun deleteAllByMemberIdAndDeletedAtIsNull(
        memberId: Long,
        deletedAt: LocalDateTime
    )
}
