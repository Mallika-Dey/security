package com.example.security.sec1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String featureName;

    @OneToMany(mappedBy = "feature")
    private List<UserFeaturePermission> userFeaturePermissionList;
}
