package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.UserRoleEntity;

import java.util.Collection;
import java.util.List;

/**
 * @author Rin
 */
public interface ShiroUserRoleRepo extends JpaRepository<UserRoleEntity, Long>, JpaSpecificationExecutor<UserRoleEntity> {
    List<UserRoleEntity> findAllByUserId(long userId);

    List<UserRoleEntity> findAllByUserIdIn(Collection<Long> userId);

    List<UserRoleEntity> findAllByRoleId(long roleId);

    int deleteByUserId(long userId);

}
