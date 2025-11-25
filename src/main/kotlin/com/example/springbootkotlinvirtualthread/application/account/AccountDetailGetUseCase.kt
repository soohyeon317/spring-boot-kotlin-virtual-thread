package com.example.springbootkotlinvirtualthread.application.account

import com.example.springbootkotlinvirtualthread.domain.account.AccountForResponse

fun interface AccountDetailGetUseCase {

    fun getAccountDetail(command: AccountDetailGetCommand.GetAccountDetail): AccountForResponse
}
