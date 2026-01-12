package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.member

import com.example.springbootkotlinvirtualthread.domain.member.ThirdPartyAuthType
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataMemberRepository : JpaRepository<MemberEntity, Long> {

    fun findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String
    ): MemberEntity?

    fun findTopByIdAndDeletedAtIsNull(id: Long): MemberEntity?
}
