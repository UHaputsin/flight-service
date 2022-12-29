package com.test.repository.ticket;

import com.test.model.Ticket;
import com.test.repository.FindByIdRepository;

/*
 * Interface allows you to extend ticket repository actions
 * and hides FindByIdRepository realisation
 */
public interface TicketRepository extends FindByIdRepository<Ticket, Integer> {
}
