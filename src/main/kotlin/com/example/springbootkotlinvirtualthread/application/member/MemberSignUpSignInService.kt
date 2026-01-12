package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenType
import com.example.springbootkotlinvirtualthread.configuration.authentication.JwtUtil
import com.example.springbootkotlinvirtualthread.domain.member.Member
import com.example.springbootkotlinvirtualthread.domain.member.MemberRepository
import com.example.springbootkotlinvirtualthread.domain.member.ThirdPartyAuthType
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironmentManager
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberSignUpSignInService(
    private val memberRepository: MemberRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val jwtUtil: JwtUtil,
    private val appUseEnvironmentManager: AppUseEnvironmentManager,
) : MemberSignUpSignInUseCase {

    // 회원가입 및 로그인 통합 처리
    @Transactional(rollbackFor = [Throwable::class])
    override fun signUpSignIn(command: MemberSignUpSignInCommand.SignUpSignIn): Pair<AuthToken, Boolean> {
        try {
            var isNewMember = false
            var myMember: Member = memberRepository.findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
                thirdPartyAuthType = command.thirdPartyAuthType,
                thirdPartyAuthUid = command.thirdPartyAuthUid
            ) ?: run {
                // 기존 계정이 없으면, 회원 가입 수행.
                isNewMember = true
                signUp(
                    thirdPartyAuthType = command.thirdPartyAuthType,
                    thirdPartyAuthUid = command.thirdPartyAuthUid,
                    email = command.email,
                    languageCode = command.languageCode,
                    countryCode = command.countryCode,
                    timeZoneCode = command.timeZoneCode
                )
            }
            val myMemberId = myMember.id!!

            /*
             기존 회원인 경우, 기존과 최신 정보를 비교하고 업데이트를 수행한다.
             */
            if (!isNewMember) {
                val willUpdateEmail = (myMember.email != command.email)
                val willUpdateLanguageCode = (myMember.languageCode != command.languageCode)
                val willUpdateCountryCode = (myMember.countryCode != command.countryCode)
                val willUpdateTimeZoneCode = (myMember.timeZoneCode != command.timeZoneCode)
                if (willUpdateEmail || willUpdateLanguageCode || willUpdateCountryCode || willUpdateTimeZoneCode) {
                    myMember = myMember.updateEmail(
                        email = command.email
                    ).updateLocaleInfo(
                        languageCode = command.languageCode,
                        countryCode = command.countryCode,
                        timeZoneCode = command.timeZoneCode,
                    )
                    memberRepository.save(member = myMember)
                }
            }

            /*
            인증 토큰 생성 및 응답
             */
            // 새로운 authToken 저장
            val accessToken = jwtUtil.generateJwt(memberId = myMemberId, tokenType = AuthenticationTokenType.ACCESS)
            val refreshToken = jwtUtil.generateJwt(memberId = myMemberId, tokenType = AuthenticationTokenType.REFRESH)
            val newAuthToken = authTokenRepository.save(
                AuthToken(memberId = myMemberId, accessToken =  accessToken, refreshToken = refreshToken)
            )

            /*
            앱 사용 환경 정보 저장
             */
            appUseEnvironmentManager.create(
                memberId = myMemberId,
                deviceModelName = command.deviceModelName,
                appOs = command.appOs,
                appVersion = command.appVersion,
                appPushToken = command.appPushToken,
            )

            return Pair(newAuthToken, isNewMember)
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
    ): Member {
        val member = memberRepository.save(
            Member(
                thirdPartyAuthType = thirdPartyAuthType,
                thirdPartyAuthUid = thirdPartyAuthUid,
                email = email,
                languageCode = languageCode,
                countryCode = countryCode,
                timeZoneCode = timeZoneCode
            )
        )
        return member
    }
}
