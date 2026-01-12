package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.authtoken

import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken
import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthTokenRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class AuthTokenRepositoryImpl(
    private val springDataAuthTokenRepository: SpringDataAuthTokenRepository
) : AuthTokenRepository {

    override fun save(
        authToken: AuthToken,
        willDelete: Boolean
    ): AuthToken {
        return springDataAuthTokenRepository.save(
            AuthTokenEntity(
                authToken = authToken,
                willDelete = willDelete
            )
        ).toAuthToken()
    }

    override fun findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken: String): AuthToken? {
        return springDataAuthTokenRepository.findTopByAccessTokenAndDeletedAtIsNullOrderByIdDesc(accessToken)?.toAuthToken()
    }

    override fun findTopByMemberIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(memberId: Long, accessToken: String): AuthToken? {
        return springDataAuthTokenRepository.findTopByMemberIdAndAccessTokenAndDeletedAtIsNullOrderByIdDesc(
            memberId = memberId,
            accessToken = accessToken
        )?.toAuthToken()
    }

    override fun deleteAllByMemberIdAndDeletedAtIsNull(
        memberId: Long,
        deletedAt: LocalDateTime,
    ) {
        return springDataAuthTokenRepository.deleteAllByMemberIdAndDeletedAtIsNull(
            memberId = memberId,
            deletedAt = deletedAt
        )
    }
}
