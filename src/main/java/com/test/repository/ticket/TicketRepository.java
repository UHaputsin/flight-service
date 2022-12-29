package com.test.repository.ticket;

import com.test.model.Ticket;
import com.test.repository.FindByIdRepository;

/*
 * Allows you to create different from FindById operations
 */
public interface TicketRepository extends FindByIdRepository<Ticket, Integer> {
}
