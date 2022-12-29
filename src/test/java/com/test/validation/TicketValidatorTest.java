package com.test.validation;

import com.test.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.test.validation.TicketValidator.validateByIdAction;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class TicketValidatorTest {

    private static final String MOCK_INT_VALUE = "1";
    private static final String MOCK_NEGATIVE_INT_VALUE = "-1";
    private static final String MOCK_DOUBLE_VALUE  = "1.0";
    private static final String MOCK_STRING_VALUE = "value";

    @Test
    void when_IdIsNull_Expect_BadRequestException() {
        assertThrowsExactly(BadRequestException.class, () -> validateByIdAction(null));
    }

    @Test
    void when_IdIsCharSequenceValue_Expect_BadRequestException() {
        assertThrowsExactly(BadRequestException.class, () -> validateByIdAction(MOCK_STRING_VALUE));
    }

    @Test
    void when_IdIsDoubleValue_Expect_BadRequestException() {
        assertThrowsExactly(BadRequestException.class, () -> validateByIdAction(MOCK_DOUBLE_VALUE));
    }

    @Test
    void when_IdIsIntValue_Expect_Nothing() {
        assertDoesNotThrow(() -> validateByIdAction(MOCK_INT_VALUE));
    }

    @Test
    void when_IdIsNegativeIntValue_Expect_Nothing() {
        assertDoesNotThrow(() -> validateByIdAction(MOCK_NEGATIVE_INT_VALUE));
    }
}