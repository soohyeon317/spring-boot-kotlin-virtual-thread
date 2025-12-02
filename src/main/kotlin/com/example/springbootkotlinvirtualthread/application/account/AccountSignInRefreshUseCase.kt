package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken

fun interface AccountSignInRefreshUseCase {

    fun refreshSignIn(command: AccountSignInRefreshCommand.RefreshSignIn): AuthToken
}
