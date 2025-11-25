package com.example.springbootkotlinvirtualthread.configuration.redis

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.redis.redisson.config")
data class RedisConfigProperties (
    val singleServerConfig: CustomSingleServerConfig,
    val threads: Int,
    val nettyThreads: Int,
    val transportMode: String
)

data class CustomSingleServerConfig(
    val address: String,
    val password: String? = null,
    val database: Int,
    val idleConnectionTimeout: Int,
    val connectTimeout: Int,
    val timeout: Int,
    val retryAttempts: Int,
    val retryInterval: Int,
    val subscriptionsPerConnection: Int,
    val subscriptionConnectionMinimumIdleSize: Int,
    val subscriptionConnectionPoolSize: Int,
    val connectionMinimumIdleSize: Int,
    val connectionPoolSize: Int,
    val dnsMonitoringInterval: Long
)
