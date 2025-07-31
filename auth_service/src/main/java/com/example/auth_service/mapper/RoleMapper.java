package com.example.auth_service.mapper;

import com.example.auth_service.dto.RoleDto;
import com.example.auth_service.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    @SuppressWarnings("unused")
    List<RoleDto> toDtoList(List<Role> roles);
}
