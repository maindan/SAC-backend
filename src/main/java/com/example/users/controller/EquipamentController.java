package com.example.users.controller;

import com.example.users.DTO.equipament.CreateEquipamentDTO;
import com.example.users.DTO.equipament.EquipamentRequestDTO;
import com.example.users.DTO.equipament.EquipamentTypeDTO;
import com.example.users.DTO.equipament.UpdateEquipamentDTO;
import com.example.users.domain.equipament.EquipamentType;
import com.example.users.services.EquipamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipament")
public class EquipamentController {
    @Autowired
    private EquipamentService equipamentService;

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentRequestDTO> getById(@PathVariable Long id) {
        EquipamentRequestDTO equipament = equipamentService.getById(id);
        return ResponseEntity.ok(equipament);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<EquipamentRequestDTO>> getAllByCustomerId(@PathVariable Long id) {
        List<EquipamentRequestDTO> equipaments = equipamentService.getAllByCostumer(id);
        return ResponseEntity.ok(equipaments);
    }

    @PostMapping
    public ResponseEntity<EquipamentRequestDTO> create(@RequestBody CreateEquipamentDTO equipamentData) {
        EquipamentRequestDTO equipament = equipamentService.createEquipament(equipamentData);
        return ResponseEntity.ok(equipament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentRequestDTO> update(@PathVariable Long id, @RequestBody UpdateEquipamentDTO equipamentData) {
        EquipamentRequestDTO equipament = equipamentService.updateEquipament(equipamentData, id);
        return ResponseEntity.ok(equipament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        equipamentService.deleteEquipament(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<EquipamentType>> getAllTypes() {
        List<EquipamentType> typeList = equipamentService.getEquipamentTypeList();
        return ResponseEntity.ok(typeList);
    }

    @PostMapping("/types")
    public ResponseEntity<EquipamentType> createType(@RequestBody EquipamentTypeDTO typeData) {
        EquipamentType type = equipamentService.createEquipamentType(typeData);
        return ResponseEntity.ok(type);
    }
}
