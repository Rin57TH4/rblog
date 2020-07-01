package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.rin.blog.domain.entity.UserEntity;

import java.util.Collection;

/**
 * @author Rin
 */
public interface UserRepo extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    @Modifying
    @Query("update UserEntity set posts = posts + :increment where id = :id")
    int updatePosts(@Param("id") long id, @Param("increment") int increment);

    @Modifying
    @Query("update UserEntity set comments = comments + :increment where id in (:ids)")
    int updateComments(@Param("ids") Collection<Long> ids, @Param("increment") int increment);
}
