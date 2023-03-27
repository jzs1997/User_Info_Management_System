package com.example.myproject.common;

/**
 * Meaning of returned error code
 */
public enum ErrorCode {

    PARAMS_ERROR(40000, "Request Parameters Error", ""),
    NOT_LOGIN(40100, "Fail to login", ""),
    NO_AUTH(40101, "No Authentication", ""),
    SUCCESS(0, "ok", ""),
    SYSTEM_ERROR(50000, "system error", ""),
    PARAMS_NULL_ERROR(40001, "Request data is NULL", "");


    private final int code;
    private final String message;
    private final String description;
    ErrorCode(int code, String message, String description) {
    this.code = code;
    this.message = message;
    this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
