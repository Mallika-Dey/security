package com.example.security.sec1.repositories;

import com.example.security.sec1.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByRoleNameIn(List<String> roleNames);
}
