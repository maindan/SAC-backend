package com.example.users.DTO.user;

import com.example.users.domain.user.Role;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record UserRequestDTO(
        Long id,
        String email,
        List<Role> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
