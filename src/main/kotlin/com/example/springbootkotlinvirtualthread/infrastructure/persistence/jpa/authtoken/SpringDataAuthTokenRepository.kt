package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.authtoken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface SpringDataAuthTokenRepository : JpaRepository<AuthTokenEntity, Long> {

    fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthTokenEntity?
    fun findTopByMemberIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(memberId: Long, accessToken: String): AuthTokenEntity?

    @Modifying
    @Query(
        """
        UPDATE AuthTokenEntity at
        SET at.deletedAt = :deletedAt
        WHERE at.memberId = :memberId
            AND at.deletedAt IS NULL
    """
    )
    fun deleteAllByMemberIdAndDeletedAtIsNull(
        memberId: Long,
        deletedAt: LocalDateTime,
    )
}
