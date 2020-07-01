package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.data.CommentVO;
import vn.rin.blog.domain.entity.CommentEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rin
 */
public interface CommentService {
    Page<CommentVO> paging4Admin(Pageable pageable);

    Page<CommentVO> pagingByAuthorId(Pageable pageable, long authorId);

    Page<CommentVO> pagingByPostId(Pageable pageable, long postId);

    List<CommentVO> findLatestComments(int maxResults);

    Map<Long, CommentVO> findByIds(Set<Long> ids);

    CommentEntity findById(long id);

    long post(CommentVO comment);

    void delete(List<Long> ids);

    void delete(long id, long authorId);

    void deleteByPostId(long postId);

    long count();

    long countByAuthorIdAndPostId(long authorId, long postId);
}
