package com.example.auth_service.service;

import com.example.auth_service.entity.Role;
import com.example.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow();
    }
}
