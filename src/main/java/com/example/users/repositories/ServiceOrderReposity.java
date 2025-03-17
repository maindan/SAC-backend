package com.example.users.repositories;

import com.example.users.domain.serviceOrder.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderReposity extends JpaRepository<ServiceOrder, Long> {
}
