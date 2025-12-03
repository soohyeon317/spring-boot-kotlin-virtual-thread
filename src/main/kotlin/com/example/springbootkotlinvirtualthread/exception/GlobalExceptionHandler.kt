package com.example.springbootkotlinvirtualthread.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

@RestControllerAdvice
class GlobalExceptionHandler {

    data class ErrorResponse(
        val code: ErrorCode,
        val message: String
    )

    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(ex: UnAuthorizedException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ex.code,
            message = ex.message ?: ex.code.message
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @ExceptionHandler(AccessForbiddenException::class)
    fun handleAccessForbiddenException(ex: AccessForbiddenException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ex.code,
            message = ex.message ?: ex.code.message
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ex.code,
            message = ex.message ?: ex.code.message
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ex.code,
            message = ex.message ?: ex.code.message
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: ConflictException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ex.code,
            message = ex.message ?: ex.code.message
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }

    @ExceptionHandler(InternalServerException::class)
    fun handleInternalServerException(ex: InternalServerException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ex.code,
            message = ex.message ?: ex.code.message
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessage = ex.bindingResult.fieldErrors.firstOrNull()?.let {
            "${it.field}: ${it.defaultMessage}"
        } ?: ErrorCode.INPUT_INVALID.message

        val response = ErrorResponse(
            code = ErrorCode.INPUT_INVALID,
            message = errorMessage
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleHandlerMethodValidationException(ex: HandlerMethodValidationException): ResponseEntity<ErrorResponse> {
        val errorMessage = ex.allErrors.firstOrNull()?.let { error ->
            (error.codes?.get(1) ?: error.codes?.firstOrNull()) + ": " + error.defaultMessage
        } ?: ErrorCode.INPUT_INVALID.message

        val response = ErrorResponse(
            code = ErrorCode.INPUT_INVALID,
            message = errorMessage
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(ex: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ErrorCode.INPUT_INVALID,
            message = "Required parameter '${ex.parameterName}' is missing"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(MethodExecutionTimeoutException::class)
    fun handleMethodExecutionTimeoutException(ex: MethodExecutionTimeoutException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = ex.code,
            message = ex.message ?: ex.code.message
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        ex.printStackTrace()
        val response = ErrorResponse(
            code = ErrorCode.INTERNAL_SERVER_ERROR,
            message = ex.message ?: ErrorCode.INTERNAL_SERVER_ERROR.message
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}