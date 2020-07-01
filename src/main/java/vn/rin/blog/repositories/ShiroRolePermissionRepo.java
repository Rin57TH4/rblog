package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.RolePermissionEntity;

import java.util.List;

/**
 * @author Rin
 */
public interface ShiroRolePermissionRepo extends JpaRepository<RolePermissionEntity, Long>, JpaSpecificationExecutor<RolePermissionEntity> {

    int deleteByRoleId(long roleId);
    List<RolePermissionEntity> findAllByRoleId(long roleId);
}
