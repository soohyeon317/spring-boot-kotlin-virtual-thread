package com.example.springbootkotlinvirtualthread.application.account

fun interface AccountWithdrawUseCase {

    fun withdraw(command: AccountWithdrawCommand.Withdraw)
}
