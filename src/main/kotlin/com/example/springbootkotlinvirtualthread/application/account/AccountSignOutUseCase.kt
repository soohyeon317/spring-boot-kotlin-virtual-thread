package com.example.springbootkotlinvirtualthread.application.account

fun interface AccountSignOutUseCase {

    fun signOut(command: AccountSignOutCommand.SignOut)
}
