package com.example.users.controller;

import com.example.users.DTO.person.PersonRequestDTO;
import com.example.users.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<PersonRequestDTO> getAll() {
        return personService.getAll();
    }

    @GetMapping("/{id}")
    public PersonRequestDTO getById(@PathVariable long id) throws Exception {
        return personService.getById(id);
    }

    @GetMapping("/user/{id}")
    public PersonRequestDTO getByUser(@PathVariable long id) throws Exception {
        return personService.getByUserId(id);
    }
}
