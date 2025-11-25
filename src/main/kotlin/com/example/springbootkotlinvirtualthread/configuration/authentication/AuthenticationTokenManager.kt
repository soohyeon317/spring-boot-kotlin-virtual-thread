package com.example.springbootkotlinvirtualthread.configuration.authentication

import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.UnAuthorizedException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationTokenManager(
    private val jwtUtil: JwtUtil,
) {

    /**
     * Token 생성
     */
    fun generateToken(accountId: Long, tokenType: AuthenticationTokenType): String {
        return jwtUtil.generateJwt(
            accountId = accountId,
            tokenType = tokenType,
        )
    }

    /**
     * Token 유효성 검증
     */
    fun validateToken(token: String, tokenType: AuthenticationTokenType): Boolean {
        return try {
            jwtUtil.parseJwtClaims(token)
            true
        } catch (e: Throwable) {
            when (e) {
                is ExpiredJwtException -> {
                    val errorCode = if (tokenType == AuthenticationTokenType.ACCESS) {
                        ErrorCode.ACCESS_TOKEN_EXPIRED
                    } else {
                        ErrorCode.REFRESH_TOKEN_EXPIRED
                    }
                    throw UnAuthorizedException(code = errorCode)
                }
                is MalformedJwtException,
                is SignatureException,
                is UnsupportedJwtException,
                is IllegalArgumentException -> {
                    val errorCode = if (tokenType == AuthenticationTokenType.ACCESS) {
                        ErrorCode.ACCESS_TOKEN_INVALID
                    } else {
                        ErrorCode.REFRESH_TOKEN_INVALID
                    }
                    throw UnAuthorizedException(code = errorCode)
                }
                else -> throw e
            }
        }
    }

    /**
     * Token에서 Account ID 추출
     */
    fun getAccountIdFromToken(token: String): Long {
        val claims = jwtUtil.parseJwtClaims(token)
        return when (val accountId = claims[AuthenticationToken.ACCOUNT_ID_CLAIM_KEY]) {
            is Number -> accountId.toLong()
            is String -> accountId.toLong()
            else -> throw IllegalArgumentException("Invalid account ID in token")
        }
    }

    fun getAccountId(): Long = SecurityContextHolder.getContext().authentication!!.principal.toString().toLong()
}

