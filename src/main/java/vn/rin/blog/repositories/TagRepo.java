package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entities.Tag;

import java.util.List;

/**
 * @author Rin
 */
public interface TagRepo extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {

    List<Tag> findByStatus(int status);
}
