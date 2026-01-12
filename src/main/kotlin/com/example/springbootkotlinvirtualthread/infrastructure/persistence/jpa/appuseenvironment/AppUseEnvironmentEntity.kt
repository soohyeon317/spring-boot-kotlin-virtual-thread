package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.appuseenvironment

import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppOS
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironment
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "app_use_environment")
data class AppUseEnvironmentEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val memberId: Long,
    val deviceModelName: String,
    @Enumerated(EnumType.STRING) val appOs: AppOS,
    val appVersion: String,
    val appPushToken: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    constructor(appUseEnvironment: AppUseEnvironment, willDelete: Boolean = false) :
            this(
                id = appUseEnvironment.id,
                memberId = appUseEnvironment.memberId,
                deviceModelName = appUseEnvironment.deviceModelName,
                appVersion = appUseEnvironment.appVersion,
                appOs = appUseEnvironment.appOs,
                appPushToken = appUseEnvironment.appPushToken,
                createdAt = if (appUseEnvironment.id == null) {
                    LocalDateTime.now()
                } else {
                    appUseEnvironment.createdAt ?: LocalDateTime.now()
                },
                updatedAt = if (appUseEnvironment.id != null) {
                    if (willDelete) {
                        appUseEnvironment.updatedAt
                    } else {
                        LocalDateTime.now()
                    }
                } else {
                    null
                },
                deletedAt = if (willDelete) {
                    LocalDateTime.now()
                } else {
                    null
                }
            )

    fun toAppUseEnvironment(): AppUseEnvironment = AppUseEnvironment(
        id = this.id,
        memberId = this.memberId,
        deviceModelName = this.deviceModelName,
        appOs = this.appOs,
        appVersion = this.appVersion,
        appPushToken = this.appPushToken,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )
}
