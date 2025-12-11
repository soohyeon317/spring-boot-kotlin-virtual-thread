package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenManager
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenType
import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironmentManager
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import com.example.springbootkotlinvirtualthread.exception.AuthTokenNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import com.example.springbootkotlinvirtualthread.exception.InputInvalidException
import com.example.springbootkotlinvirtualthread.exception.UnAuthorizedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountSignInRefreshService(
    private val authTokenRepository: AuthTokenRepository,
    private val authenticationTokenManager: AuthenticationTokenManager,
    private val appUseEnvironmentManager: AppUseEnvironmentManager,
): AccountSignInRefreshUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun refreshSignIn(command: AccountSignInRefreshCommand.RefreshSignIn): AuthToken {
        try {
            // 해당 AccessToken 정보와 일치하는 AuthToken 데이터 가져오기
            val authToken = authTokenRepository.findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(command.accessToken)
                ?: throw AuthTokenNotFoundException(code = ErrorCode.ACCESS_TOKEN_NOT_FOUND)
            val myAccountId = authToken.accountId

            // RefreshToken 유효성 검증
            authenticationTokenManager.validateToken(command.refreshToken, AuthenticationTokenType.REFRESH)
            // RefreshToken 일치 여부 검사
            if (command.refreshToken != authToken.refreshToken) {
                throw InputInvalidException(code = ErrorCode.REFRESH_TOKEN_NOT_MATCHED)
            }

            // 새로운 토큰 생성
            val newAccessToken =
                authenticationTokenManager.generateToken(authToken.accountId, AuthenticationTokenType.ACCESS)
            val newRefreshToken =
                authenticationTokenManager.generateToken(authToken.accountId, AuthenticationTokenType.REFRESH)

            /*
            앱 사용 환경 정보 저장
             */
            appUseEnvironmentManager.create(
                accountId = myAccountId,
                deviceModelName = command.deviceModelName,
                appOs = command.appOs,
                appVersion = command.appVersion,
                appPushToken = command.appPushToken,
            )

            return authTokenRepository.save(
                authToken.update(newAccessToken, newRefreshToken)
            )
        } catch (e: Throwable) {
            if (e is UnAuthorizedException) {
                logger().warn("command=$command", e)
            } else {
                logger().error("command=$command", e)
            }
            throw e
        }
    }
}
