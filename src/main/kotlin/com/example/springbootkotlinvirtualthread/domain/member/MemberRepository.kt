package com.example.springbootkotlinvirtualthread.domain.member

interface MemberRepository {

    fun save(member: Member, willDelete: Boolean = false): Member
    fun findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String
    ): Member?
    fun findTopByIdAndDeletedAtIsNull(id: Long): Member?
}
