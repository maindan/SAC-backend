package com.example.users.DTO.person;

import com.example.users.DTO.user.UserRequestDTO;

public record PersonRequestDTO(
        Long id,
        String name,
        String phoneNumber,
        UserRequestDTO user
) {
}
