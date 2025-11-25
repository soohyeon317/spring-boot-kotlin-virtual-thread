package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.account

import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken
import kotlinx.serialization.Serializable

@Serializable
data class AccountSignInRefreshResponseDto(
    val accessToken: String,
    val refreshToken: String,
) {

    constructor(authToken: AuthToken): this(
        accessToken = authToken.accessToken,
        refreshToken = authToken.refreshToken,
    )
}
