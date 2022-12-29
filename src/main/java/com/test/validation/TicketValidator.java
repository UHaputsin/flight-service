package com.test.validation;

import com.test.exception.BadRequestException;
import lombok.experimental.UtilityClass;

import static com.test.exception.ApiErrorCode.NOT_VALID_INPUT;
import static com.test.exception.ApiErrorMessage.VALUE_MUST_BE_INTEGER_TEMPLATE;
import static com.test.exception.ApiErrorMessage.VALUE_MUST_BE_SPECIFIED_TEMPLATE;

@UtilityClass
public class TicketValidator {

    public static void validateByIdAction(String id) {
        if (id == null)
            throw new BadRequestException(
                    String.format(VALUE_MUST_BE_SPECIFIED_TEMPLATE,"Id"),
                    NOT_VALID_INPUT);

        if (!isInteger(id))
            throw new BadRequestException(
                    String.format(VALUE_MUST_BE_INTEGER_TEMPLATE,"Id"),
                    NOT_VALID_INPUT);
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
