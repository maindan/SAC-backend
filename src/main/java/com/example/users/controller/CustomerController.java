package com.example.users.controller;

import com.example.users.DTO.customer.CreateCustomerDTO;
import com.example.users.DTO.customer.CustomerRequestDTO;
import com.example.users.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable Long id) {
        List<CustomerRequestDTO> customers = customerService.getAllCustomersByUserId(id);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CustomerRequestDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CreateCustomerDTO customerDTO) {
        CustomerRequestDTO newCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(newCustomer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CreateCustomerDTO customerDTO) {
        CustomerRequestDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}
