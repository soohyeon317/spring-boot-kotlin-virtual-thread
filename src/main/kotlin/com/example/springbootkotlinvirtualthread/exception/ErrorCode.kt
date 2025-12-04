package com.example.springbootkotlinvirtualthread.exception

enum class ErrorCode(val message: String) {

    // INPUT
    INPUT_INVALID("Input is invalid."),
    DATE_FORMAT_INVALID("Date format is invalid."),

    // HEADER
    DEVICE_MODEL_NAME_HEADER_REQUIRED("Device model name header is required."),
    APP_OS_HEADER_REQUIRED("App OS header is required."),
    APP_VERSION_HEADER_REQUIRED("App version header is required."),
    APP_OS_HEADER_INVALID("App OS header is invalid."),

    // AUTHENTICATION
    UNAUTHORIZED("Account is unauthorized."),
    ACCESS_FORBIDDEN("Access is forbidden."),
    ACCESS_TOKEN_EXPIRED("AccessToken is expired."),
    REFRESH_TOKEN_EXPIRED("RefreshToken is expired."),
    ACCESS_TOKEN_INVALID("AccessToken is invalid."),
    REFRESH_TOKEN_INVALID("RefreshToken is invalid."),

    // ACCOUNT
    MY_ACCOUNT_NOT_FOUND("My account is not found."),

    // AUTH_TOKEN
    ACCESS_TOKEN_NOT_FOUND("AccessToken is not found."),
    REFRESH_TOKEN_NOT_MATCHED("RefreshToken is not matched."),
    AUTH_TOKEN_OF_MY_ACCOUNT_NOT_FOUND("My account's authToken is not found."),

    // INTERNAL SERVER ERROR
    DISTRIBUTED_LOCK_ACQUISITION_FAILURE("Fail to acquire the lock."),
    METHOD_EXECUTION_TIMEOUT("Method execution timeout occurred."),
    INTERNAL_SERVER_ERROR("Internal server error occurred.");
}
