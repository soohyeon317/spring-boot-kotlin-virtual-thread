package com.example.springbootkotlinvirtualthread.endpoint

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LBHealthCheckController {

    data class Health(val status: String)

    @GetMapping("/health")
    @ResponseBody
    suspend fun health(): ResponseEntity<Health> = ResponseEntity(Health("OK"), HttpStatus.OK)
}
