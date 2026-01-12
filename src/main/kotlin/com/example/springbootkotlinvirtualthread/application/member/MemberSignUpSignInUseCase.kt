package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.domain.authtoken.AuthToken

fun interface MemberSignUpSignInUseCase {

    fun signUpSignIn(command: MemberSignUpSignInCommand.SignUpSignIn): Pair<AuthToken, Boolean>
}
