package com.example.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleDto {
    private UUID id;
    private String name;
}
