package com.example.springbootkotlinvirtualthread.application.member

fun interface MemberSignOutUseCase {

    fun signOut(command: MemberSignOutCommand.SignOut)
}
