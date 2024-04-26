package com.example.security.sec1.service;

import com.example.security.sec1.model.User;
import com.example.security.sec1.model.UserDTO;
import com.example.security.sec1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final UserRepository userRepository;

    @PostAuthorize("returnObject.email == authentication.name")
    public UserDTO callUser(Long id) {
        User user = userRepository.findById(id).get();
        return UserDTO
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
