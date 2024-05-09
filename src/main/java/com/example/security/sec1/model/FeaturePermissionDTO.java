package com.example.security.sec1.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeaturePermissionDTO {
    private Boolean add;

    private Long userId;
    @NotEmpty(message = "Feature name is required")
    private String featureName;
    @NotEmpty(message = "Permission is required")
    private String permission;
}
