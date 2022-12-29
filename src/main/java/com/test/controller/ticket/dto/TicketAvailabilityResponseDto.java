package com.test.controller.ticket.dto;

/**
 * @param available represents the ticket existing in the database, response of
 * {@link com.test.controller.ticket.v1.TicketController#findTicketById(String)}
 */
public record TicketAvailabilityResponseDto(Boolean available) {
}
