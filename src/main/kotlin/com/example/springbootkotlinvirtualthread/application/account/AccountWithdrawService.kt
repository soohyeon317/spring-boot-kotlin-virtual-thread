package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.domain.account.AccountRepository
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import com.example.springbootkotlinvirtualthread.exception.AccountNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AccountWithdrawService(
    private val accountRepository: AccountRepository,
    private val authTokenRepository: AuthTokenRepository,
): AccountWithdrawUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun withdraw(command: AccountWithdrawCommand.Withdraw) {
        try {
            val myAccountId = command.accountId
            val myAccount = accountRepository.findTopByIdAndDeletedAtIsNull(
                id = myAccountId
            ) ?: throw AccountNotFoundException(ErrorCode.MY_ACCOUNT_NOT_FOUND)
            val currentLocalDateTime = LocalDateTime.now()

            /*
            인증 토큰 목록 삭제
             */
            authTokenRepository.deleteAllByAccountIdAndDeletedAtIsNull(
                accountId = myAccountId,
                deletedAt = currentLocalDateTime,
            )

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
}
