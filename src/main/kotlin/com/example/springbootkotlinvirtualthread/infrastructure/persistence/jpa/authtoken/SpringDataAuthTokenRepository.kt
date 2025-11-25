package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.authtoken

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataAuthTokenRepository : JpaRepository<AuthTokenEntity, Long> {

    fun findTopByAccountIdAndDeletedAtIsNullOrderByIdDesc(accountId: Long): AuthTokenEntity?
    fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthTokenEntity?
    fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthTokenEntity?
    fun findTopByAccountIdOrderByIdDesc(accountId: Long): AuthTokenEntity?
    fun findAllByAccountIdAndDeletedAtIsNull(accountId: Long): List<AuthTokenEntity>
}
