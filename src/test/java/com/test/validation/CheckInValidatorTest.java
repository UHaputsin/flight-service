package com.test.validation;

import com.test.controller.check_in.dto.CheckInRequestDto;
import com.test.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.test.validation.CheckInValidator.validateCheckInAction;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CheckInValidatorTest {

    private static final int MOCK_INT_VALUE = 1;
    private static final String MOCK_STRING_VALUE = "value";

    @Test
    void when_BaggageIdIsNull_Expect_BadRequestException() {
        CheckInRequestDto dto = new CheckInRequestDto(null, null);
        assertThrowsExactly(BadRequestException.class, () -> validateCheckInAction(dto));
    }

    @Test
    void when_DestinationIdIsNull_Expect_BadRequestException() {
        CheckInRequestDto dto = new CheckInRequestDto(null, MOCK_INT_VALUE);
        assertThrowsExactly(BadRequestException.class, () -> validateCheckInAction(dto));
    }

    @Test
    void when_DestinationIdIsBlank_Expect_BadRequestException() {
        CheckInRequestDto dto = new CheckInRequestDto(EMPTY, MOCK_INT_VALUE);
        assertThrowsExactly(BadRequestException.class, () -> validateCheckInAction(dto));
    }

    @Test
    void when_DestinationIdAndBaggageIdAreFilled_Expect_Nothing() {
        CheckInRequestDto dto = new CheckInRequestDto(MOCK_STRING_VALUE, MOCK_INT_VALUE);
        assertDoesNotThrow(() -> validateCheckInAction(dto));
    }
}