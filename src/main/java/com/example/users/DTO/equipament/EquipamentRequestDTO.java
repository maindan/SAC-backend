package com.example.users.DTO.equipament;

import java.time.Instant;

public record EquipamentRequestDTO(
        Long id,
        String typeName,
        String model,
        String fabricator,
        Instant buyDate,
        String voltage,
        String serialNumber,
        Long customerId,
        Instant createdAt,
        Instant updatedAt
) {
}
