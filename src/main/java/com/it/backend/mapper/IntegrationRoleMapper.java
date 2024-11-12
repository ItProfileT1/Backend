package com.it.backend.mapper;

import com.it.backend.dto.response.RoleResponse;
import com.it.backend.entity.IntegrationRole;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IntegrationRoleMapper {
    Set<RoleResponse> toRoleResponses(Iterable<IntegrationRole> integrationRoles);
}
