package com.it.backend.repository;

import com.it.backend.entity.ApiClient;
import org.springframework.data.repository.CrudRepository;

public interface ApiClientRepository extends CrudRepository<ApiClient, Long> {
    boolean existsByToken(String token);
}
