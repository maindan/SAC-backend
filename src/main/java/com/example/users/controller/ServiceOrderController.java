package com.example.users.controller;

import com.example.users.DTO.serviceOrder.CreateServiceOrderDTO;
import com.example.users.DTO.serviceOrder.ServiceOrderDTO;
import com.example.users.DTO.serviceOrder.UpdateServiceOrderDTO;
import com.example.users.services.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-order")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ServiceOrderDTO serviceOrderDTO = serviceOrderService.getById(id);
        return ResponseEntity.ok(serviceOrderDTO);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByCustomerId(@PathVariable Long id) {
        List<ServiceOrderDTO> serviceOrderDTOList = serviceOrderService.getAllByCustomerId(id);
        return ResponseEntity.ok(serviceOrderDTOList);
    }

    @GetMapping("/equipament/{id}")
    public ResponseEntity<?> getAllByEquipmentId(@PathVariable Long id) {
        List<ServiceOrderDTO> serviceOrderDTOList = serviceOrderService.getAllByEquipamentId(id);
        return ResponseEntity.ok(serviceOrderDTOList);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateServiceOrderDTO serviceOrderDTO) {
        ServiceOrderDTO serviceOrder = serviceOrderService.createServiceOrder(serviceOrderDTO);
        return ResponseEntity.ok(serviceOrder);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateServiceOrderDTO serviceOrderDTO) {
        ServiceOrderDTO serviceOrder = serviceOrderService.updateServiceOrder(id, serviceOrderDTO);
        return ResponseEntity.ok(serviceOrder);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        serviceOrderService.deleteServiceOrder(id);
        return ResponseEntity.ok().build();
    }
}
