package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.LinksEntity;

/**
 * @author Rin
 */
public interface LinksRepository extends JpaRepository<LinksEntity, Long>, JpaSpecificationExecutor<LinksEntity> {
}
