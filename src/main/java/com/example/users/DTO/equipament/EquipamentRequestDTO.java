package com.example.users.DTO.equipament;

import java.time.Instant;
import java.time.LocalDateTime;

public record EquipamentRequestDTO(
        Long id,
        String typeName,
        String model,
        String fabricator,
        Instant buyDate,
        String voltage,
        String serialNumber,
        Long customerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
