package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.account

import com.example.springbootkotlinvirtualthread.application.account.*
import com.example.springbootkotlinvirtualthread.configuration.annotation.FunctionExecutionBeforeLog
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationToken
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenManager
import com.example.springbootkotlinvirtualthread.domain.account.AccountForResponse
import com.example.springbootkotlinvirtualthread.domain.account.LocaleInfoDefault
import com.example.springbootkotlinvirtualthread.domain.common.HeaderKey
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountSignUpSignInUseCase: AccountSignUpSignInUseCase,
    private val accountDetailGetUseCase: AccountDetailGetUseCase,
    private val accountSignInRefreshUseCase: AccountSignInRefreshUseCase,
    private val accountSignOutUseCase: AccountSignOutUseCase,
    private val accountWithdrawUseCase: AccountWithdrawUseCase,
    private val authenticationTokenManager: AuthenticationTokenManager,
) {

    @FunctionExecutionBeforeLog
    @PostMapping("/sign-up/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signUpSignIn(
        httpServletRequest: HttpServletRequest,
        @RequestBody @Valid request: AccountSignUpSignInRequestDto
    ): AccountSignUpSignInResponseDto {
        val languageCode = httpServletRequest.getHeader(HeaderKey.LANGUAGE_CODE) ?: LocaleInfoDefault.LANGUAGE_CODE.default
        val countryCode = httpServletRequest.getHeader(HeaderKey.COUNTRY_CODE) ?: LocaleInfoDefault.COUNTRY_CODE.default
        val timeZoneCode = httpServletRequest.getHeader(HeaderKey.TIME_ZONE_CODE) ?: LocaleInfoDefault.TIME_ZONE_CODE.default

        return AccountSignUpSignInResponseDto(
            accountSignUpSignInUseCase.signUpSignIn(
                AccountSignUpSignInCommand.SignUpSignIn(
                    thirdPartyAuthType = request.thirdPartyAuthType!!,
                    thirdPartyAuthUid = request.thirdPartyAuthUid!!,
                    email = request.email!!,
                    languageCode = languageCode,
                    countryCode = countryCode,
                    timeZoneCode = timeZoneCode,
                )
            )
        )
    }

    @FunctionExecutionBeforeLog
    @GetMapping("/detail")
    @ResponseStatus(HttpStatus.OK)
    fun getAccountDetail(
        httpServletRequest: HttpServletRequest,
    ): AccountForResponse {
        val accountId = authenticationTokenManager.getAccountId()
        val languageCode = httpServletRequest.getHeader(HeaderKey.LANGUAGE_CODE) ?: LocaleInfoDefault.LANGUAGE_CODE.default
        val countryCode = httpServletRequest.getHeader(HeaderKey.COUNTRY_CODE) ?: LocaleInfoDefault.COUNTRY_CODE.default
        val timeZoneCode = httpServletRequest.getHeader(HeaderKey.TIME_ZONE_CODE) ?: LocaleInfoDefault.TIME_ZONE_CODE.default

        return accountDetailGetUseCase.getAccountDetail(
            command = AccountDetailGetCommand.GetAccountDetail(
                accountId = accountId,
                languageCode = languageCode,
                countryCode = countryCode,
                timeZoneCode = timeZoneCode,
            )
        )
    }

    @FunctionExecutionBeforeLog
    @PostMapping("/sign-in/refresh")
    @ResponseStatus(HttpStatus.OK)
    fun refreshSignIn(
        @RequestBody @Valid request: AccountSignInRefreshRequestDto
    ): AccountSignInRefreshResponseDto {
        return AccountSignInRefreshResponseDto(
            authToken = accountSignInRefreshUseCase.refreshSignIn(
                command = AccountSignInRefreshCommand.RefreshSignIn(
                    accessToken = request.accessToken!!,
                    refreshToken = request.refreshToken!!,
                )
            )
        )
    }

    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.OK)
    fun signOut(
        httpServletRequest: HttpServletRequest,
    ) {
        val accessToken = AuthenticationToken.getJwtFromRequest(request = httpServletRequest)!!
        val accountId = authenticationTokenManager.getAccountIdFromToken(token = accessToken)
        accountSignOutUseCase.signOut(
            command = AccountSignOutCommand.SignOut(accountId = accountId, accessToken = accessToken
            )
        )
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw() {
        val accountId = authenticationTokenManager.getAccountId()
        accountWithdrawUseCase.withdraw(
            command = AccountWithdrawCommand.Withdraw(accountId = accountId)
        )
    }
}