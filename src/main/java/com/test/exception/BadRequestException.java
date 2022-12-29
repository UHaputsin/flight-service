package com.test.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BadRequestException extends RuntimeException {

    private String message;
    private ApiErrorCode code;
}
