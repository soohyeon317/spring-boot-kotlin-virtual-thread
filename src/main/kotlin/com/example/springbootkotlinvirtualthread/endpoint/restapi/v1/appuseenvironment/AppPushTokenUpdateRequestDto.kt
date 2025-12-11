package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.appuseenvironment

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class AppPushTokenUpdateRequestDto(

    @field:NotBlank(message = "NotBlank")
    val appPushToken: String? = null
)
