package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.authtoken

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface SpringDataAuthTokenRepository : JpaRepository<AuthTokenEntity, Long> {

    fun findTopByAccountIdAndDeletedAtIsNullOrderByIdDesc(accountId: Long): AuthTokenEntity?
    fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthTokenEntity?
    fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthTokenEntity?
    fun findTopByAccountIdOrderByIdDesc(accountId: Long): AuthTokenEntity?

    @Modifying
    @Query("""
        UPDATE AuthTokenEntity at
        SET at.deletedAt = :deletedAt
        WHERE at.accountId = :accountId
            AND at.deletedAt IS NULL
    """)
    fun deleteAllByAccountIdAndDeletedAtIsNull(
        accountId: Long,
        deletedAt: LocalDateTime,
    )
}
