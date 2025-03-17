package com.example.users.repositories;

import com.example.users.domain.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUser_Id(Long id);
}
