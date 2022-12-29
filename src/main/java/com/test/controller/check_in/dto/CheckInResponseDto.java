package com.test.controller.check_in.dto;

/**
 * @param success represents the success of the check-in action
 * {@link com.test.controller.check_in.v1.CheckInController#checkIn(CheckInRequestDto)}
 */

public record CheckInResponseDto(Boolean success) {
}
