package com.example.security.sec1.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasRole('ADMIN')")
    public String callUser() {
        return "user data sending........";
    }
}
