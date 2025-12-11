package com.example.springbootkotlinvirtualthread.application.appuseenvironment

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironmentRepository
import com.example.springbootkotlinvirtualthread.exception.AppUseEnvironmentNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AppPushTokenUpdateService(
    private val appUseEnvironmentRepository: AppUseEnvironmentRepository,
): AppPushTokenUpdateUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun updateAppPushToken(command: AppPushTokenUpdateCommand.UpdateAppPushToken) {
        try {
            val myAccountId = command.accountId

            val existingAppUseEnvironment = appUseEnvironmentRepository.findTopByAccountIdAndDeviceModelNameAndDeletedAtIsNullOrderByIdDesc(
                accountId = myAccountId,
                deviceModelName = command.deviceModelName,
            ) ?: throw AppUseEnvironmentNotFoundException(code = ErrorCode.APP_USE_ENVIRONMENT_NOT_FOUND)

            val updatedAppUseEnvironment = existingAppUseEnvironment.updateAppPushToken(
                appPushToken = command.appPushToken
            )
            appUseEnvironmentRepository.save(
                appUseEnvironment = updatedAppUseEnvironment
            )
        } catch (e: Throwable) {
            logger().error("command=$command", e)
            throw e
        }
    }
}
