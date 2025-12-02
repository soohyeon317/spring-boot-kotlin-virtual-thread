package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.domain.account.AccountRepository
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import com.example.springbootkotlinvirtualthread.exception.AccountNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountWithdrawService(
    private val accountRepository: AccountRepository,
    private val authTokenRepository: AuthTokenRepository,
): AccountWithdrawUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun withdraw(command: AccountWithdrawCommand.Withdraw) {
        try {
            val myAccount = accountRepository.findTopByIdAndDeletedAtIsNull(command.accountId)
                ?: throw AccountNotFoundException(ErrorCode.MY_ACCOUNT_NOT_FOUND)

            // 삭제되지 않은 액세스 토큰 목록 삭제
            deleteAuthTokens(command.accountId)

            // 탈퇴 계정 삭제
            accountRepository.save(
                account = myAccount,
                willDelete = true
            )
        } catch (e: Throwable) {
            logger().error("command=$command", e)
            throw e
        }
    }

    private fun deleteAuthTokens(accountId: Long) {
        val authTokens = authTokenRepository.findAllByAccountIdAndDeletedAtIsNull(accountId)
        for (authToken in authTokens) {
            authTokenRepository.save(authToken, true)
        }
    }
}
