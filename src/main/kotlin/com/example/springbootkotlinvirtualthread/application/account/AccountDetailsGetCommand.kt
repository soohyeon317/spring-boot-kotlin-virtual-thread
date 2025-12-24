package com.example.springbootkotlinvirtualthread.application.account

class AccountDetailsGetCommand {

    data class GetAccountDetails(val accountId: Long,
                                 val languageCode: String,
                                 val countryCode: String,
                                 val timeZoneCode: String)
}
