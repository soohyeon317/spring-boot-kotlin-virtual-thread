package com.example.springbootkotlinvirtualthread.configuration.authentication

import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import org.springframework.util.StringUtils
import java.util.*

data class AuthenticationToken(
    val accountId: Long,
    val expiration: Long,
    val expirationDate: Date,
    val tokenType: AuthenticationTokenType
) {

    constructor(claims: Claims) : this(
        accountId = claims[ACCOUNT_ID_CLAIM_KEY].toString().toLong(),
        expiration = claims.expiration.toInstant().toEpochMilli(),
        expirationDate = claims.expiration,
        tokenType = AuthenticationTokenType.valueOf(claims[TOKEN_TYPE_CLAIM_KEY] as String)
    )

    companion object {
        const val ACCOUNT_ID_CLAIM_KEY = "accountId"
        const val TOKEN_TYPE_CLAIM_KEY = "tokenType"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "

        fun getJwtFromRequest(request: HttpServletRequest): String? {
            val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
            return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
                bearerToken.substring(BEARER_PREFIX.length)
            } else {
                null
            }
        }
    }
}
