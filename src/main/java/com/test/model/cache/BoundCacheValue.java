package com.test.model.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class BoundCacheValue<T> {

    private Integer key;
    private T value;
    private BoundCacheValue<T> nextNode = null;
    private BoundCacheValue<T> prevNode = null;

    public void connectAsNext(BoundCacheValue<T> anotherValue) {
        this.nextNode = anotherValue;
        anotherValue.setPrevNode(this);
    }

    @Override
    public String toString() {
        if (prevNode == null && nextNode == null) return "{key=" + key + '}';
        if (prevNode != null && nextNode == null) return "{key=" + key + ", prevKey=" + prevNode.getKey() + '}';
        if (prevNode == null && nextNode != null) return "{key=" + key + ", nextKey=" + nextNode.getKey() + '}';
        return "{" +
                "key=" + key +
                ", prevKey=" + prevNode.getKey() +
                ", nextKey=" + nextNode.getKey() +
                '}';
    }
}
