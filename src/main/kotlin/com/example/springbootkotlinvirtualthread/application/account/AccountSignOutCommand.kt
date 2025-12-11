package com.example.springbootkotlinvirtualthread.application.account

class AccountSignOutCommand {

    data class SignOut(
        val accountId: Long,
        val accessToken: String,
        val deviceModelName: String,
    )
}
