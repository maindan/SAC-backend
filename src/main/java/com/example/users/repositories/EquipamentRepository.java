package com.example.users.repositories;

import com.example.users.domain.equipament.Equipament;
import com.example.users.domain.equipament.EquipamentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipamentRepository extends JpaRepository<Equipament, Long> {
    Optional<List<Equipament>> findAllByCustomerId(Long customerId);
}
