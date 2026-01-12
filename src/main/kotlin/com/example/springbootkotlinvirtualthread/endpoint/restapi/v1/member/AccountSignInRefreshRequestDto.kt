package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.member

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class AccountSignInRefreshRequestDto(

    @field:NotBlank(message = "NotBlank")
    val accessToken: String? = null,
    @field:NotBlank(message = "NotBlank")
    val refreshToken: String? = null,

    val appPushToken: String? = null,
)
