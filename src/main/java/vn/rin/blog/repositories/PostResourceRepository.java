package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.PostResourceEntity;

import java.util.Collection;
import java.util.List;

/**
 * @author Rin
 */
public interface PostResourceRepository extends JpaRepository<PostResourceEntity, Long>, JpaSpecificationExecutor<PostResourceEntity> {

    int deleteByPostId(long postId);

    int deleteByPostIdAndResourceIdIn(long postId, Collection<Long> resourceId);

    List<PostResourceEntity> findByResourceId(long resourceId);

    List<PostResourceEntity> findByPostId(long postId);

}
