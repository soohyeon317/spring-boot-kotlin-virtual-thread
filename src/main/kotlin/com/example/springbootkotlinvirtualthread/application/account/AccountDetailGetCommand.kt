package com.example.springbootkotlinvirtualthread.application.account

class AccountDetailGetCommand {

    data class GetAccountDetail(val accountId: Long,
                                val languageCode: String,
                                val countryCode: String,
                                val timeZoneCode: String)
}
