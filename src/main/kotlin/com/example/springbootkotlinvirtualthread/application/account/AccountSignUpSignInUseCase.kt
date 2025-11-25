package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken

fun interface AccountSignUpSignInUseCase {

    fun signUpSignIn(command: AccountSignUpSignInCommand.SignUpSignIn): Pair<AuthToken, Boolean>
}
