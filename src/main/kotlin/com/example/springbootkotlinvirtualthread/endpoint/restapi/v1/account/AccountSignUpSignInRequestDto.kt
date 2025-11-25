package com.example.springbootkotlinvirtualthread.endpoint.restapi.v1.account

import com.example.springbootkotlinvirtualthread.domain.account.ThirdPartyAuthType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable

@Serializable
data class AccountSignUpSignInRequestDto(

    @field:NotNull(message = "NotNull")
    val thirdPartyAuthType: ThirdPartyAuthType? = null,

    @field:NotBlank(message = "NotBlank")
    val thirdPartyAuthUid: String? = null,

    @field:Email(message = "Should be an email format.")
    @field:NotBlank(message = "NotBlank")
    val email: String? = null
)
