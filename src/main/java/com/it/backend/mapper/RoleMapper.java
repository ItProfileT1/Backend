package com.it.backend.mapper;

import com.it.backend.dto.response.RoleResponse;
import com.it.backend.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Set<RoleResponse> toRoleResponses(Set<Role> roles);

    RoleResponse toRoleResponse(Role role);
}
