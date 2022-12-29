package com.test.repository.ticket;

import com.test.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toConcurrentMap;

@Repository
public class TicketFakeRepositoryImpl implements TicketRepository {
    /*
     * Map TICKET_DATABASE contains 100 tickets values with id value in range [0, 100)
     */
    private static final Map<Integer, Ticket> TICKET_DATABASE =
            IntStream.range(0, 100)
                    .mapToObj(Ticket::new)
                    .collect(toConcurrentMap(Ticket::getId, Function.identity()));

    @Override
    public Optional<Ticket> findById(Integer id) {
        return Optional.ofNullable(TICKET_DATABASE.get(id));
    }
}
