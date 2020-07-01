package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.rin.blog.domain.entity.TagEntity;

import java.util.Collection;

/**
 * @author Rin
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long>, JpaSpecificationExecutor<TagEntity> {
    TagEntity findByName(String name);

    @Modifying
    @Query("update TagEntity set posts = posts - 1 where id in (:ids) and posts > 0")
    int decrementPosts(@Param("ids") Collection<Long> ids);
}
