package com.example.springbootkotlinvirtualthread.application.member

import com.example.springbootkotlinvirtualthread.domain.member.MemberForResponse

fun interface MemberDetailsGetUseCase {

    fun getMemberDetails(command: MemberDetailsGetCommand.GetMemberDetails): MemberForResponse
}
