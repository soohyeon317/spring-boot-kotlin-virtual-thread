package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.domain.member.MemberForResponse
import com.example.springbootkotlinvirtualthread.domain.member.MemberRepository
import com.example.springbootkotlinvirtualthread.exception.MemberNotFoundException
import com.example.springbootkotlinvirtualthread.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberDetailsGetService(
    private val memberRepository: MemberRepository,
): MemberDetailsGetUseCase {

    @Transactional(rollbackFor = [Throwable::class])
    override fun getMemberDetails(command: MemberDetailsGetCommand.GetMemberDetails): MemberForResponse {
        try {
            // 기존 회원 데이터 조회
            var myMember = memberRepository.findTopByIdAndDeletedAtIsNull(id = command.memberId)
                ?: throw MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND)

            // 기존 Locale 정보가 최신 정보와 다르면, 업데이트
            if (!myMember.isLatestLocaleInfo(
                    latestLanguageCode = command.languageCode,
                    latestCountryCode = command.countryCode,
                    latestTimeZoneCode = command.timeZoneCode
                )) {
                // 언어/국가/타임존 정보 업데이트
                val willUpdateLanguageCode = (myMember.languageCode != command.languageCode)
                val willUpdateCountryCode = (myMember.countryCode != command.countryCode)
                val willUpdateTimeZoneCode = (myMember.timeZoneCode != command.timeZoneCode)
                if (willUpdateLanguageCode || willUpdateCountryCode || willUpdateTimeZoneCode) {
                    myMember = myMember.updateLocaleInfo(
                        languageCode = command.languageCode,
                        countryCode = command.countryCode,
                        timeZoneCode = command.timeZoneCode
                    )
                    // 기존 회원 데이터 업데이트
                    memberRepository.save(myMember)
                }
            }

            return myMember.toMemberForResponse()
        } catch (e: Throwable) {
            throw e
        }
    }
}
