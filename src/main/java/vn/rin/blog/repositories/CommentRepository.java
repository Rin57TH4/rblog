package vn.rin.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.CommentEntity;

import java.util.Collection;
import java.util.List;

/**
 * @author Rin
 */
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, JpaSpecificationExecutor<CommentEntity> {
    Page<CommentEntity> findAll(Pageable pageable);

    Page<CommentEntity> findAllByPostId(Pageable pageable, long postId);

    Page<CommentEntity> findAllByAuthorId(Pageable pageable, long authorId);

    List<CommentEntity> removeByIdIn(Collection<Long> ids);

    List<CommentEntity> removeByPostId(long postId);

    long countByAuthorIdAndPostId(long authorId, long postId);
}
