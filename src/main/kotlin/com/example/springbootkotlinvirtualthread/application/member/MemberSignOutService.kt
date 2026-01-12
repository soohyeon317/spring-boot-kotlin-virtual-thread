package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironmentRepository
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import com.example.springbootkotlinvirtualthread.exception.AuthTokenNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MemberSignOutService(
    private val authTokenRepository: AuthTokenRepository,
    private val appUseEnvironmentRepository: AppUseEnvironmentRepository,
): MemberSignOutUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun signOut(command: MemberSignOutCommand.SignOut) {
        try {
            val myMemberId = command.memberId
            val authToken = authTokenRepository.findTopByMemberIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(
                memberId = myMemberId,
                accessToken = command.accessToken
            ) ?: throw AuthTokenNotFoundException(ErrorCode.ACCESS_TOKEN_NOT_FOUND)
            authTokenRepository.save(authToken, true)

            /*
            앱 사용 환경 정보 삭제
             */
            val currentLocalDateTime = LocalDateTime.now()
            appUseEnvironmentRepository.deleteAllByMemberIdAndDeviceModelNameAndDeletedAtIsNull(
                memberId = myMemberId,
                deviceModelName = command.deviceModelName,
                deletedAt = currentLocalDateTime
            )
        } catch (e: Throwable) {
            logger().error("command=$command", e)
            throw e
        }
    }
}
