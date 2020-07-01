package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.data.PostTagVO;
import vn.rin.blog.domain.data.TagVO;

/**
 * @author Rin
 */
public interface TagService {
    Page<TagVO> pagingQueryTags(Pageable pageable);

    Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName);

    void batchUpdate(String names, long latestPostId);

    void deteleMappingByPostId(long postId);
}
