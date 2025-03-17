package com.example.users.DTO.equipament;

import java.util.Date;

public record UpdateEquipamentDTO(
        Long typeId,
        String model,
        String fabricator,
        Date buyDate,
        String voltage,
        String serialNumber
) {
}
