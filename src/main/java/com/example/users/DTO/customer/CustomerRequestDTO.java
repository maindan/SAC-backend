package com.example.users.DTO.customer;

import java.time.Instant;
import java.time.LocalDateTime;

public record CustomerRequestDTO(
        Long id,
        String name,
        String phoneNumber,
        String registerNumber,
        Boolean typeBusiness,
        String address,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
