package com.example.springbootkotlinvirtualthread.configuration.redis.distributedlock

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@Component
class TransactionUtil {

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [Throwable::class])
    fun <T> executeInNewTransaction(
        timeout: Long,
        timeUnit: TimeUnit,
        operation: () -> T,
    ): T {
        if (timeout <= -1L) {
            return operation()
        }

        return CompletableFuture.supplyAsync { operation() }[timeout, timeUnit]
    }
}
