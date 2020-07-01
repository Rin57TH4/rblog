package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.data.FavoriteVO;

/**
 * @author Rin
 */
public interface FavoriteService {

    Page<FavoriteVO> pagingByUserId(Pageable pageable, long userId);

    void add(long userId, long postId);

    void delete(long userId, long postId);

    void deleteByPostId(long postId);
}
