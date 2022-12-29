package com.test.repository;

import java.util.Optional;

public interface FindByIdRepository<T, ID> {
    Optional<T> findById(ID id);
}
