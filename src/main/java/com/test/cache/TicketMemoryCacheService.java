package com.test.cache;

import com.test.model.Ticket;
import com.test.model.cache.CacheValue;
import com.test.repository.ticket.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
public class TicketMemoryCacheService extends AbstractMemoryCache<Integer, Ticket> {

    private static final Duration EXPIRATION_AFTER_WRITE_DURATION = Duration.of(30, ChronoUnit.SECONDS);
    private static final ConcurrentMap<Optional<Integer>, CacheValue<Ticket>> TICKET_CACHE =
            new ConcurrentHashMap<>(100);

    private final TicketRepository ticketRepository;

    public TicketMemoryCacheService(TicketRepository ticketRepository) {
        super(log, EXPIRATION_AFTER_WRITE_DURATION, TICKET_CACHE);
        this.ticketRepository = ticketRepository;
    }

    @Override
    protected Ticket getValueFromDatasource(Integer key) {
        return ticketRepository.findById(key).orElse(null);
    }
}
