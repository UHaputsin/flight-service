package com.test.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiErrorMessage {

    public static final String INTERNAL_SERVER_EXCEPTION_MESSAGE = "Internal server error";
    public static final String JSON_PARSE_EXCEPTION_MESSAGE = "Json parse error, check input values";
    public static final String VALUE_MUST_BE_SPECIFIED_TEMPLATE = "%s must be specified";
    public static final String VALUE_MUST_BE_INTEGER_TEMPLATE = "%s must be integer";
}
