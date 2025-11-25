package com.example.springbootkotlinvirtualthread.configuration.authentication

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtConfigProperties(
    val secretKey: String,
    val accessTokenValidityInSeconds: Int,
    val refreshTokenValidityInSeconds: Int
)
