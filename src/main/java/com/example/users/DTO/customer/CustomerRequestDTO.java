package com.example.users.DTO.customer;

import java.time.Instant;

public record CustomerRequestDTO(
        Long id,
        String name,
        String phoneNumber,
        String registerNumber,
        Boolean typeBusiness,
        String address,
        Long userId,
        Instant createdAt,
        Instant updatedAt
) {
}
