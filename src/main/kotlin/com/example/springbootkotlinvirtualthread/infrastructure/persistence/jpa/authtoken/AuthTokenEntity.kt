package com.example.springbootkotlinvirtualthread.infrastructure.persistence.jpa.authtoken

import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "auth_token")
data class AuthTokenEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    val accountId: Long,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val deletedAt: LocalDateTime?
) {

    constructor(authToken: AuthToken, willDelete: Boolean? = null) :
            this(
                id = authToken.id,
                accountId = authToken.accountId,
                accessToken = authToken.accessToken,
                refreshToken = authToken.refreshToken,
                createdAt = if (authToken.id == null) {
                    LocalDateTime.now()
                } else {
                    authToken.createdAt ?: LocalDateTime.now()
                },
                updatedAt = if (authToken.id != null) {
                    if (willDelete == true) {
                        authToken.updatedAt
                    } else {
                        LocalDateTime.now()
                    }
                } else {
                    null
                },
                deletedAt = if (willDelete != null && willDelete == true) {
                    LocalDateTime.now()
                } else {
                    null
                }
            )

    fun toAuthToken(): AuthToken = AuthToken(
        id = this.id,
        accountId = this.accountId,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deletedAt = this.deletedAt
    )
}
