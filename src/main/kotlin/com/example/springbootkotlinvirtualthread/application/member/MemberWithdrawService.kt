package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import com.example.springbootkotlinvirtualthread.domain.member.MemberRepository
import com.example.springbootkotlinvirtualthread.domain.appuseenvironment.AppUseEnvironmentRepository
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import com.example.springbootkotlinvirtualthread.exception.MemberNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MemberWithdrawService(
    private val memberRepository: MemberRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val appUseEnvironmentRepository: AppUseEnvironmentRepository,
): MemberWithdrawUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun withdraw(command: MemberWithdrawCommand.Withdraw) {
        try {
            val myMemberId = command.memberId
            val myMember = memberRepository.findTopByIdAndDeletedAtIsNull(
                id = myMemberId
            ) ?: throw MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND)
            val currentLocalDateTime = LocalDateTime.now()

            /*
            인증 토큰 목록 삭제
             */
            authTokenRepository.deleteAllByMemberIdAndDeletedAtIsNull(
                memberId = myMemberId,
                deletedAt = currentLocalDateTime,
            )

            /*
            앱 사용 환경 목록 삭제
             */
            appUseEnvironmentRepository.deleteAllByMemberIdAndDeletedAtIsNull(
                memberId = myMemberId,
                deletedAt = currentLocalDateTime,
            )

            // 탈퇴 계정 삭제
            memberRepository.save(
                member = myMember,
                willDelete = true
            )
        } catch (e: Throwable) {
            logger().error("command=$command", e)
            throw e
        }
    }
}
