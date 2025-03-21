package com.example.users.repositories;

import com.example.users.domain.serviceOrder.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceOrderReposity extends JpaRepository<ServiceOrder, Long> {
    List<ServiceOrder> findAllByCustomer_Id(Long customerId);
    List<ServiceOrder> findAllByEquipaments_Id(Long equipamentId);
}
