package com.example.auth_service.mapper;

import com.example.auth_service.dto.UserDto;
import com.example.auth_service.entity.User;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserDto toDto(User user);

    @SuppressWarnings("unused")
    Set<UserDto> toDto(Set<User> user);
}