package com.it.backend.repository;

import com.it.backend.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role getRoleByName(String name);
}
