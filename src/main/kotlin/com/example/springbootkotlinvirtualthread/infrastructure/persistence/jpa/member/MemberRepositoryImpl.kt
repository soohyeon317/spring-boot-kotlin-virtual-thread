package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.member

import com.example.springbootkotlinvirtualthread.domain.member.Member
import com.example.springbootkotlinvirtualthread.domain.member.MemberRepository
import com.example.springbootkotlinvirtualthread.domain.member.ThirdPartyAuthType
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val springDataMemberRepository: SpringDataMemberRepository
) : MemberRepository {

    override fun save(
        member: Member,
        willDelete: Boolean,
    ): Member {
        return springDataMemberRepository.save(
            MemberEntity(
                member = member,
                willDelete = willDelete,
            )
        ).toAccount()
    }

    override fun findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String,
    ): Member? {
        return springDataMemberRepository.findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
            thirdPartyAuthType = thirdPartyAuthType,
            thirdPartyAuthUid = thirdPartyAuthUid,
        )?.toAccount()
    }

    override fun findTopByIdAndDeletedAtIsNull(id: Long): Member? {
        return springDataMemberRepository.findTopByIdAndDeletedAtIsNull(
            id = id
        )?.toAccount()
    }
}
