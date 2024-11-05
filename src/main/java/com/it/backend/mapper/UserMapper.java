package com.it.backend.mapper;

import com.it.backend.dto.response.UserResponse;
import com.it.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", source = "originalPassword")
    @Mapping(target = "roleResponse", source = "user.role")
    UserResponse toUserResponse(User user, String originalPassword);
}
