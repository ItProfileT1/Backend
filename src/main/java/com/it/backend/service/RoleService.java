package com.it.backend.service;

import com.it.backend.Role;
import com.it.backend.dto.NameAndDescriptionDto;
import com.it.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public void createRole(NameAndDescriptionDto dto){
        var role = new Role();
        role.setName(dto.name());
        role.setDescription(dto.description());
        roleRepository.save(role);
    }
}
