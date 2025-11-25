package com.example.springbootkotlinvirtualthread.configuration.redis.distributedlock

import kotlinx.coroutines.withTimeout
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Component
class TransactionUtil {

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Exception::class])
    suspend fun <T> executeInNewTransaction(
        timeoutSecond: Long = -1,
        operation: suspend () -> T,
    ): T {
        if (timeoutSecond == -1L) {
            return operation()
        }

        try {
            return withTimeout(timeoutSecond.toDuration(DurationUnit.SECONDS)) {
                operation()
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}
