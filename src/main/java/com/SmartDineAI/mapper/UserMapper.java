package com.SmartDineAI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.SmartDineAI.dto.user.CreateUserRequest;
import com.SmartDineAI.dto.user.UpdateUserRequest;
import com.SmartDineAI.dto.user.UserResponse;
import com.SmartDineAI.entity.User;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.WARN,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(CreateUserRequest request);

    @Mapping(target = "roleName", source = "role.name")
    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    // @Mapping(target = "username", ignore = true)
    // @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
