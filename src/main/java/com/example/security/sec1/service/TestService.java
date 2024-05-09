package com.example.security.sec1.service;

import com.example.security.sec1.model.*;
import com.example.security.sec1.repositories.FeatureRepository;
import com.example.security.sec1.repositories.UserFeaturePermissionRepository;
import com.example.security.sec1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {
    private Logger log = LoggerFactory.getLogger(TestService.class);

    private final UserRepository userRepository;
    private final FeatureRepository featureRepository;
    private final UserFeaturePermissionRepository userFeaturePermissionRepository;

    @PostAuthorize("returnObject.email == authentication.name")
    public UserDTO callUser(Long id) {
        User user = userRepository.findById(id).get();
        return UserDTO
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public String updatePermission(FeaturePermissionDTO featurePermissionDTO) {
        return featurePermissionDTO.getAdd() ?
                addPermission(featurePermissionDTO) : revokePermission(featurePermissionDTO);
    }

    private String revokePermission(FeaturePermissionDTO featurePermissionDTO) {
        userFeaturePermissionRepository.deleteByFeatureAndPermissionAndUserId(
                featurePermissionDTO.getFeatureName(), featurePermissionDTO.getPermission(), featurePermissionDTO.getUserId());
        return "permission revoke successful";
    }

    private String addPermission(FeaturePermissionDTO featurePermissionDTO) {
        List<Object[]> featurePermission = userFeaturePermissionRepository.findFeaturePermissionThatNotExists(featurePermissionDTO.getUserId(),
                featurePermissionDTO.getFeatureName(), featurePermissionDTO.getPermission());

        if (ObjectUtils.isEmpty(featurePermission)) {
            throw new RuntimeException("permission not allowed");
        }

        Optional<User> optionalUser = userRepository.findById(featurePermissionDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.getUserFeaturePermissions().add(UserFeaturePermission.builder()
                    .id(new UserFeaturePermissionId())
                    .feature((Feature) featurePermission.get(0)[0])
                    .permission((Permission) featurePermission.get(0)[1])
                    .user(user)
                    .build());

            userRepository.save(user);
        } else {
            throw new RuntimeException("user not exist");
        }
        return "permission granted";
    }
}
