package com.test.model.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * CacheValue class represents common memory caching object
 * @param <T> defines type of caching object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class CacheValue<T> {
    /**
     * writeTime field represents writing to cache time
     */
    private Instant writeTime;

    /**
     * value field represents caching value
     */
    private T value;

}
