package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenType
import com.example.springbootkotlinvirtualthread.configuration.authentication.JwtUtil
import com.example.springbootkotlinvirtualthread.domain.account.Account
import com.example.springbootkotlinvirtualthread.domain.account.AccountRepository
import com.example.springbootkotlinvirtualthread.domain.account.ThirdPartyAuthType
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountSignUpSignInService(
    private val accountRepository: AccountRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val jwtUtil: JwtUtil,
) : AccountSignUpSignInUseCase {

    // 회원가입 및 로그인 통합 처리
    @Transactional(rollbackFor = [Throwable::class])
    override fun signUpSignIn(command: AccountSignUpSignInCommand.SignUpSignIn): Pair<AuthToken, Boolean> {
        try {
            var isNewAccount = false
            var myAccount: Account = accountRepository.findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
                thirdPartyAuthType = command.thirdPartyAuthType,
                thirdPartyAuthUid = command.thirdPartyAuthUid
            ) ?: run {
                // 기존 계정이 없으면, 회원 가입 수행.
                isNewAccount = true
                signUp(
                    thirdPartyAuthType = command.thirdPartyAuthType,
                    thirdPartyAuthUid = command.thirdPartyAuthUid,
                    email = command.email,
                    languageCode = command.languageCode,
                    countryCode = command.countryCode,
                    timeZoneCode = command.timeZoneCode
                )
            }
            val myAccountId = myAccount.id!!

            /*
             기존 회원인 경우, 기존과 최신 정보를 비교하고 업데이트를 수행한다.
             */
            if (!isNewAccount) {
                val willUpdateEmail = (myAccount.email != command.email)
                val willUpdateLanguageCode = (myAccount.languageCode != command.languageCode)
                val willUpdateCountryCode = (myAccount.countryCode != command.countryCode)
                val willUpdateTimeZoneCode = (myAccount.timeZoneCode != command.timeZoneCode)
                if (willUpdateEmail || willUpdateLanguageCode || willUpdateCountryCode || willUpdateTimeZoneCode) {
                    myAccount = myAccount.updateEmail(
                        email = command.email
                    ).updateLocaleInfo(
                        languageCode = command.languageCode,
                        countryCode = command.countryCode,
                        timeZoneCode = command.timeZoneCode,
                    )
                    accountRepository.save(account = myAccount)
                }
            }

            /*
            인증 토큰 생성 및 응답
             */
            // 새로운 authToken 저장
            val accessToken = jwtUtil.generateJwt(accountId = myAccountId, tokenType = AuthenticationTokenType.ACCESS)
            val refreshToken = jwtUtil.generateJwt(accountId = myAccountId, tokenType = AuthenticationTokenType.REFRESH)
            val newAuthToken = authTokenRepository.save(
                AuthToken(accountId = myAccountId, accessToken =  accessToken, refreshToken = refreshToken)
            )

            return Pair(newAuthToken, isNewAccount)
        } catch (e: Throwable) {
            throw e
        }
    }

    private fun signUp(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String,
        email: String,
        languageCode: String,
        countryCode: String,
        timeZoneCode: String
    ): Account {
        val account = accountRepository.save(
            Account(
                thirdPartyAuthType = thirdPartyAuthType,
                thirdPartyAuthUid = thirdPartyAuthUid,
                email = email,
                languageCode = languageCode,
                countryCode = countryCode,
                timeZoneCode = timeZoneCode
            )
        )
        return account
    }
}
