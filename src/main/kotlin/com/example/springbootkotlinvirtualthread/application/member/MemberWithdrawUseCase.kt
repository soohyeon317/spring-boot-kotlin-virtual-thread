package com.example.springbootkotlinvirtualthread.application.member

fun interface MemberWithdrawUseCase {

    fun withdraw(command: MemberWithdrawCommand.Withdraw)
}
