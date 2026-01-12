package com.example.springbootkotlinvirtualthread.domain.authtoken

import java.time.LocalDateTime

data class AuthToken(
    val id: Long?,
    val memberId: Long,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    constructor(memberId: Long, accessToken: String, refreshToken: String) :
            this(
                id = null,
                memberId = memberId,
                accessToken = accessToken,
                refreshToken = refreshToken,
                createdAt = null,
                updatedAt = null,
                deletedAt = null
            )

    fun update(accessToken: String, refreshToken: String): AuthToken = this.copy(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}
