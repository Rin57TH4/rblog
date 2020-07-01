package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rin.blog.domain.entities.Option;

import java.util.List;

/**
 * @author Rin
 */
public interface OptionRepo extends JpaRepository<Option, String> {

    List<Option> findByCategory(String cate);

    List<Option> findByCategoryAndValue(String cate, String value);

    long countByCategoryAndValue(String cate, String value);
}
