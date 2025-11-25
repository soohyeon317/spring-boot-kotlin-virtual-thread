package com.example.springbootkotlinvirtualthread.configuration.annotation

import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FunctionExecutionBeforeLog

@Aspect
@Component
@Order(1)
class FunctionExecutionBeforeLogAspect {

    @Pointcut("@annotation(FunctionExecutionBeforeLog)")
    fun functionExecutionBeforeLogPointCut() = Unit

    @Before("functionExecutionBeforeLogPointCut()")
    fun functionExecutionBeforeLogAdvice(joinPoint: JoinPoint) {
        logger().info("${joinPoint.target}.${joinPoint.signature.name}(${joinPoint.args.joinToString(", ")})")
    }
}
