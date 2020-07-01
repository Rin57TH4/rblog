package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.SecurityCodeEntity;

/**
 * @author Rin
 */
public interface SecurityCodeRepository extends JpaRepository<SecurityCodeEntity, Long>, JpaSpecificationExecutor<SecurityCodeEntity> {
    SecurityCodeEntity findByKeyAndType(String key, int type);

    SecurityCodeEntity findByKey(String key);
}
