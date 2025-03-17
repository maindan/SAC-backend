package com.example.users.DTO.user;

import java.util.List;

public record UserUpdateDTO(
        String email,
        String role,
        String name,
        String phoneNumber
) {}