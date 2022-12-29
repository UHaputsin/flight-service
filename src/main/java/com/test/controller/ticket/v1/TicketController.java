package com.test.controller.ticket.v1;

import com.test.controller.ticket.dto.TicketAvailabilityResponseDto;
import com.test.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TicketController.TICKET_CONTROLLER_MAPPING_V1)
public class TicketController {

    public static final String TICKET_CONTROLLER_MAPPING_V1 = "/api/v1/tickets";

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * @param id must be the integer value
     * @return boolean value true if exists or false if not
     */
    @GetMapping("/{id}")
    public TicketAvailabilityResponseDto findTicketById(@PathVariable String id) {
        return ticketService.existById(id);
    }
}
