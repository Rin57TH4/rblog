package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rin.blog.domain.entity.OptionsEntity;

/**
 * @author Rin
 */
public interface OptionsRepo extends JpaRepository<OptionsEntity, Long> {
    OptionsEntity findByKey(String key);
}
