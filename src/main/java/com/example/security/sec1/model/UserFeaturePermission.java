package com.example.security.sec1.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserFeaturePermission {
    @EmbeddedId
    private UserFeaturePermissionId id;

    @ManyToOne
    @MapsId("featureId")
    @JoinColumn(name="feature_id")
    private Feature feature;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name="permission_id")
    private Permission permission;

    @ManyToOne
    @MapsId("userId")
    private User user;
}
