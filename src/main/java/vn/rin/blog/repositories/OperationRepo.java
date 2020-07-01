package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rin.blog.domain.entities.Operation;

/**
 * @author Rin
 */
public interface OperationRepo  extends JpaRepository<Operation,String> {
}
