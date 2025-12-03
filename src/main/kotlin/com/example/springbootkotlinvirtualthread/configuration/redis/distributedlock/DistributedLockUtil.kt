package com.example.springbootkotlinvirtualthread.configuration.redis.distributedlock

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.exception.DistributedLockAcquisitionFailureException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.MethodExecutionTimeoutException
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@Component
class DistributedLockUtil(
    private val redissonClient: RedissonClient,
    private val transactionUtil: TransactionUtil,
) {

    fun run(lockName: String, operation: () -> Any?): Any? {
        val rLock = redissonClient.getLock(lockName)

        return try {
            val isLocked = rLock.tryLock(LOCK_TRY_TIMEOUT, LOCK_LEASE_TIMEOUT, timeUnit)
            if (!isLocked) {
                throw DistributedLockAcquisitionFailureException(ErrorCode.DISTRIBUTED_LOCK_ACQUISITION_FAILURE)
            }

            logger().info("Distributed Lock acquired. (LockName=$lockName, LockTryTimeout=$LOCK_TRY_TIMEOUT, LockLeaseTimeout=$LOCK_LEASE_TIMEOUT, MethodExecutionTimeout=$METHOD_EXECUTION_TIMEOUT)")
            transactionUtil.executeInNewTransaction(
                timeout = METHOD_EXECUTION_TIMEOUT,
                timeUnit = timeUnit,
            ) {
                operation()
            }
        } catch (_: TimeoutException) {
            throw MethodExecutionTimeoutException(ErrorCode.METHOD_EXECUTION_TIMEOUT)
        } catch (e: Throwable) {
            throw e
        } finally {
            if (rLock.isLocked && rLock.isHeldByCurrentThread) {
                rLock.unlock()
                logger().info("Distributed Lock released. (LockName=$lockName, LockTryTimeout=$LOCK_TRY_TIMEOUT, LockLeaseTimeout=$LOCK_LEASE_TIMEOUT, MethodExecutionTimeout=$METHOD_EXECUTION_TIMEOUT)")
            }
        }
    }

    companion object {
        private const val LOCK_TRY_TIMEOUT = 0L
        private const val LOCK_LEASE_TIMEOUT = 6L
        private const val METHOD_EXECUTION_TIMEOUT = 5L
        private val timeUnit = TimeUnit.SECONDS
    }
}