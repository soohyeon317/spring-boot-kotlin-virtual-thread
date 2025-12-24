package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.domain.account.AccountForResponse

fun interface AccountDetailsGetUseCase {

    fun getAccountDetails(command: AccountDetailsGetCommand.GetAccountDetails): AccountForResponse
}
