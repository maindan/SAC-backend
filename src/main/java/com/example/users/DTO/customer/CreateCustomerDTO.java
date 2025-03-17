package com.example.users.DTO.customer;

public record CreateCustomerDTO(
        String name,
        String phoneNumber,
        String registerNumber,
        Boolean typeBusiness,
        String address,
        Long userId
) {
}
