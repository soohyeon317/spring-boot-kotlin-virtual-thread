package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.appuseenvironment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface SpringDataAppUseEnvironmentRepository : JpaRepository<AppUseEnvironmentEntity, Long> {

    fun findTopByAccountIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
        accountId: Long,
        deviceModelName: String,
    ): AppUseEnvironmentEntity?

    @Modifying
    @Query("""
        UPDATE AppUseEnvironmentEntity aue
        SET aue.deletedAt = :deletedAt
        WHERE aue.accountId = :accountId
            AND aue.deviceModelName = :deviceModelName
            AND aue.deletedAt IS NULL
    """)
    fun deleteAllByAccountIdAndDeviceModelNameAndDeletedAtIsNull(
        accountId: Long,
        deviceModelName: String,
        deletedAt: LocalDateTime,
    )

    @Modifying
    @Query("""
        UPDATE AppUseEnvironmentEntity aue
        SET aue.deletedAt = :deletedAt
        WHERE aue.accountId = :accountId
            AND aue.deletedAt IS NULL
    """)
    fun deleteAllByAccountIdAndDeletedAtIsNull(
        accountId: Long,
        deletedAt: LocalDateTime,
    )
}
