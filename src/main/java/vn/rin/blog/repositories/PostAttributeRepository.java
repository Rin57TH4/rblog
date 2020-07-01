package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.PostAttributeEntity;

/**
 * @author Rin
 */
public interface PostAttributeRepository extends JpaRepository<PostAttributeEntity, Long>, JpaSpecificationExecutor<PostAttributeEntity> {
}
