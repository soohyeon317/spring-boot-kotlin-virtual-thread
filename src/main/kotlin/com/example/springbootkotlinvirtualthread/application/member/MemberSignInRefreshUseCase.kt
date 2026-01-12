package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken

fun interface MemberSignInRefreshUseCase {

    fun refreshSignIn(command: MemberSignInRefreshCommand.RefreshSignIn): AuthToken
}
