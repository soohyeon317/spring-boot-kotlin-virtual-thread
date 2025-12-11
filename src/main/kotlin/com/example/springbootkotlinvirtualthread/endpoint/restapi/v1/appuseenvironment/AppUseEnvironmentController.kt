package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.appuseenvironment

import com.example.springbootkotlinvirtualthread.application.appuseenvironment.AppPushTokenUpdateCommand
import com.example.springbootkotlinvirtualthread.application.appuseenvironment.AppPushTokenUpdateUseCase
import com.example.springbootkotlinvirtualthread.configuration.annotation.FunctionExecutionBeforeLog
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenManager
import com.example.springbootkotlinvirtualthread.domain.common.HeaderKey
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.HeaderInvalidException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/app-use-environments")
class AppUseEnvironmentController(
    private val appPushTokenUpdateUseCase: AppPushTokenUpdateUseCase,
    private val authenticationTokenManager: AuthenticationTokenManager,
) {

    @FunctionExecutionBeforeLog
    @PutMapping("/app-push-token")
    @ResponseStatus(HttpStatus.OK)
    fun updateAppPushToken(
        httpServletRequest: HttpServletRequest,
        @RequestBody @Valid request: AppPushTokenUpdateRequestDto
    ) {
        val accountId = authenticationTokenManager.getAccountId()

        val deviceModelNameHeader = httpServletRequest.getHeader(HeaderKey.DEVICE_MODEL_NAME)
        val appPushToken = request.appPushToken!!

        when {
            deviceModelNameHeader.isNullOrEmpty() -> {
                throw HeaderInvalidException(code = ErrorCode.DEVICE_MODEL_NAME_HEADER_REQUIRED)
            }

            else -> {
                appPushTokenUpdateUseCase.updateAppPushToken(
                    command = AppPushTokenUpdateCommand.UpdateAppPushToken(
                        accountId = accountId,
                        deviceModelName = deviceModelNameHeader,
                        appPushToken = appPushToken
                    )
                )
            }
        }
    }
}
