package com.gcp.agent25.common.core.exception;

import org.springframework.http.HttpStatusCode;

public class ApiExecutionException extends RuntimeException {
    private final HttpStatusCode httpStatusCode;
    private final String errorMessage;

    public ApiExecutionException(HttpStatusCode httpStatusCode, String errorMessage) {
        this.httpStatusCode = httpStatusCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
