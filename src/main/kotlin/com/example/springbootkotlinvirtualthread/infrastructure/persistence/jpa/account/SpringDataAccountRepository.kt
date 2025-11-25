package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.account

import com.example.springbootkotlinvirtualthread.domain.account.ThirdPartyAuthType
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataAccountRepository : JpaRepository<AccountEntity, Long> {

    fun findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String
    ): AccountEntity?

    fun findTopByIdAndDeletedAtIsNull(id: Long): AccountEntity?
}
