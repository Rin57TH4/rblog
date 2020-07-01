package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.data.PermissionTree;
import vn.rin.blog.domain.entity.PermissionEntity;

import java.util.List;

/**
 * @author Rin
 */
public interface PermissionService {
    Page<PermissionEntity> paging(Pageable pageable, String name);

    List<PermissionTree> tree();

    List<PermissionTree> tree(int parentId);

    List<PermissionEntity> list();

    PermissionEntity get(long id);

}
