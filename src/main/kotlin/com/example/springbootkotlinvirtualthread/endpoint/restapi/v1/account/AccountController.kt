package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.account

import com.example.springbootkotlinvirtualthread.application.account.*
import com.example.springbootkotlinvirtualthread.configuration.annotation.FunctionExecutionBeforeLog
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationToken
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenManager
import com.example.springbootkotlinvirtualthread.domain.account.AccountForResponse
import com.example.springbootkotlinvirtualthread.domain.account.LocaleInfoDefault
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppOS
import com.example.springbootkotlinvirtualthread.domain.common.HeaderKey
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.HeaderInvalidException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountSignUpSignInUseCase: AccountSignUpSignInUseCase,
    private val accountDetailsGetUseCase: AccountDetailsGetUseCase,
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
        val languageCodeHeader = httpServletRequest.getHeader(HeaderKey.LANGUAGE_CODE) ?: LocaleInfoDefault.LANGUAGE_CODE.default
        val countryCodeHeader = httpServletRequest.getHeader(HeaderKey.COUNTRY_CODE) ?: LocaleInfoDefault.COUNTRY_CODE.default
        val timeZoneCodeHeader = httpServletRequest.getHeader(HeaderKey.TIME_ZONE_CODE) ?: LocaleInfoDefault.TIME_ZONE_CODE.default

        val deviceModelNameHeader = httpServletRequest.getHeader(HeaderKey.DEVICE_MODEL_NAME)
        val appOsHeader = httpServletRequest.getHeader(HeaderKey.APP_OS)
        val appVersionHeader = httpServletRequest.getHeader(HeaderKey.APP_VERSION)
        val appPushToken = request.appPushToken

        when {
            deviceModelNameHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.DEVICE_MODEL_NAME_HEADER_REQUIRED)
            }
            appOsHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.APP_OS_HEADER_REQUIRED)
            }
            appVersionHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.APP_VERSION_HEADER_REQUIRED)
            }
            else -> {
                val appOs = AppOS.getBy(appOs = appOsHeader)

                return AccountSignUpSignInResponseDto(
                    accountSignUpSignInUseCase.signUpSignIn(
                        AccountSignUpSignInCommand.SignUpSignIn(
                            thirdPartyAuthType = request.thirdPartyAuthType!!,
                            thirdPartyAuthUid = request.thirdPartyAuthUid!!,
                            email = request.email!!,
                            languageCode = languageCodeHeader,
                            countryCode = countryCodeHeader,
                            timeZoneCode = timeZoneCodeHeader,
                            deviceModelName = deviceModelNameHeader,
                            appOs = appOs,
                            appVersion = appVersionHeader,
                            appPushToken = appPushToken,
                        )
                    )
                )
            }
        }
    }

    @FunctionExecutionBeforeLog
    @GetMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    fun getAccountDetails(
        httpServletRequest: HttpServletRequest,
    ): AccountForResponse {
        val accountId = authenticationTokenManager.getAccountId()
        val languageCode = httpServletRequest.getHeader(HeaderKey.LANGUAGE_CODE) ?: LocaleInfoDefault.LANGUAGE_CODE.default
        val countryCode = httpServletRequest.getHeader(HeaderKey.COUNTRY_CODE) ?: LocaleInfoDefault.COUNTRY_CODE.default
        val timeZoneCode = httpServletRequest.getHeader(HeaderKey.TIME_ZONE_CODE) ?: LocaleInfoDefault.TIME_ZONE_CODE.default

        return accountDetailsGetUseCase.getAccountDetails(
            command = AccountDetailsGetCommand.GetAccountDetails(
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
        httpServletRequest: HttpServletRequest,
        @RequestBody @Valid request: AccountSignInRefreshRequestDto
    ): AccountSignInRefreshResponseDto {
        val deviceModelNameHeader = httpServletRequest.getHeader(HeaderKey.DEVICE_MODEL_NAME)
        val appOsHeader = httpServletRequest.getHeader(HeaderKey.APP_OS)
        val appVersionHeader = httpServletRequest.getHeader(HeaderKey.APP_VERSION)
        val appPushToken = request.appPushToken

        when {
            deviceModelNameHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.DEVICE_MODEL_NAME_HEADER_REQUIRED)
            }

            appOsHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.APP_OS_HEADER_REQUIRED)
            }

            appVersionHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.APP_VERSION_HEADER_REQUIRED)
            }

            else -> {
                val appOs = AppOS.getBy(appOs = appOsHeader)

                return AccountSignInRefreshResponseDto(
                    authToken = accountSignInRefreshUseCase.refreshSignIn(
                        command = AccountSignInRefreshCommand.RefreshSignIn(
                            accessToken = request.accessToken!!,
                            refreshToken = request.refreshToken!!,
                            deviceModelName = deviceModelNameHeader,
                            appOs = appOs,
                            appVersion = appVersionHeader,
                            appPushToken = appPushToken
                        )
                    )
                )
            }
        }
    }

    @FunctionExecutionBeforeLog
    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.OK)
    fun signOut(
        httpServletRequest: HttpServletRequest,
    ) {
        val accessToken = AuthenticationToken.getJwtFromRequest(request = httpServletRequest)!!
        val accountId = authenticationTokenManager.getAccountIdFromToken(token = accessToken)

        val deviceModelNameHeader = httpServletRequest.getHeader(HeaderKey.DEVICE_MODEL_NAME)

        when {
            deviceModelNameHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.DEVICE_MODEL_NAME_HEADER_REQUIRED)
            }
            else -> {
                accountSignOutUseCase.signOut(
                    command = AccountSignOutCommand.SignOut(
                        accountId = accountId,
                        accessToken = accessToken,
                        deviceModelName = deviceModelNameHeader,
                    )
                )
            }
        }
    }

    @FunctionExecutionBeforeLog
    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    fun withdraw() {
        val accountId = authenticationTokenManager.getAccountId()
        accountWithdrawUseCase.withdraw(
            command = AccountWithdrawCommand.Withdraw(accountId = accountId)
        )
    }
}