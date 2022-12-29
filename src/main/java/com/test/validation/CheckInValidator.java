package com.test.validation;

import com.test.controller.check_in.dto.CheckInRequestDto;
import com.test.exception.BadRequestException;
import lombok.experimental.UtilityClass;

import static com.test.exception.ApiErrorCode.NOT_VALID_INPUT;
import static com.test.exception.ApiErrorMessage.VALUE_MUST_BE_SPECIFIED_TEMPLATE;
import static org.springframework.util.StringUtils.hasText;

@UtilityClass
public class CheckInValidator {

    public static void validateCheckInAction(CheckInRequestDto requestDto) {
        if (requestDto.baggageId() == null)
            throw new BadRequestException(
                    String.format(VALUE_MUST_BE_SPECIFIED_TEMPLATE, "Baggage ID"),
                    NOT_VALID_INPUT
            );

        if (!hasText(requestDto.destinationId())) {
            throw new BadRequestException(
                    String.format(VALUE_MUST_BE_SPECIFIED_TEMPLATE, "Destination ID"),
                    NOT_VALID_INPUT
            );
        }
    }
}
