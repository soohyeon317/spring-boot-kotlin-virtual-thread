package com.example.springbootkotlinvirtualthread.domain.account

interface AccountRepository {

    fun save(account: Account, willDelete: Boolean? = null): Account
    fun findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String
    ): Account?
    fun findTopByIdAndDeletedAtIsNull(id: Long): Account?
}
