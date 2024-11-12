package com.it.backend.mapper;

import com.it.backend.dto.response.RoleResponse;
import com.it.backend.entity.IntegrationRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IntegrationRoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Set<RoleResponse> toRoleResponses(Iterable<IntegrationRole> integrationRoles);

    RoleResponse toRoleResponse(IntegrationRole integrationRole);
}
