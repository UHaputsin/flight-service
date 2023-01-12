package com.test.cache;

import com.test.model.cache.BoundCacheValue;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractBoundMemoryCache<T> implements Cache<Integer, T> {

    private final ConcurrentMap<Integer, BoundCacheValue<T>> cache;
    private final int maxSize;

    private BoundCacheValue<T> HEAD_VALUE = null;
    private BoundCacheValue<T> TAIL_VALUE = null;

    public AbstractBoundMemoryCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new ConcurrentHashMap<>(maxSize);
    }

    public T get(Integer key) {
        if (key == null) return null;

        return Optional.ofNullable(cache.get(key))
                .map(BoundCacheValue::getValue)
                .orElse(null);
    }

    public void evict(Integer key) {
        if (key == null) return;

        synchronized (cache) {
            BoundCacheValue<T> cacheValue = cache.get(key);
            if (cacheValue == null) return;

            cache.remove(key);
            pullValue(cacheValue);
        }
    }

    public void put(Integer key, T value) {
        if (value == null) return;

        synchronized (cache) {
            BoundCacheValue<T> cacheValue = cache.get(key);
            if (cacheValue != null) return;

            if (cache.size() == maxSize) {
                cache.remove(TAIL_VALUE.getKey());
                pullValue(TAIL_VALUE);
            }

            BoundCacheValue<T> boundCacheValue = BoundCacheValue.<T>of()
                    .key(key)
                    .value(value)
                    .build();

            cache.put(key, boundCacheValue);
            pushValue(boundCacheValue);
        }
    }

    //Add value to start of double-linked list
    private void pushValue(BoundCacheValue<T> newValue) {
        if (newValue == null) return;

        //If cache is empty push HEAD_VALUE and TAIL_VALUE will be the same value
        //If cache is not empty HEAD_VALUE can not be null
        if (HEAD_VALUE == null) {
            HEAD_VALUE = newValue;
            TAIL_VALUE = newValue;
            return;
        }

        newValue.connectAsNext(HEAD_VALUE);
        HEAD_VALUE = newValue;
    }

    //Remove value from the double-linked list
    private void pullValue(BoundCacheValue<T> value) {
        if (value == null) return;
        BoundCacheValue<T> nextValue = value.getNextNode();
        BoundCacheValue<T> prevValue = value.getPrevNode();

        //Current value is 2
        //If previous and next values are not null
        //Remove value from the middle, reorganize references
        //1 <-> 2 <-> 3 <->   TO   1 <-> 3
        if (prevValue != null && nextValue != null) {
            prevValue.setNextNode(nextValue);
            nextValue.setPrevNode(prevValue);
            return;
        }

        //Current value is 2
        //If previous not null and next is null
        //Remove value from the end, reorganize references, update TAIL_VALUE
        //1 <-> 2   TO   1
        if (prevValue != null && nextValue == null) {
            prevValue.setNextNode(null);
            TAIL_VALUE = prevValue;
            return;
        }

        //Current value is 2
        //If previous is null and next not null
        //Remove value from the head, reorganize references, update HEAD_VALUE
        //2 <-> 3   TO   3
        if (prevValue == null && nextValue != null) {
            nextValue.setPrevNode(null);
            HEAD_VALUE = nextValue;
            return;
        }

        //Current value is 2
        //If previous and next values are null
        //Remove value, clear HEAD_VALUE and TAIL_VALUE
        //2   TO   (null)
        if (prevValue == null && nextValue == null) {
            HEAD_VALUE = null;
            TAIL_VALUE = null;
        }
    }

    public void printNodes() {
        if (HEAD_VALUE != null) {
            BoundCacheValue<T> node = HEAD_VALUE;
            do {
                System.out.println(node);
                node = node.getNextNode();
            } while (node != null);
        }
    }

    public void printCacheValues() {
        cache.values().forEach(System.out::println);
    }
}
