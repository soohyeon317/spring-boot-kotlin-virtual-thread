package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.appuseenvironment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface SpringDataAppUseEnvironmentRepository : JpaRepository<AppUseEnvironmentEntity, Long> {

    fun findTopByMemberIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
        memberId: Long,
        deviceModelName: String,
    ): AppUseEnvironmentEntity?

    @Modifying
    @Query(
        """
        UPDATE AppUseEnvironmentEntity aue
        SET aue.deletedAt = :deletedAt
        WHERE aue.memberId = :memberId
            AND aue.deviceModelName = :deviceModelName
            AND aue.deletedAt IS NULL
    """
    )
    fun deleteAllByMemberIdAndDeviceModelNameAndDeletedAtIsNull(
        memberId: Long,
        deviceModelName: String,
        deletedAt: LocalDateTime,
    )

    @Modifying
    @Query(
        """
        UPDATE AppUseEnvironmentEntity aue
        SET aue.deletedAt = :deletedAt
        WHERE aue.memberId = :memberId
            AND aue.deletedAt IS NULL
    """
    )
    fun deleteAllByMemberIdAndDeletedAtIsNull(
        memberId: Long,
        deletedAt: LocalDateTime,
    )
}
