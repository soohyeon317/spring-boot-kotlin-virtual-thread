package com.example.springbootkotlinvirtualthread.application.account

class AccountSignInRefreshCommand {

    data class RefreshSignIn(
        val accessToken: String,
        val refreshToken: String,
    )
}
