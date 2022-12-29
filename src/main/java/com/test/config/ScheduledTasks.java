package com.test.config;

import com.test.cache.TicketMemoryCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@Profile("!junit")
public class ScheduledTasks {

    private static final long FIXED_CACHE_CLEANUP_RATE = 1000L;

    private final TicketMemoryCacheService ticketMemoryCacheService;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public ScheduledTasks(TicketMemoryCacheService ticketMemoryCacheService,
                          ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.ticketMemoryCacheService = ticketMemoryCacheService;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @PostConstruct
    public void scheduleTasks() {
        // Schedule repeating task that remove expired values from the ticket cache
        threadPoolTaskScheduler.scheduleAtFixedRate(
                ticketMemoryCacheService::cleanExpiredValuesInCache,
                FIXED_CACHE_CLEANUP_RATE
        );
    }

}
