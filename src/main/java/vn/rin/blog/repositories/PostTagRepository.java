package vn.rin.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.rin.blog.domain.entity.PostTagEntity;

import java.util.Set;

/**
 * @author Rin
 */
@Repository
public interface PostTagRepository extends JpaRepository<PostTagEntity, Long>, JpaSpecificationExecutor<PostTagEntity> {
    Page<PostTagEntity> findAllByTagId(Pageable pageable, long tagId);

    PostTagEntity findByPostIdAndTagId(long postId, long tagId);

    @Query("select tagId from PostTagEntity where postId = ?1")
    Set<Long> findTagIdByPostId(long postId);

    int deleteByPostId(long postId);
}
