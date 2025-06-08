package com.gcp25.common.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.gcp25.common.core.constant.CommonErrorConstant.*;


@Getter
public enum CommonErrorMessage {

    FIELD_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, FIELD_VALIDATION_ERROR_CODE, FIELD_VALIDATION_ERROR_DESC),
    METHOD_NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED, METHOD_NOT_ALLOWED_ERROR_CODE,
            METHOD_NOT_ALLOWED_ERROR_DESC),
    API_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, API_EXECUTION_ERROR_CODE, API_EXECUTION_ERROR_DESC),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_DESC),
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, FORBIDDEN_ERROR_CODE, FORBIDDEN_ERROR_DESC);

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;


    CommonErrorMessage(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
