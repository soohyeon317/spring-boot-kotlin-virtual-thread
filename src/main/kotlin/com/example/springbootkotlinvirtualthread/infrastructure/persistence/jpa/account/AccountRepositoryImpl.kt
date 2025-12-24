package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.account

import com.example.springbootkotlinvirtualthread.domain.account.Account
import com.example.springbootkotlinvirtualthread.domain.account.AccountRepository
import com.example.springbootkotlinvirtualthread.domain.account.ThirdPartyAuthType
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val springDataAccountRepository: SpringDataAccountRepository
) : AccountRepository {

    override fun save(
        account: Account,
        willDelete: Boolean,
    ): Account {
        return springDataAccountRepository.save(
            AccountEntity(
                account = account,
                willDelete = willDelete,
            )
        ).toAccount()
    }

    override fun findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
        thirdPartyAuthType: ThirdPartyAuthType,
        thirdPartyAuthUid: String,
    ): Account? {
        return springDataAccountRepository.findTopByThirdPartyAuthTypeAndThirdPartyAuthUidAndDeletedAtIsNullOrderByIdDesc(
            thirdPartyAuthType = thirdPartyAuthType,
            thirdPartyAuthUid = thirdPartyAuthUid,
        )?.toAccount()
    }

    override fun findTopByIdAndDeletedAtIsNull(id: Long): Account? {
        return springDataAccountRepository.findTopByIdAndDeletedAtIsNull(
            id = id
        )?.toAccount()
    }
}
