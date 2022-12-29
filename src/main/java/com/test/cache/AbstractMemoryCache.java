package com.test.cache;

import com.test.config.ScheduledTasks;
import com.test.model.cache.CacheValue;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractMemoryCache<K, V> implements Cache<K, V> {

    private final Logger log;
    private final Duration expireAfterWrite;
    /**
     * ConcurrentMap does not allow to store null as key/value, even , only Optional.empty()
     * Cache key must be Optional to fix unacceptable null value in key,
     * CacheValue will exist always even if real value is null
     */
    private final ConcurrentMap<Optional<K>, CacheValue<V>> cache;

    public AbstractMemoryCache(Logger log,
                               Duration expireAfterWrite,
                               ConcurrentMap<Optional<K>, CacheValue<V>> cache) {
        this.log = log;
        this.expireAfterWrite = expireAfterWrite;
        this.cache = cache;
    }

    @Override
    public void put(K key, V value) {
        CacheValue<V> cacheValue = createCacheValue(value);
        try {
            Optional<K> optKey = defineKey(key);
            cache.put(optKey, cacheValue);
            log.debug("Successfully put value to cache, key {}", key);
        } catch (RuntimeException e) {
            log.error("Failed to put value in cache, key {}", key, e);
        }
    }

    /**
     * @return value in few steps:
     * 1. If value is not found in cache -> get value from datasource, put in cache and return value
     * 2. If value is found in cache but expired ->  get value from datasource, put in cache and return value
     * 3. If value is found in cache and not expired -> return value
     */
    @Override
    public V get(K key) {
        Optional<K> optKey = defineKey(key);

        CacheValue<V> cachedValue = cache.get(optKey);
        if (cachedValue == null) {
            log.debug("Value in not cached, key {}", key);
            return getValueFromDatasourceAndPutInCache(key);
        }

        boolean isExpired = isExpired(cachedValue.getWriteTime());
        if (isExpired) {
            log.debug("Value is expired, key {}", key);
            evict(key);
            return getValueFromDatasourceAndPutInCache(key);
        }

        log.debug("Return value from cache, key {}", key);
        return cachedValue.getValue();
    }

    @Override
    public void evict(K key) {
        try {
            cache.remove(Optional.of(key));
            log.debug("Evict value, key {}", key);
        } catch (NullPointerException ex) {
            log.error("can not remove null value key");
        }
    }

    protected boolean isExpired(Instant writeTime) {
        return writeTime.plus(expireAfterWrite).isBefore(Instant.now());
    }

    /**
     * Method for schedule cache cleaning
     * {@link ScheduledTasks} schedule config
     */
    public void cleanExpiredValuesInCache() {
        cache.entrySet().removeIf(e -> {
            boolean expired = isExpired(e.getValue().getWriteTime());
            if (expired) log.debug("Value is expired, key {}", e.getKey());
            return expired;
        });
    }

    /**
     * @return cache model with value object inside and time of writing to store in cache
     */
    private CacheValue<V> createCacheValue(V value) {
        return CacheValue.<V>of()
                .writeTime(Instant.now())
                .value(value)
                .build();
    }

    private V getValueFromDatasourceAndPutInCache(K key) {
        V valueFromDatasource = getValueFromDatasource(key);
        put(key, valueFromDatasource);
        return valueFromDatasource;
    }

    /**
     * @return Optional.of(key) if value not null and Optional.empty() if null
     * ConcurrentMap does not allow to store null and Optional.of(null) as key/value
     */
    private Optional<K> defineKey(K key) {
        return key != null ? Optional.of(key) : Optional.empty();
    }

    /**
     * @return value that must be fetched from datasource
     */
    protected abstract V getValueFromDatasource(K key);
}
