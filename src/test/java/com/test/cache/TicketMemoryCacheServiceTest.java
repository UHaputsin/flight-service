package com.test.cache;

import com.test.model.Ticket;
import com.test.repository.ticket.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketMemoryCacheServiceTest {

    @InjectMocks
    TicketMemoryCacheService ticketMemoryCacheService;

    @Mock
    TicketRepository ticketRepository;

    @Test
    public void when_FindTickets2Times_Expect_1CallFromDatasource() {
        Ticket ticket = new Ticket(1);

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        ticketMemoryCacheService.get(1);
        ticketMemoryCacheService.get(1);

        verify(ticketRepository, atMostOnce()).findById(1);
    }
}