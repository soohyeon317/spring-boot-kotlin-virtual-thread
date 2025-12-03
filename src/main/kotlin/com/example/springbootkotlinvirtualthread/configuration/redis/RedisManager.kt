package com.example.springbootkotlinvirtualthread.configuration.redis

interface RedisManager {

    fun rollbackForStringValue(previousKeyValues: Map<String, String?>)
    fun rollbackForListValue(previousKeyValues: Map<String, List<String>?>)
    fun deleteKeyForListValue(key: String): Boolean
}
