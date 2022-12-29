package com.test.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.test.exception.ApiErrorCode.INTERNAL_SERVER_EXCEPTION;
import static com.test.exception.ApiErrorCode.JSON_PARSE_EXCEPTION;
import static com.test.exception.ApiErrorMessage.INTERNAL_SERVER_EXCEPTION_MESSAGE;
import static com.test.exception.ApiErrorMessage.JSON_PARSE_EXCEPTION_MESSAGE;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    /**
     * @param message represents message for user
     * @param code represents code for UI purposes
     */
    public record ApiError(String message, ApiErrorCode code) {
    }

    /*
    * Process unhandled exception
    * Must return mocked exception message and suppress stacktrace/technology sharing
    */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleUncaughtException(Exception ex) {
        log.error("", ex);
        return new ApiError(INTERNAL_SERVER_EXCEPTION_MESSAGE, INTERNAL_SERVER_EXCEPTION);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleJsonParseException(HttpMessageNotReadableException ignored) {
        return new ApiError(JSON_PARSE_EXCEPTION_MESSAGE, JSON_PARSE_EXCEPTION);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(BadRequestException ex) {
        return new ApiError(ex.getMessage(), ex.getCode());
    }
}
