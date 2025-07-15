package com.example.users.services;

import com.example.users.DTO.customer.CustomerRequestDTO;
import com.example.users.DTO.equipament.CreateEquipamentDTO;
import com.example.users.DTO.equipament.EquipamentRequestDTO;
import com.example.users.DTO.equipament.EquipamentTypeDTO;
import com.example.users.DTO.equipament.UpdateEquipamentDTO;
import com.example.users.domain.customer.Customer;
import com.example.users.domain.equipament.Equipament;
import com.example.users.domain.equipament.EquipamentType;
import com.example.users.exceptions.NotFoundException;
import com.example.users.repositories.CustomerRepository;
import com.example.users.repositories.EquipamentRepository;
import com.example.users.repositories.EquipamentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipamentService {
    @Autowired
    private EquipamentRepository equipamentRepository;
    @Autowired
    private EquipamentTypeRepository equipamentTypeRepository;
    @Autowired
    CustomerRepository customerRepository;

    public EquipamentRequestDTO getById(Long id) {
        Optional<Equipament> equipament = this.equipamentRepository.findById(id);
        if(equipament.isEmpty()) {
            throw new NotFoundException("Equipament not found");
        }
        return convertEquipament(equipament.get());
    }

    public List<EquipamentRequestDTO> getAllByCostumer(Long id) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Customer not found"));

        List<Equipament> equipamentsList = this.equipamentRepository.findAllByCustomerId(id).orElse(List.of());
        return convertEquipaments(equipamentsList);
    }

    public EquipamentRequestDTO createEquipament(CreateEquipamentDTO equipamentData) {
        Equipament equipament = new Equipament();
        EquipamentType type = equipamentTypeRepository.findById(equipamentData.typeId())
                .orElseThrow(() -> new NotFoundException("Equipament type not found"));
        Customer customer = customerRepository.findById(equipamentData.customerId())
                .orElseThrow(()-> new NotFoundException("Customer not found"));

        equipament.setType(type);
        equipament.setModel(equipamentData.model());
        Optional.ofNullable(equipamentData.buyDate())
                .ifPresent(equipament::setBuyDate);
        Optional.ofNullable(equipament.getFabricator()).ifPresent(equipament::setFabricator);
        Optional.ofNullable(equipament.getVoltage()).ifPresent(equipament::setVoltage);
        Optional.ofNullable(equipament.getSerialNumber()).ifPresent(equipament::setSerialNumber);
        equipament.setCustomer(customer);
        equipamentRepository.save(equipament);
        return convertEquipament(equipament);
    }

    public EquipamentRequestDTO updateEquipament(UpdateEquipamentDTO equipamentData, Long id) {
        Equipament equipament = equipamentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipament not found"));

        if(equipamentData.typeId() != null) {
            EquipamentType type = equipamentTypeRepository.findById(equipamentData.typeId())
                    .orElseThrow(() -> new NotFoundException("Equipament type not found"));
            equipament.setType(type);
        }

        Optional.ofNullable(equipamentData.model()).ifPresent(equipament::setModel);
        Optional.ofNullable(equipamentData.fabricator()).ifPresent(equipament::setFabricator);
        Optional.ofNullable(equipamentData.buyDate()).ifPresent(equipament::setBuyDate);
        Optional.ofNullable(equipamentData.voltage()).ifPresent(equipament::setVoltage);
        Optional.ofNullable(equipament.getSerialNumber()).ifPresent(equipament::setSerialNumber);

        return convertEquipament(equipamentRepository.save(equipament));
    }

    public void deleteEquipament(Long id) {
        Equipament equipament = equipamentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipament not found"));

        equipamentRepository.delete(equipament);
    }

    public List<EquipamentType> getEquipamentTypeList() {
        return equipamentTypeRepository.findAll();
    }

    public EquipamentType createEquipamentType(EquipamentTypeDTO typeDTO) {
        EquipamentType equipamentType = new EquipamentType();
        equipamentType.setTypeName(typeDTO.typeName());
        return equipamentTypeRepository.save(equipamentType);
    }

    private EquipamentRequestDTO convertEquipament(Equipament equipament) {
        return new EquipamentRequestDTO(
                equipament.getId(),
                equipament.getType().getTypeName(),
                equipament.getModel(),
                equipament.getFabricator(),
                equipament.getBuyDate().toInstant(),
                equipament.getVoltage(),
                equipament.getSerialNumber(),
                equipament.getCustomer().getId(),
                equipament.getCreatedAt(),
                equipament.getUpdatedAt()
            );
    }

    private List<EquipamentRequestDTO> convertEquipaments(List<Equipament> equipaments) {
        return equipaments.stream().map(this::convertEquipament).collect(Collectors.toList());
    }
}
