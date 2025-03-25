package com.example.users.DTO.serviceOrder;

import com.example.users.DTO.equipament.EquipamentRequestDTO;
import com.example.users.domain.serviceOrder.ServiceOrderStatus;
import com.example.users.domain.serviceOrder.ServiceOrderType;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public record ServiceOrderDTO(
        Long id,
        Long customerId,
        Date entryDate,
        Date budgetDate,
        Float budgetValue,
        Date warrantyClaimDate,
        Date warrantyApprovalDate,
        Date removalDate,
        ServiceOrderType serviceOrderType,
        ServiceOrderStatus serviceOrderStatus,
        String fiscalNumber,
        String observation,
        List<EquipamentRequestDTO> equipaments,
        Long parentId,
        Long userId,
        Instant createdAt,
        Instant updatedAt
) {
}
