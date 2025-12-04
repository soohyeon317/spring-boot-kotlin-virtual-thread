package com.example.springbootkotlinvirtualthread.configuration.redis

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.client.codec.StringCodec
import org.redisson.config.Config
import org.redisson.config.TransportMode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfiguration(
    private val redisConfigProperties: RedisConfigProperties
) {

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
            .setCodec(StringCodec.INSTANCE)
            .setThreads(redisConfigProperties.threads)
            .setNettyThreads(redisConfigProperties.nettyThreads)
            .setTransportMode(TransportMode.valueOf(redisConfigProperties.transportMode))

        config.useSingleServer()
            .setAddress(redisConfigProperties.singleServerConfig.address)
            .setPassword(redisConfigProperties.singleServerConfig.password)
            .setDatabase(redisConfigProperties.singleServerConfig.database)
            .setIdleConnectionTimeout(redisConfigProperties.singleServerConfig.idleConnectionTimeout)
            .setConnectTimeout(redisConfigProperties.singleServerConfig.connectTimeout)
            .setTimeout(redisConfigProperties.singleServerConfig.timeout)
            .setRetryAttempts(redisConfigProperties.singleServerConfig.retryInterval)
            .setSubscriptionsPerConnection(redisConfigProperties.singleServerConfig.subscriptionsPerConnection)
            .setSubscriptionConnectionMinimumIdleSize(redisConfigProperties.singleServerConfig.connectionMinimumIdleSize)
            .setSubscriptionConnectionPoolSize(redisConfigProperties.singleServerConfig.subscriptionConnectionPoolSize)
            .setConnectionPoolSize(redisConfigProperties.singleServerConfig.connectionPoolSize)
            .setDnsMonitoringInterval(redisConfigProperties.singleServerConfig.dnsMonitoringInterval)

        return Redisson.create(config)
    }
}
