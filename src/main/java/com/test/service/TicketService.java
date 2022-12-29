package com.test.service;

import com.test.cache.TicketMemoryCacheService;
import com.test.controller.ticket.dto.TicketAvailabilityResponseDto;
import com.test.model.Ticket;
import com.test.repository.ticket.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.test.validation.TicketValidator.validateByIdAction;

@Slf4j
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMemoryCacheService ticketMemoryCacheService;

    public TicketService(TicketRepository ticketRepository,
                         TicketMemoryCacheService ticketMemoryCacheService) {
        this.ticketRepository = ticketRepository;
        this.ticketMemoryCacheService = ticketMemoryCacheService;
    }

    /**
     * @param id represents ticket id value
     * @return value fetched from datasource without caching
     */
    public TicketAvailabilityResponseDto existById(String id) {
        validateByIdAction(id);

        int intId = Integer.parseInt(id);
        Optional<Ticket> optTicket = ticketRepository.findById(intId);

        return new TicketAvailabilityResponseDto(optTicket.isPresent());
    }

    /**
     * @param id represents ticket id value
     * @return value fetched from datasource with caching by the next scheme:
     * get from cache and return value ↓
     * if not exists in cache ↓
     * get from datasource and put in cache and return value
     */
    public TicketAvailabilityResponseDto existByIdCached(String id) {
        validateByIdAction(id);

        int ticketId = Integer.parseInt(id);
        Ticket ticket = ticketMemoryCacheService.get(ticketId);
        boolean ticketExists = ticket != null;

        return new TicketAvailabilityResponseDto(ticketExists);
    }
}
