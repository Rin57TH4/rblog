package vn.rin.blog.service;

import vn.rin.blog.domain.entity.RoleEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rin
 */
public interface UserRoleService {

    List<Long> listRoleIds(long userId);

    List<RoleEntity> listRoles(long userId);

    Map<Long, List<RoleEntity>> findMapByUserIds(List<Long> userIds);

    void updateRole(long userId, Set<Long> roleIds);
}
