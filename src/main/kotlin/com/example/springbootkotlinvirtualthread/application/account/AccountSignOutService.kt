package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import com.example.springbootkotlinvirtualthread.exception.AuthTokenNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountSignOutService(
    private val authTokenRepository: AuthTokenRepository,
): AccountSignOutUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override suspend fun signOut(command: AccountSignOutCommand.SignOut) {
        try {
            val authToken = authTokenRepository.findTopByAccountIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(
                accountId = command.accountId,
                accessToken = command.accessToken
            ) ?: throw AuthTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)
            authTokenRepository.save(authToken, true)
        } catch (e: Throwable) {
            logger().error("command=$command", e)
            throw e
        }
    }
}
