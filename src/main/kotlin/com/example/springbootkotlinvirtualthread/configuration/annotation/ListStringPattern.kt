package com.vespexx.signal.serviceapi.configuration.annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ListStringPatternValidator::class])
annotation class ListStringPattern(
    val message: String,
    val regexp: String,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = [],
)
