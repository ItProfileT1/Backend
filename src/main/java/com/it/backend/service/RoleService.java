package com.it.backend.service;

import com.it.backend.entity.Role;
import com.it.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getByName(String name){
        return roleRepository.getRoleByName(name);
    }
}
