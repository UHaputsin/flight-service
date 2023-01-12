package com.test.cache;

import com.test.cache.impl.TicketBoundMemoryCacheService;
import com.test.model.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

class TicketBoundMemoryCacheServiceTest {

    TicketBoundMemoryCacheService ticketBoundMemoryCacheService = new TicketBoundMemoryCacheService();

    //Not a test, just to verify manually
    @Test
    public void when_FindTickets2Times_Expect_1CallFromDatasource() {

        List<Ticket> tickets = IntStream.range(7, 93).mapToObj(Ticket::new).toList();
        for (Ticket t : tickets) {
            ticketBoundMemoryCacheService.put(t.getId(), t);
        }

        System.out.println("Node values:");
        ticketBoundMemoryCacheService.printNodes();

        System.out.println("\nCache values:");
        ticketBoundMemoryCacheService.printCacheValues();
    }
}