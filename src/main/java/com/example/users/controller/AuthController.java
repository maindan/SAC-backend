package com.example.users.controller;

import com.example.users.DTO.JWT.RecoveryJwtTokenDTO;
import com.example.users.DTO.user.LoginUserDTO;
import com.example.users.DTO.user.UserCreateDTO;
import com.example.users.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> authUser(@RequestBody LoginUserDTO loginUser) {
        RecoveryJwtTokenDTO token = userService.authUser(loginUser);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO data) throws Exception {
        userService.createUser(data);
        return ResponseEntity.ok().build();
    }
}
