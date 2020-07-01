package vn.rin.blog.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.rin.blog.domain.entity.PermissionEntity;

import java.util.List;

/**
 * @author Rin
 */
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>, JpaSpecificationExecutor<PermissionEntity> {
    List<PermissionEntity> findAllByParentId(int parentId, Sort sort);

    @Query(value = "select count(role_id) from shiro_role_permission where permission_id=:permId", nativeQuery = true)
    int countUsed(@Param("permId") long permId);

    @Query("select coalesce(max(weight), 0) from PermissionEntity")
    int maxWeight();
}
