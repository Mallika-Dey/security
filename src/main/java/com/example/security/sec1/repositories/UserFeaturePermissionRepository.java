package com.example.security.sec1.repositories;

import com.example.security.sec1.model.UserFeaturePermission;
import com.example.security.sec1.model.UserFeaturePermissionId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFeaturePermissionRepository extends JpaRepository<UserFeaturePermission, UserFeaturePermissionId> {
    @Query("select f, p from Feature f cross join Permission p left join UserFeaturePermission uf on " +
            "f.id = uf.id.featureId and p.id = uf.id.permissionId " +
            "and uf.id.userId= :userId where f.featureName= :featureName and " +
            "p.permissionName= :permissionName and (uf.id is null or uf.id.userId is null)")
    List<Object[]> findFeaturePermissionThatNotExists(Long userId, @Param("featureName") String featureName,
                                                      @Param("permissionName") String permissionName);

    @Modifying
    @Transactional
    @Query("delete from UserFeaturePermission uf where uf.feature.featureName= :featureName " +
            "and uf.permission.permissionName= :permissionName and uf.user.id= :userId")
    public void deleteByFeatureAndPermissionAndUserId(String featureName, String permissionName, Long userId);
}
