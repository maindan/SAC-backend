package com.example.users.DTO.user;

import com.example.users.domain.user.Role;

import java.time.Instant;
import java.util.List;

public record UserRequestDTO(
        Long id,
        String email,
        List<Role> roles,
        Instant createdAt,
        Instant updatedAt
) {}
