package com.example.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private Set<RoleDto> roles;
}
