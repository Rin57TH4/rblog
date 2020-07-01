package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.data.PostVO;

/**
 * @author Rin
 */
public interface PostSearchService {
    Page<PostVO> search(Pageable pageable, String term) throws Exception;

    void resetIndexes();
}
