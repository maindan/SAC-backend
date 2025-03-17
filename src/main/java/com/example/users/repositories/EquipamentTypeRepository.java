package com.example.users.repositories;


import com.example.users.domain.equipament.EquipamentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentTypeRepository extends JpaRepository<EquipamentType, Long> {
}
