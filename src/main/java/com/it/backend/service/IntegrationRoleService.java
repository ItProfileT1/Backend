package com.it.backend.service;

import com.it.backend.entity.IntegrationRole;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.IntegrationRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegrationRoleService {
    private final IntegrationRoleRepository integrationRoleRepository;

    public Iterable<IntegrationRole> findAll() {
        return integrationRoleRepository.findAll();
    }

    public IntegrationRole findById(Long id) {
        return integrationRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("integration_role.not.found", id));
    }
}
