package vn.rin.blog.service;

import vn.rin.blog.domain.entity.PermissionEntity;
import vn.rin.blog.domain.entity.RolePermissionEntity;

import java.util.List;
import java.util.Set;

/**
 * @author Rin
 */
public interface RolePermissionService {
    List<PermissionEntity> findPermissions(long roleId);

    void deleteByRoleId(long roleId);

    void add(Set<RolePermissionEntity> rolePermissions);

}
