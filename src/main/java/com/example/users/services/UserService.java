package com.example.users.services;

import com.example.users.DTO.JWT.RecoveryJwtTokenDTO;
import com.example.users.DTO.person.PersonRequestDTO;
import com.example.users.DTO.user.LoginUserDTO;
import com.example.users.DTO.user.UserCreateDTO;
import com.example.users.DTO.user.UserRequestDTO;
import com.example.users.DTO.user.UserUpdateDTO;
import com.example.users.domain.user.Person;
import com.example.users.domain.user.Role;
import com.example.users.domain.user.User;
import com.example.users.exceptions.NotFoundException;
import com.example.users.repositories.PersonRepository;
import com.example.users.repositories.RoleRepository;
import com.example.users.repositories.UserRepository;
import com.example.users.security.authentication.JwtTokenService;
import com.example.users.security.config.SecurityConfiguration;
import com.example.users.security.userDetail.UserDetailImplementation;
import com.example.users.utils.RoleName;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PersonService personService;

    public List<UserRequestDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return this.convertUserList(users);
    }

    public RecoveryJwtTokenDTO authUser(LoginUserDTO data) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailImplementation userDetails = (UserDetailImplementation) authentication.getPrincipal();
        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    @Transactional
    public void createUser(UserCreateDTO userData) throws Exception {
        try {
            User user = new User();
            user.setEmail(userData.email());
            user.setPassword(securityConfiguration.passwordEncoder().encode(userData.password()));
            List<Role> roles = userData.roles().stream()
                    .map(roleName -> {
                        try {
                            Role role = roleRepository.findByName(RoleName.valueOf(roleName.toUpperCase()))
                                    .orElseGet(() -> {
                                        Role newRole = new Role(RoleName.valueOf(roleName.toUpperCase()));
                                        roleRepository.save(newRole);
                                        return newRole;
                                    });
                            return role;
                        } catch (IllegalArgumentException e) {
                            throw new RuntimeException("Invalid role name: " + roleName);
                        }
                    })
                    .collect(Collectors.toList());

            user.setRoles(roles);
            user = userRepository.save(user);

            Person person = new Person();
            person.setName(userData.name());
            person.setPhoneNumber(userData.phoneNumber());
            person.setUser(user);
            person = personRepository.save(person);
        } catch (Exception e) {
            throw new Exception("Erro ao criar usuário " + e.getMessage());
        }
    }

    public UserRequestDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return convertUserDTO(user);
    }

    @Transactional
    public PersonRequestDTO updateUser(UserUpdateDTO userData, Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        Person person = personRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("Person not found"));

        Optional.ofNullable(userData.email()).ifPresent(user::setEmail);
        if (userData.role() != null && !userData.role().isEmpty()) {
            List<Role> roles = Arrays.stream(userData.role().split(","))
                            .map(this::findRole)
                            .collect(Collectors.toList());
            user.setRoles(roles);
        }

        Optional.ofNullable(userData.name()).ifPresent(person::setName);
        Optional.ofNullable(userData.phoneNumber()).ifPresent(person::setPhoneNumber);

        user.setUpdatedAt(Instant.now());
        person.setUpdatedAt(Instant.now());

        userRepository.save(user);
        personRepository.save(person);

        return personService.convertPersonDTO(person);
    }

    private Role findRole(String role) {
       return roleRepository.findByName(RoleName.valueOf(role.toUpperCase()))
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }

    public void deleteUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new Exception("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserRequestDTO convertUserDTO(User user) {
        return new UserRequestDTO(
                user.getId(),
                user.getEmail(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    private List<UserRequestDTO> convertUserList(List<User> users) {
        return users.stream().map(this::convertUserDTO).collect(Collectors.toList());
    }
}
