package com.test.service;

import com.test.controller.ticket.dto.TicketAvailabilityResponseDto;
import com.test.repository.ticket.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.test.validation.TicketValidator.validateByIdAction;

@Slf4j
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketAvailabilityResponseDto existById(String id) {
        validateByIdAction(id);

        int intId = Integer.parseInt(id);
        boolean ticketExists = ticketRepository.findById(intId).isPresent();

        return new TicketAvailabilityResponseDto(ticketExists);
    }
}
