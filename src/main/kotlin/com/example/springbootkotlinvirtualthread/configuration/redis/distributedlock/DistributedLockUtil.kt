package com.example.springbootkotlinvirtualthread.configuration.redis.distributedlock

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.exception.DistributedLockAcquisitionFailureException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.MethodRuntimeTimeoutException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.redisson.api.RedissonReactiveClient
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.concurrent.TimeUnit

@Component
class DistributedLockUtil(
    private val redissonReactiveClient: RedissonReactiveClient,
    private val transactionUtil: TransactionUtil
) {

    val random = SecureRandom()

    suspend fun <T> run(targetName: String, id: String, block: suspend () -> T): T {
        val uniqueId = random.nextLong()
        val lockName = "$targetName:$id"
        val lock = redissonReactiveClient.getLock(lockName)

        val available = lock.tryLock(TRY_LOCK_TIMEOUT, LEASE_TIMEOUT, TimeUnit.SECONDS, uniqueId).awaitSingle()

        if (!available) {
            throw DistributedLockAcquisitionFailureException(
                code = ErrorCode.DISTRIBUTED_LOCK_ACQUISITION_FAILURE,
                message = "${ErrorCode.DISTRIBUTED_LOCK_ACQUISITION_FAILURE.message} (LockName=$lockName)"
            )
        }

        try {
            logger().info("Distributed Lock acquired. (LockName=$lockName, UniqueId=$uniqueId, TryLockTimeout=$TRY_LOCK_TIMEOUT, LeaseTimeout=$LEASE_TIMEOUT, TargetMethodTimeout=$TARGET_METHOD_TIMEOUT)")
            return transactionUtil.executeInNewTransaction(
                timeoutSecond = TARGET_METHOD_TIMEOUT,
                operation = { block.invoke() }
            )
        } catch (ex: Exception) {
            when (ex) {
                is TimeoutCancellationException -> {
                    throw MethodRuntimeTimeoutException(code = ErrorCode.METHOD_RUNTIME_TIMEOUT)
                }

                else -> throw ex
            }
        } finally {
            withContext(NonCancellable) {
                lock.unlock(uniqueId).awaitSingleOrNull()
                logger().info("Distributed Lock released. (LockName=$lockName, UniqueId=$uniqueId, TryLockTimeout=$TRY_LOCK_TIMEOUT, LeaseTimeout=$LEASE_TIMEOUT, TargetMethodTimeout=$TARGET_METHOD_TIMEOUT)")
            }
        }
    }

    companion object {
        // 획득까지 대기 시간
        private const val TRY_LOCK_TIMEOUT = 0L

        // 획득 이후 잡고 있을 시간, 이 시간이 지나도 unlock되지 않으면 자동으로 unlock
        private const val LEASE_TIMEOUT = 6L

        private const val TARGET_METHOD_TIMEOUT = 5L
    }
}
