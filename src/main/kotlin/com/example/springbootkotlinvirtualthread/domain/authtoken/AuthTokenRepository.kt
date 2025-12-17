package com.example.springbootkotlinvirtualthread.domain.authtoken

import java.time.LocalDateTime

interface AuthTokenRepository {

    fun save(authToken: AuthToken, willDelete: Boolean = false): AuthToken
    fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthToken?
    fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthToken?

    fun deleteAllByAccountIdAndDeletedAtIsNull(
        accountId: Long,
        deletedAt: LocalDateTime,
    )
}
