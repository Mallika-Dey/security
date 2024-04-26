package com.example.security.sec1.service;

import com.example.security.sec1.auth.JWTUtils;
import com.example.security.sec1.controller.UserController;
import com.example.security.sec1.model.AuthenticationDTO;
import com.example.security.sec1.model.Permission;
import com.example.security.sec1.model.User;
import com.example.security.sec1.repositories.PermissionRepository;
import com.example.security.sec1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //for testing
    private final PermissionRepository permissionRepository;

    public String login(AuthenticationDTO authenticationDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getEmail(), authenticationDTO.getPassword()
            ));
            //check if the user exist

            return jwtUtils.generateToken(userRepository.findByEmail(
                    authenticationDTO.getEmail()).get());
        } catch (BadCredentialsException ex) {
            log.error("Bad credential exception {}", ex.getMessage());
            throw ex;
        }
    }

    public String register(User user) {
        if (!ObjectUtils.isEmpty(userRepository.findByEmail(user.getEmail()))) {
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

       /* check if permission exist
        HashSet<Permission> permissionList = new HashSet<>(permissionRepository.findAll());
        if (!permissionList.containsAll(user.getPermissions())) {
            throw new RuntimeException("Permission not granted");
        }*/
        List<Permission> permissionList = permissionRepository.findByRoleNameIn(
                user.getPermissions()
                        .stream()
                        .map(Permission::getRoleName)
                        .toList());
        if(user.getPermissions().size()!=permissionList.size()){
            throw new RuntimeException("Permission not granted");
        }

        user.setPermissions(permissionList);
        userRepository.save(user);

        return jwtUtils.generateToken(user);
    }
}
