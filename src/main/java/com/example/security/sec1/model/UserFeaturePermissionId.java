package com.example.security.sec1.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserFeaturePermissionId implements Serializable {
    @Column(name = "feature_id")
    private Long featureId;

    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "user_id")
    private Long userId;
}
