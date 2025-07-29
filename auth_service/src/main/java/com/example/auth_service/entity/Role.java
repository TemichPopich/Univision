package com.example.auth_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "ROLES")
public class Role {
    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "ROLE")
    private String role;
}
