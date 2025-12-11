package com.example.springbootkotlinvirtualthread.domain.authtoken

import java.time.LocalDateTime

interface AuthTokenRepository {

    fun save(authToken: AuthToken, willDelete: Boolean? = null): AuthToken
    fun findTopByAccountIdAndDeletedAtIsNullOrderByIdDesc(accountId: Long): AuthToken?
    fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthToken?
    fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthToken?
    fun findTopByAccountIdOrderByIdDesc(accountId: Long): AuthToken?

    fun deleteAllByAccountIdAndDeletedAtIsNull(
        accountId: Long,
        deletedAt: LocalDateTime,
    )
}
