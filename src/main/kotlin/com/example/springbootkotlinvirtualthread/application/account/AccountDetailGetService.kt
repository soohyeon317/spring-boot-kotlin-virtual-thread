package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.domain.account.AccountForResponse
import com.example.springbootkotlinvirtualthread.domain.account.AccountRepository
import com.example.springbootkotlinvirtualthread.exception.AccountNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountDetailGetService(
    private val accountRepository: AccountRepository,
): AccountDetailGetUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun getAccountDetail(command: AccountDetailGetCommand.GetAccountDetail): AccountForResponse {
        try {
            /**
             * 참고: 커플 연결 끊김을 당한 경우에는 커플 연결 끊김 확인 필요 여부를 클라이언트에서 체크하기로 한 상황이기 때문에 서버에서 오류를 반환하지 않음.
             */
            // 기존 계정 데이터 조회
            var myAccount = accountRepository.findTopByIdAndDeletedAtIsNull(id = command.accountId)
                ?: throw AccountNotFoundException(ErrorCode.MY_ACCOUNT_NOT_FOUND)

            // 기존 Locale 정보가 최신 정보와 다르면, 업데이트
            if (!myAccount.isLatestLocaleInfo(
                    latestLanguageCode = command.languageCode,
                    latestCountryCode = command.countryCode,
                    latestTimeZoneCode = command.timeZoneCode
                )) {
                // 언어/국가/타임존 정보 업데이트
                val willUpdateLanguageCode = (myAccount.languageCode != command.languageCode)
                val willUpdateCountryCode = (myAccount.countryCode != command.countryCode)
                val willUpdateTimeZoneCode = (myAccount.timeZoneCode != command.timeZoneCode)
                if (willUpdateLanguageCode || willUpdateCountryCode || willUpdateTimeZoneCode) {
                    myAccount = myAccount.updateLocaleInfo(
                        languageCode = command.languageCode,
                        countryCode = command.countryCode,
                        timeZoneCode = command.timeZoneCode
                    )
                    // 기존 계정 데이터 업데이트
                    accountRepository.save(myAccount)
                }
            }

            return myAccount.toAccountForResponse()
        } catch (e: Throwable) {
            throw e
        }
    }
}
