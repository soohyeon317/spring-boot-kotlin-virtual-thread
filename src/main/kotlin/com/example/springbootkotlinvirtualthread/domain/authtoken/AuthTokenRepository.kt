package com.example.springbootkotlinvirtualthread.domain.authtoken

interface AuthTokenRepository {

    fun save(authToken: AuthToken, willDelete: Boolean? = null): AuthToken
    fun findTopByAccountIdAndDeletedAtIsNullOrderByIdDesc(accountId: Long): AuthToken?
    fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthToken?
    fun findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(accountId: Long, accessToken: String): AuthToken?
    fun findTopByAccountIdOrderByIdDesc(accountId: Long): AuthToken?
    fun findAllByAccountIdAndDeletedAtIsNull(accountId: Long): List<AuthToken>
}
