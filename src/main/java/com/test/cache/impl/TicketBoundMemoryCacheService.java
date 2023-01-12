package com.test.cache.impl;

import com.test.cache.AbstractBoundMemoryCache;
import com.test.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketBoundMemoryCacheService extends AbstractBoundMemoryCache<Ticket> {

    public TicketBoundMemoryCacheService() {
        super(5);
    }
}
