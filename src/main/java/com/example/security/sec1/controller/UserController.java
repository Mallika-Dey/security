package com.example.security.sec1.controller;

import com.example.security.sec1.model.AuthenticationDTO;
import com.example.security.sec1.model.FeaturePermissionDTO;
import com.example.security.sec1.model.User;
import com.example.security.sec1.model.UserDTO;
import com.example.security.sec1.service.AuthenticationService;
import com.example.security.sec1.service.TestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final TestService testService;
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

    //it will be removed
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('F1READ')")
    public UserDTO showUser(@PathVariable Long id) {
        log.info("user add data");
        return testService.callUser(id);
    }

    @PostMapping("/permission")
    public String updatePermission(@RequestBody FeaturePermissionDTO featurePermissionDTO) {
        log.info("giving permission to the user");
        return testService.updatePermission(featurePermissionDTO);
    }
}
