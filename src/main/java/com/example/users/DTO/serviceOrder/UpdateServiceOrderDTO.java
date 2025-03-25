package com.example.users.DTO.serviceOrder;

import com.example.users.domain.equipament.Equipament;
import com.example.users.domain.serviceOrder.ServiceOrderStatus;
import com.example.users.domain.serviceOrder.ServiceOrderType;

import java.util.Date;
import java.util.List;

public record UpdateServiceOrderDTO(
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
        List<Equipament> equipaments,
        Long parentId
) {
}
