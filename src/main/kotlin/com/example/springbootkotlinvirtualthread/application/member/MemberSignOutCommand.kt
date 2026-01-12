package com.example.springbootkotlinvirtualthread.application.member

class MemberSignOutCommand {

    data class SignOut(
        val memberId: Long,
        val accessToken: String,
        val deviceModelName: String,
    )
}
