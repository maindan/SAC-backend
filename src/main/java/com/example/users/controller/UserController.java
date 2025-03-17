package com.example.users.controller;

import com.example.users.DTO.user.UserRequestDTO;
import com.example.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserRequestDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserRequestDTO getUserById(@PathVariable Long id) throws Exception {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) throws Exception {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
