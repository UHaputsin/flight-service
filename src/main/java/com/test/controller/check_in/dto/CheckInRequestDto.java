package com.test.controller.check_in.dto;

/**
 * @param destinationId represents the destination identifier request value
 * @param baggageId represents the baggage identifier request value
 * for {@link com.test.controller.check_in.v1.CheckInController#checkIn(CheckInRequestDto)}
 */

public record CheckInRequestDto(String destinationId, Integer baggageId) {
}
