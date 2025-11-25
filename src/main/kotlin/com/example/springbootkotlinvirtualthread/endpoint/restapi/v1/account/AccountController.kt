package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.account

import com.example.springbootkotlinvirtualthread.application.account.*
import com.example.springbootkotlinvirtualthread.configuration.annotation.FunctionExecutionBeforeLog
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenManager
import com.example.springbootkotlinvirtualthread.domain.account.AccountForResponse
import com.example.springbootkotlinvirtualthread.domain.account.LocaleInfoDefault
import com.example.springbootkotlinvirtualthread.domain.common.HeaderName
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
    private val authenticationTokenManager: AuthenticationTokenManager,
) {

    @FunctionExecutionBeforeLog
    @PostMapping("/sign-up/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signUpSignIn(
        httpServletRequest: HttpServletRequest,
        @RequestBody @Valid request: AccountSignUpSignInRequestDto
    ): AccountSignUpSignInResponseDto {
        val languageCode = httpServletRequest.getHeader(HeaderName.LANGUAGE_CODE) ?: LocaleInfoDefault.LANGUAGE_CODE.default
        val countryCode = httpServletRequest.getHeader(HeaderName.COUNTRY_CODE) ?: LocaleInfoDefault.COUNTRY_CODE.default
        val timeZoneCode = httpServletRequest.getHeader(HeaderName.TIME_ZONE_CODE) ?: LocaleInfoDefault.TIME_ZONE_CODE.default

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
        val languageCode = httpServletRequest.getHeader(HeaderName.LANGUAGE_CODE) ?: LocaleInfoDefault.LANGUAGE_CODE.default
        val countryCode = httpServletRequest.getHeader(HeaderName.COUNTRY_CODE) ?: LocaleInfoDefault.COUNTRY_CODE.default
        val timeZoneCode = httpServletRequest.getHeader(HeaderName.TIME_ZONE_CODE) ?: LocaleInfoDefault.TIME_ZONE_CODE.default

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
    suspend fun refreshSignIn(
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
}