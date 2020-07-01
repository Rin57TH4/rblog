package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rin.blog.domain.entities.Article;

/**
 * @author Rin
 */
public interface ArticleRepo extends JpaRepository<Article, String> {
}
