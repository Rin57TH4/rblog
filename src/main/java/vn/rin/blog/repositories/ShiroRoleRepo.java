package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.RoleEntity;

import java.util.List;

/**
 * @author Rin
 */
public interface ShiroRoleRepo extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {
    List<RoleEntity> findAllByStatus(int status);
}
