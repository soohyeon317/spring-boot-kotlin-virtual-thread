package com.example.springbootkotlinvirtualthread.application.account

fun interface AccountSignOutUseCase {

    suspend fun signOut(command: AccountSignOutCommand.SignOut)
}
