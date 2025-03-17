package com.example.users.security.authentication;

import com.example.users.exceptions.UnauthorizedException;
import com.example.users.security.userDetail.UserDetailImplementation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class IdCheckService {

    public void validateOwner(Long id) {
        Long authenticatedUserId = getAuthUserId();

        if(!authenticatedUserId.equals(id)) {
            throw new UnauthorizedException("User unauthorized");
        }
    }

    public Long getAuthUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetailImplementation userDetail) {
            return userDetail.getUser().getId();
        }
        throw new UnauthorizedException("User not authenticated");
    }
}
