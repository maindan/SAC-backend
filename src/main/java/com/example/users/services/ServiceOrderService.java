package com.example.users.services;

import com.example.users.repositories.ServiceOrderReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderService {
    @Autowired
    private ServiceOrderReposity serviceOrderReposity;
}
