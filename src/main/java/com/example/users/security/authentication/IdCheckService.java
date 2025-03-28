package com.example.users.security.authentication;

import com.example.users.domain.user.User;
import com.example.users.exceptions.UnauthorizedException;
import com.example.users.repositories.UserRepository;
import com.example.users.security.userDetail.UserDetailImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class IdCheckService {

    @Autowired
    UserRepository userRepository;

    public void validateOwner(Long id) {
        Long authenticatedUserId = getAuthUserId();

        if(!authenticatedUserId.equals(id)) {
            throw new UnauthorizedException("User unauthorized");
        }
    }

    public Long getAuthUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByEmail(principal.toString())
                .map(User::getId)
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));
    }
}
