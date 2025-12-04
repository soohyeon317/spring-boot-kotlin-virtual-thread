package com.example.springbootkotlinvirtualthread

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks
import java.util.*

@SpringBootApplication
@ConfigurationPropertiesScan
class SpringBootKotlinVirtualThreadApplication

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<SpringBootKotlinVirtualThreadApplication>(*args)
    Hooks.enableAutomaticContextPropagation() // 로그에 MDC 요청 추적 ID 출력을 위해 활성화. ThreadLocal 값이 자동으로 Context에 전파됨.
}
