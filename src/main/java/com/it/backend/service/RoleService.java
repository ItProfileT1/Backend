package com.it.backend.service;

import com.it.backend.dto.response.RoleResponse;
import com.it.backend.entity.Role;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.RoleMapper;
import com.it.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("role.not.found", id));
    }

    public Set<RoleResponse> findAll() {
        Set<Role> roleSet = new HashSet<>();
        roleRepository.findAll().forEach(roleSet::add);
        return RoleMapper.INSTANCE.toRoleResponses(roleSet);
    }

    public RoleResponse roleToRoleResponse(Role role) {
        return RoleMapper.INSTANCE.toRoleResponse(role);
    }
}
