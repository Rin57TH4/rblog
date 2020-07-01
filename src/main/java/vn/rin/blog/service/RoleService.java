package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.entity.PermissionEntity;
import vn.rin.blog.domain.entity.RoleEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rin
 */
public interface RoleService {

    Page<RoleEntity> paging(Pageable pageable, String name);

    List<RoleEntity> list();

    Map<Long, RoleEntity> findByIds(Set<Long> ids);

    RoleEntity get(long id);

    void update(RoleEntity r, Set<PermissionEntity> permissions);

    boolean delete(long id);

    void activate(long id, boolean active);

}
