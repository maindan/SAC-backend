package com.example.users.services;

import com.example.users.DTO.person.PersonRequestDTO;
import com.example.users.DTO.user.UserRequestDTO;
import com.example.users.domain.user.Person;
import com.example.users.exceptions.NotFoundException;
import com.example.users.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<PersonRequestDTO> getAll() {
        List<Person> people = personRepository.findAll();
        return this.convertPersonList(people);
    }

    public PersonRequestDTO getByUserId(long id) {
        Optional<Person> person = personRepository.findByUser_Id(id);
        if (person.isEmpty()) {
            throw new NotFoundException("Person not found");
        }
        return convertPersonDTO(person.get());
    }

    public PersonRequestDTO getById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
            return convertPersonDTO(person);
    }

    public PersonRequestDTO update(PersonRequestDTO updatedPerson, Long id) throws Exception {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
        Optional.ofNullable(updatedPerson.name()).ifPresent(person::setName);
        Optional.ofNullable(updatedPerson.phoneNumber()).ifPresent(person::setPhoneNumber);

        person.setUpdatedAt(Instant.now());
        return convertPersonDTO(personRepository.save(person));
    }

    public void deleteById(Long id) throws Exception {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            this.personRepository.deleteById(id);
        }
        throw new Exception("Person not found");
    }

    public PersonRequestDTO convertPersonDTO(Person person) {
        return new PersonRequestDTO(person.getId(), person.getName(), person.getPhoneNumber(), new UserRequestDTO(
                person.getUser().getId(),
                person.getUser().getEmail(),
                person.getUser().getRoles(),
                person.getUser().getCreatedAt(),
                person.getUser().getUpdatedAt()
        ));
    }


    private List<PersonRequestDTO> convertPersonList(List<Person> people) {
        return people.stream().map(this::convertPersonDTO).collect(Collectors.toList());
    }


}
