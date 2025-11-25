package com.vespexx.signal.serviceapi.configuration.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ListStringPatternValidator : ConstraintValidator<ListStringPattern, List<String>> {
    lateinit var regexp: String

    override fun initialize(requiredIfChecked: ListStringPattern) {
        this.regexp = requiredIfChecked.regexp
    }

    override fun isValid(value: List<String>?, context: ConstraintValidatorContext): Boolean =
        value.isNullOrEmpty() || value.all { it.matches(Regex(this.regexp)) }
}
