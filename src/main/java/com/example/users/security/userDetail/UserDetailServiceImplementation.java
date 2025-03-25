package com.example.users.security.userDetail;

import com.example.users.domain.user.Person;
import com.example.users.domain.user.User;
import com.example.users.repositories.PersonRepository;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        Person person = personRepository.findByUser_Id(user.getId()).orElseThrow(() -> new RuntimeException("Person not found"));
        return new UserDetailImplementation(user, person);
    }
}
