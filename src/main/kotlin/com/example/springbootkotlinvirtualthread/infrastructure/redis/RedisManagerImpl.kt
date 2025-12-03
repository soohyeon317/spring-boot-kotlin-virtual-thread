package com.example.springbootkotlinvirtualthread.infrastructure.redis

import com.example.springbootkotlinvirtualthread.configuration.redis.RedisManager
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class RedisManagerImpl(
    private val redissonClient: RedissonClient
): RedisManager {

    override fun rollbackForStringValue(previousKeyValues: Map<String, String?>) {
        for (previousKeyValue in previousKeyValues.entries) {
            if (previousKeyValue.value == null) {
                deleteKeyForStringValue(previousKeyValue.key)
            } else {
                val bucket = redissonClient.getBucket<String>(previousKeyValue.key)
                val hasKey = bucket.isExists
                if (!hasKey) {
                    bucket.setIfAbsent(previousKeyValue.value)
                } else {
                    bucket.setIfExists(previousKeyValue.value)
                }
            }
        }
    }

    override fun rollbackForListValue(previousKeyValues: Map<String, List<String>?>) {
        for (previousKeyValue in previousKeyValues.entries) {
            deleteKeyForListValue(previousKeyValue.key)
            val values = previousKeyValue.value
            if (values != null) {
                val list = redissonClient.getList<String>(previousKeyValue.key)
                for (value in values) {
                    list.add(value)
                }
            }
        }
    }

    override fun deleteKeyForListValue(key: String): Boolean {
        val bucket = redissonClient.getList<String>(key)
        val hasBucket = bucket.isExists
        return if (!hasBucket) {
            true
        } else {
            bucket.delete()
        }
    }

    private fun deleteKeyForStringValue(key: String): Boolean {
        val bucket = redissonClient.getBucket<String>(key)
        val hasBucket = bucket.isExists
        return if (!hasBucket) {
            true
        } else {
            bucket.delete()
        }
    }
}
