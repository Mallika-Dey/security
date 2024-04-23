package com.example.security.sec1.controller;

import com.example.security.sec1.model.AuthenticationDTO;
import com.example.security.sec1.model.User;
import com.example.security.sec1.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        log.info("user show data is called");
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationDTO authenticationDTO) {
        log.info("user {} attempt to login", authenticationDTO.getEmail());
        return authenticationService.login(authenticationDTO);
    }

    @GetMapping("/user")
    public void showUser() {
        log.info("user add data");
    }
}
